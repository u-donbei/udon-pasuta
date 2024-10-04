package jp.udonbei.udonpasuta.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.font.FontUtil;
import jp.udonbei.udonpasuta.gameloop.GameLoopManager;
import jp.udonbei.udonpasuta.sound.bgm.BGMConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuController extends GUIInit {

    @FXML
    private Button back;

    @FXML
    private Button saveAndExit;

    @FXML
    private Button setting;

    @FXML
    private Label title;
    private Scene settingScene;

    @FXML
    void initialize() {
        Font font = FontUtil.getPixelMplus12Regular(16);
        back.setFont(font);
        saveAndExit.setFont(font);
        setting.setFont(font);
        title.setFont(FontUtil.getPixelMplus12Regular(24));
    }

    public void guiInit(Stage mainWindow, Scene mainScene, Scene settingScene) {
        super.guiInit(mainWindow, mainScene);
        this.settingScene = settingScene;
    }

    public void onBackHandle(ActionEvent actionEvent) {
        mainWindow.setScene(beforeScene);
        GameLoopManager.timer.start();
        BGMConstants.FIELD.getBGM().play();
    }

    public void onSettingHandle(ActionEvent actionEvent) {
        mainWindow.setScene(settingScene);
    }

    public void onExitHandle(ActionEvent actionEvent) {
    }

    public void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            mainWindow.setScene(beforeScene);
            GameLoopManager.timer.start();
            BGMConstants.FIELD.getBGM().play();
        }
    }

}
