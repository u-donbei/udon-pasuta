/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udon_pasuta.test;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.controller.SettingController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Objects;

public class SettingPaneTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = FXMLLoader.load(Objects.requireNonNull(SettingController.class.getResource("/setting.fxml")));
        stage.setScene(new Scene(pane, 800, 500));
        stage.setTitle("テスト");
        stage.show();
        Platform.enterNestedEventLoop("test-loop");
    }

    @Test
    public void ignored() {

    }
}
