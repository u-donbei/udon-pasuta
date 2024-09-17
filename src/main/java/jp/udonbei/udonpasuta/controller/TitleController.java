/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import jp.udonbei.udonpasuta.managers.AppStartManager;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleController implements Initializable {

    @FXML
    private Button exit;

    @FXML
    private BorderPane pane;

    @FXML
    private Button setting;

    @FXML
    private Button start;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("/textures/title.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        Font font = Font.loadFont(getClass().getResourceAsStream("/font/PixelMplus/PixelMplus12-Regular.ttf"), 45);

        start.setFont(font);
        setting.setFont(font);
        exit.setFont(font);
    }

    @FXML
    void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void handleSetting(ActionEvent event) {

    }

    @FXML
    void handleStart(ActionEvent event) {
        AppStartManager.start();
    }

}

