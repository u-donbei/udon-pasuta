/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.font.FontUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingController extends GUIInit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backTo;

    @FXML
    private Button sounds;

    @FXML
    private Label title;
    private Scene soundScene;
    private FXMLLoader soundLoader;

    @FXML
    void initialize() {
        Font font = FontUtil.getPixelMplus12Regular(16);
        backTo.setFont(font);
        sounds.setFont(font);
        title.setFont(FontUtil.getPixelMplus12Regular(24));
        try {
            soundLoader = new FXMLLoader(getClass().getResource("/setting/se_bgm.fxml"));
            BorderPane soundPane = soundLoader.load();
            soundScene = new Scene(soundPane, 800, 500);
        } catch (IOException e) {
            log.error("FXML load failed", e);
        }
    }

    public void childPaneInit(Scene soundBeforeScene) {
        SEAndBGMController seAndBGMController = soundLoader.getController();
        seAndBGMController.guiInit(mainWindow, soundBeforeScene);
    }

    public void onBackToHandle(ActionEvent e) {
        mainWindow.setScene(beforeScene);
    }

    public void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            mainWindow.setScene(beforeScene);
        }
    }

    public void onSoundsHandle(ActionEvent e) {
        mainWindow.setScene(soundScene);
    }
}

