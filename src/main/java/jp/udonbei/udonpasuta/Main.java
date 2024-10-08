/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.app.AppStartManager;
import jp.udonbei.udonpasuta.controller.SettingController;
import jp.udonbei.udonpasuta.controller.TitleController;
import jp.udonbei.udonpasuta.gameloop.GameLoopManager;
import jp.udonbei.udonpasuta.path.PathConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static boolean IS_DEVELOP;
    private static int exitCode = 0;

    public static void main(String[] args) {
        LOGGER.info("Application Start.");

        //引数に応じて開発環境か否かを設定する
        LOGGER.info("Setting environment.");
        if (args.length == 0) {
            production();
        } else {
            String develop = args[0];
            if (develop.equals("--develop")) {
                LOGGER.info("Using develop environment.");
                System.setProperty("udon.develop", "true");
                IS_DEVELOP = true;
                System.setProperty("udon.log.level", "debug");
            } else {
                production();
            }
        }
        updateLogbackConfigure();
        PathConstants.update();
        launch(args);
        LOGGER.info("Application Exit.");
        System.exit(exitCode);
    }

    private static void production() {
        LOGGER.info("Using production environment.");
        System.setProperty("udon.develop", "false");
        System.setProperty("udon.log.level", "info");
    }

    private static void updateLogbackConfigure() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        context.reset();
        try {
            configurator.doConfigure(Main.class.getResource("/logback.xml"));
        } catch (JoranException e) {
            LOGGER.error("An Logback configure failed", e);
        }
    }

    public static boolean isDevelop() {
        return IS_DEVELOP;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane main = null;
        FXMLLoader titleLoader = new FXMLLoader(getClass().getResource("/title.fxml"));
        try {
            LOGGER.debug("debug test");
            LOGGER.info("Loading FXML.");
            main = titleLoader.load();
        } catch (IOException e) {
            LOGGER.error("FXML Load failed: ", e);
            exitCode = 1;
            Platform.exit();
        }

        //設定画面の初期化
        FXMLLoader settingLoader = new FXMLLoader(GameLoopManager.class.getResource("/setting.fxml"));
        BorderPane settingPane = settingLoader.load();
        Scene settingScene = new Scene(settingPane, 800, 500);
        SettingController settingController = settingLoader.getController();

        TitleController titleController = titleLoader.getController();
        titleController.registerSetting(e -> stage.setScene(settingScene));

        Scene mainScene = new Scene(main);

        settingController.guiInit(stage, mainScene);
        settingController.childPaneInit(settingScene);

        stage.setScene(mainScene);
        stage.setTitle("うどんべいのパスタ退治");
        stage.getIcons().add(new Image(PathConstants.TEXTURES.toURI() + "/udon.png"));
        stage.show();

        LOGGER.info("Waiting start.");
        AppStartManager.waitStart(stage, settingScene, settingController);
    }
}
