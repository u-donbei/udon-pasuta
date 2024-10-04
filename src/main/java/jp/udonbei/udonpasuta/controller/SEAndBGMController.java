/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import jp.udonbei.udonpasuta.font.FontUtil;
import jp.udonbei.udonpasuta.sound.bgm.BGMConstants;

import java.net.URL;
import java.util.ResourceBundle;

public class SEAndBGMController extends GUIInit implements Initializable {

    @FXML
    public Button backTo;

    @FXML
    private Slider bgm;

    @FXML
    private Label bgmLabel;

    @FXML
    private Slider se;

    @FXML
    private Label seLabel;

    @FXML
    private Label title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = FontUtil.getPixelMplus12Regular(16);
        bgmLabel.setFont(font);
        seLabel.setFont(font);
        title.setFont(FontUtil.getPixelMplus12Regular(24));

        //スライダー関係の設定
        bgm.valueProperty().addListener((ob, o, n) -> {
            int newValue = (int) Math.floor((double) n);
            bgmLabel.setText("BGMの音量(" + newValue + ")");
            for (BGMConstants b : BGMConstants.values()) {
                b.getBGM().setVolume(newValue);
            }
        });
        se.valueProperty().addListener((ob, o, n) -> {
            n = Math.floor((double) n);
            seLabel.setText("SE(効果音)の音量(" + n + ")");
        });
    }

    public void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            mainWindow.setScene(beforeScene);
        }
    }

    public void onBackToHandle(ActionEvent e) {
        mainWindow.setScene(beforeScene);
    }
}
