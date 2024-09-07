/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.managers;

import javafx.animation.AnimationTimer;
import jp.u_donbei.udon_pasuta.map.GameMap;
import jp.u_donbei.udon_pasuta.object.block.Block;
import jp.u_donbei.udon_pasuta.object.character.Udonbei;
import jp.u_donbei.udon_pasuta.pane.MainPane;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * ゲームループに関する処理を行う。
 */
@Slf4j
public final class GameLoopManager {
	private static Udonbei player;
	private static boolean isUp, isDown, isLeft, isRight, isShift;
	private static final double SCREEN_W = 800;
	private static final double SCREEN_H = 400;
	private static double udonDiffX, udonDiffY;
	/**
	 * ゲームループを行う。
	 * 先に初期化を行います。
	 */
	public static void gameLoop(MainPane gamePane) {
		init(gamePane);
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gameLoopImpl(gamePane);
			}
		};
		timer.start();
	}

	private static void gameLoopImpl(MainPane gamePane) {
		final boolean isDashed = movePlayer();
		pushBack(gamePane.getGameMap());
		initPlayerPosDiff();
		scroll(gamePane, isDashed);

		player.updateView();
	}

	private static void init(MainPane gamePane) {
		player = new Udonbei();
		gamePane.addCharacter(player);
		gamePane.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case W -> isUp = true;
				case S -> isDown = true;
				case A -> isLeft = true;
				case D -> isRight = true;
				case SHIFT -> isShift = true;
			}
		});

		gamePane.setOnKeyReleased(e -> {
			switch (e.getCode()) {
				case W -> isUp = false;
				case S -> isDown = false;
				case A -> isLeft = false;
				case D -> isRight = false;
				case SHIFT -> isShift = false;
			}
		});

		gamePane.requestFocus();
		gamePane.getTextPanel().changeTextLittleByLittle("ミートソースが現れた!", 100, null, () -> {}, gamePane::requestFocus);
	}

	/**
	 * うどんべいのX座標が400以上ならば横に、
	 * Y座標が250以上ならば縦にスクロールを行う。
	 */
	private static void scroll(MainPane pane, boolean isDashed) {
		if (player.getX() >= SCREEN_W / 2 - udonDiffX && player.getX() < Block.DEFAULT_WIDTH * GameMap.MAP_W - SCREEN_W / 2) {
			pane.getCamera().setTranslateX(pane.getCamera().getTranslateX() - udonDiffX);
		}
		if (player.getY() >= SCREEN_H / 2 - udonDiffY && player.getY() < Block.DEFAULT_HEIGHT * GameMap.MAP_H - SCREEN_H / 2) {
			pane.getCamera().setTranslateY(pane.getCamera().getTranslateY() - udonDiffY);
		}
		//カメラの座標が負の数になった場合は0に戻す
		if (pane.getCamera().getTranslateX() < 0) {
			pane.getCamera().setTranslateX(0);
		}
		if (pane.getCamera().getTranslateY() < 0) {
			pane.getCamera().setTranslateY(0);
		}
	}

	/**
	 * キー入力に応じてプレイヤーを動かす
	 * @return ダッシュしたかどうか
	 */
	private static boolean movePlayer() {
		boolean isDashed = false;
		if (isUp && player.getY() > 0) {
			player.moveY(-3);
			if (isShift) {
				isDashed = true;
				player.moveY(-3);
			}
		}

		if (isDown && player.getY() < (Block.DEFAULT_HEIGHT - 1) *  GameMap.MAP_H) {
			player.moveY(3);
			if (isShift) {
				isDashed = true;
				player.moveY(3);
			}
		}

		if (isLeft && player.getX() > 0) {
			player.moveX(-3);
			if (isShift) {
				isDashed = true;
				player.moveX(-3);
			}
		}

		if (isRight && player.getX() < (Block.DEFAULT_WIDTH - 1) * GameMap.MAP_W) {
			player.moveX(3);
			if (isShift) {
				isDashed = true;
				player.moveX(3);
			}
		}
		return isDashed;
	}

	/**
	 * プレイヤーを押し戻す。
	 * 押し戻したかどうかに応じてスクロールを停止します。
	 * @param blocks 押し戻すブロックが入った配列
	 */
	private static void pushBack(List<Block> blocks) {
		for (Block block : blocks) {
			block.pushBack(player);
		}
	}

	/**
	 * プレイヤーの座標の差分を設定する。
	 */
	private static void initPlayerPosDiff() {
		//getX()やgetY()とgetView().getTranslateX()やgetView().getTranslateY()の差分を利用する
		udonDiffX = player.getView().getTranslateX() - player.getX();
		udonDiffY = player.getView().getTranslateY() - player.getY();
	}
}
