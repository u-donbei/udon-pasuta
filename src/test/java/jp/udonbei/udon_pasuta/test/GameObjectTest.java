/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udon_pasuta.test;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jp.udonbei.udonpasuta.object.GameObject;
import jp.udonbei.udonpasuta.object.TestObject;
import jp.udonbei.udonpasuta.object.block.Block;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertSame;

public class GameObjectTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        for (int i = 0; i < 3; i++) {
            GameObject object = new TestObject();
            object.setX(i * Block.DEFAULT_WIDTH);
            object.synchronize();
            pane.getChildren().add(object.getView());
        }
        pane.setId("root-pane");

        Scene scene = new Scene(pane, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void isContactedTest() {

    }
}
