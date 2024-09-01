package jp.u_donbei.udon_pasuta.managers;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import jp.u_donbei.udon_pasuta.Main;
import jp.u_donbei.udon_pasuta.map.GameMap;
import jp.u_donbei.udon_pasuta.object.block.Block;
import jp.u_donbei.udon_pasuta.object.character.Udonbei;
import jp.u_donbei.udon_pasuta.pane.MainPane;

/**
 * ゲームループに関する処理を行う。
 */
public final class GameLoopManager {
	private static Udonbei player;
	private static boolean isUp, isDown, isLeft, isRight, isShift;
	private static int SCREEN_W = 800, SCREEN_H = 400;
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
		int addPos = isDashed ? 6 : 3;
		if (isRight && player.getX() >= SCREEN_W / 2 && player.getX() < Block.DEFAULT_WIDTH * GameMap.MAP_W - SCREEN_W / 2) {
			pane.getCamera().setTranslateX(pane.getCamera().getTranslateX() + 3);
			if (isDashed) {
				pane.getCamera().setTranslateX(pane.getCamera().getTranslateX() + 3);
			}
		}
		if (isLeft && player.getX() >= SCREEN_W / 2 - addPos) {
			pane.getCamera().setTranslateX(pane.getCamera().getTranslateX() - 3);
			if (isDashed) {
				pane.getCamera().setTranslateX(pane.getCamera().getTranslateX() - 3);
			}
		}
		if (isUp && player.getY() >= SCREEN_H / 2 - addPos) {
			pane.getCamera().setTranslateY(pane.getCamera().getTranslateY() - 3);
			if (isDashed) {
				pane.getCamera().setTranslateY(pane.getCamera().getTranslateY() - 3);
			}
		}
		if (isDown && player.getY() >= SCREEN_H / 2 && player.getY() < Block.DEFAULT_HEIGHT * GameMap.MAP_H - SCREEN_H / 2) {
			pane.getCamera().setTranslateY(pane.getCamera().getTranslateY() + 3);
			if (isDashed) {
				pane.getCamera().setTranslateY(pane.getCamera().getTranslateY() + 3);
			}
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
}
