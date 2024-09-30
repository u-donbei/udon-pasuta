/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.app;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.font.FontUtil;
import jp.udonbei.udonpasuta.gameloop.GameLoopManager;
import jp.udonbei.udonpasuta.map.GameMap;
import jp.udonbei.udonpasuta.pane.MainPane;
import jp.udonbei.udonpasuta.sound.bgm.BGMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ゲームのスタート処理を行う。
 */
public final class AppStartManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppStartManager.class);
    private static Stage mainWindow;
    private static boolean isWaited;
    private static Scene beforeScene;

    /**
     * ゲームスタートボタンが押されるのを待つ。
     *
     * @param stage ボタンが押された時に描画するウィンドウ
     */
    public static void waitStart(Stage stage) {
        mainWindow = stage;
        isWaited = true;
    }

    /**
     * ゲームをスタートする。
     */
    public static void start() {
        //シーンの変更
        changeSceneToLoading();
        //マップを構築
        GameMap map = new GameMap(false);

        Service<Void> mapBuildService = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        map.build();
                        //res.build()の呼び出しが終了するとJavaFX Application Threadでの処理が再開する
                        return null;
                    }
                };
            }
        };
        mapBuildService.start();
        mapBuildService.setOnSucceeded(e -> {
            LOGGER.info("GameMap build success.");
            changeSceneToMain(map);
        });
    }

    /**
     * mainWindowのSceneをロード中に変更する
     */
    private static void changeSceneToLoading() {
        //後で戻せるよう、現在のシーンを保存しておく
        beforeScene = mainWindow.getScene();
        Label loadLabel = new Label("ロード中...");
        loadLabel.setTextFill(Color.WHITE);
        loadLabel.setFont(FontUtil.getPixelMplus12Regular(36));

        BorderPane loadPane = new BorderPane(loadLabel);
        loadPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));


        Scene loadScene = new Scene(loadPane, 800, 500);
        mainWindow.setScene(loadScene);
    }

    /**
     * mainWindowのSceneをメインに変更する
     * @param map 生成したGameMap
     */
    private static void changeSceneToMain(GameMap map) {
        MainPane pane = new MainPane(map);

        LOGGER.info("Draw success.");
        Scene scene = new Scene(pane, 800, 500);
        scene.getStylesheets().add(AppStartManager.class.getResource("/css/style.css").toExternalForm());
        mainWindow.setScene(scene);

        BGMConstants.FIELD.getSE().setVolume(40);
        BGMConstants.FIELD.getSE().play();
        LOGGER.info("playing SE.");

        GameLoopManager.gameLoop(pane);

    }
}
