/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.gameloop;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.controller.MenuController;
import jp.udonbei.udonpasuta.controller.SettingController;
import jp.udonbei.udonpasuta.event.Event;
import jp.udonbei.udonpasuta.event.EventManager;
import jp.udonbei.udonpasuta.map.GameMap;
import jp.udonbei.udonpasuta.object.AutomaticProcessable;
import jp.udonbei.udonpasuta.object.GameObject;
import jp.udonbei.udonpasuta.object.block.Block;
import jp.udonbei.udonpasuta.object.character.GameCharacter;
import jp.udonbei.udonpasuta.object.character.Onigiri;
import jp.udonbei.udonpasuta.object.character.Udonbei;
import jp.udonbei.udonpasuta.pane.MainPane;
import jp.udonbei.udonpasuta.sound.bgm.BGMConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * ゲームループに関する処理を行う。
 */
@Slf4j
public final class GameLoopManager {
    private static final double SCREEN_W = 800;
    private static final double SCREEN_H = 400;
    private static Udonbei player;
    private static boolean isUp, isDown, isLeft, isRight, isShift, isEsc;
    private static long beforeTime;
    private static Scene menuScene, settingScene;
    private static Stage mainWindow;
    private static EventManager eventManager;
    public static AnimationTimer timer;

    /**
     * ゲームループを行う。
     * 先に初期化を行います。
     *
     * @param gamePane 生成したMainPane
     */
    public static void gameLoop(MainPane gamePane, Stage mainWindow, Scene settingScene, SettingController settingController) throws IOException {
        GameLoopManager.mainWindow = mainWindow;
        init(gamePane, settingScene, settingController);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameLoopImpl(gamePane, now);
            }
        };
        timer.start();
    }

    private static void gameLoopImpl(MainPane gamePane, long now) {
        checkEscToMenu();
        movePlayer();
        gameLoopProcess(gamePane);
        eventManager.runEvents(player, gamePane.getTextPanel());
        pushBack(gamePane.getGameMap(), gamePane.getCharacters());
        scroll(gamePane);
        animation(now);

        synchronize(gamePane.getCharacters());
    }

    private static void init(MainPane gamePane, Scene settingScene, SettingController settingController) throws IOException {
        player = new Udonbei();
        //キャラクターの初期化
        gamePane.addCharacter(player);
        gamePane.addCharacter(new Onigiri());
        //gamePaneの初期化
        gamePane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> isUp = true;
                case S -> isDown = true;
                case A -> isLeft = true;
                case D -> isRight = true;
                case SHIFT -> isShift = true;
                case ESCAPE -> isEsc = true;
            }
        });

        gamePane.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W -> isUp = false;
                case S -> isDown = false;
                case A -> isLeft = false;
                case D -> isRight = false;
                case SHIFT -> isShift = false;
                case ESCAPE -> isEsc = false;
            }
        });

        gamePane.requestFocus();

        //イベントマネージャを初期化
        eventManager = new EventManager();

        //メニューや設定画面の生成・初期化
        FXMLLoader menuLoader = new FXMLLoader(GameLoopManager.class.getResource("/menu.fxml"));
        BorderPane menuPane = menuLoader.load();
        menuScene = new Scene(menuPane, 800, 500);
        MenuController menuController = menuLoader.getController();

        GameLoopManager.settingScene = settingScene;

        menuController.guiInit(mainWindow, mainWindow.getScene(), settingScene);
        settingController.guiInit(mainWindow, menuScene);
        settingController.childPaneInit(settingScene);

        gamePane.getTextPanel().changeTextLittleByLittle("ミートソースが現れた!", 100, null, () -> {
        }, gamePane::requestFocus);
    }

    /**
     * うどんべいのX座標が400以上ならば横に、
     * Y座標が250以上ならば縦にスクロールを行う。
     *
     * @param pane スクロールするMainPane
     */
    private static void scroll(MainPane pane) {
        double udonDiffX = player.getDiffX();
        double udonDiffY = player.getDiffY();
        if (player.getX() >= SCREEN_W / 2 - udonDiffX
            && player.getX() < Block.DEFAULT_WIDTH * GameMap.MAP_W - SCREEN_W / 2) {
            pane.getCamera().setTranslateX(pane.getCamera().getTranslateX() - udonDiffX);
        }
        if (player.getY() >= SCREEN_H / 2 - udonDiffY
            && player.getY() < Block.DEFAULT_HEIGHT * GameMap.MAP_H - SCREEN_H / 2) {
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
     */
    private static void movePlayer() {
        if (isUp && player.getY() > 0) {
            player.moveY(-3);
            if (isShift) {
                player.moveY(-3);
            }
        }

        if (isDown && player.getY() < (Block.DEFAULT_HEIGHT - 1) * GameMap.MAP_H) {
            player.moveY(3);
            if (isShift) {
                player.moveY(3);
            }
        }

        if (isLeft && player.getX() > 0) {
            player.moveX(-3);
            if (isShift) {
                player.moveX(-3);
            }
        }

        if (isRight && player.getX() < (Block.DEFAULT_WIDTH - 1) * GameMap.MAP_W) {
            player.moveX(3);
            if (isShift) {
                player.moveX(3);
            }
        }
    }

    /**
     * キャラクターを押し戻す。
     *
     * @param blocks     押し戻すブロックが入った配列
     * @param characters 押し戻す対象のキャラクター
     */
    private static void pushBack(List<Block> blocks, List<GameCharacter> characters) {
        //当たり判定の対象を周囲のブロックに限定
        Rectangle rect = new Rectangle(Block.DEFAULT_WIDTH * 3, Block.DEFAULT_HEIGHT * 3);
        rect.setTranslateX(player.getX() - Block.DEFAULT_WIDTH);
        rect.setTranslateY(player.getY() - Block.DEFAULT_HEIGHT);

        blocks = blocks.stream()
                .filter(block -> {
                    Bounds bounds = rect.getBoundsInParent();
                    return bounds.contains(block.getHitRect().getBoundsInParent());
                })
                .toList();
        characters = characters.stream()
                .filter(character -> {
                    Bounds bounds = rect.getBoundsInParent();
                    return bounds.contains(character.getHitRect().getBoundsInParent());
                })
                .toList();
        for (Block block : blocks) {
            block.pushBack(player);
        }
        //for (GameCharacter character : characters) {
        //	block.pushBack(character);
        //}
        for (GameCharacter character : characters) {
            character.pushBack(player);
        }
    }

    /**
     * キャラクターをアニメーションさせる。
     *
     * @param now 現在のナノ秒
     */
    private static void animation(long now) {
        if (now - beforeTime >= 500_000_000) {
            player.nextTexture();
            beforeTime = now;
        }
    }

    /**
     * ESCキーが押されたかチェックし、メニュー画面を表示する
     */
    private static void checkEscToMenu() {
        if (!isEsc) {
            return;
        }
        mainWindow.setScene(menuScene);
        isEsc = false;      //Sceneが切り替わり、gamePaneのフォーカスが失われ、isEscがfalseにならなくなる問題の対処
        timer.stop();
        BGMConstants.FIELD.getBGM().pause();
    }

    /**
     * ゲームループに合わせて行う処理を行う。
     * @param gamePane 表示されているMainPane
     */
    private static void gameLoopProcess(MainPane gamePane) {
        gamePane.getCharacters().stream()
                .filter(t -> t instanceof AutomaticProcessable)
                .forEach(t -> ((AutomaticProcessable) t).handle());
    }

    /**
     * プレイヤーとキャラクターの座標と表示を同期する。
     * @param characters 処理するキャラクターが入ったリスト
     */
    private static void synchronize(List<GameCharacter> characters) {
        player.synchronize();
        characters.forEach(GameObject::synchronize);
    }
}
