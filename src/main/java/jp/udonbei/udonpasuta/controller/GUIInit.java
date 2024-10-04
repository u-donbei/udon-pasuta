/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class GUIInit {
    protected Stage mainWindow;
    protected Scene beforeScene;
    public void guiInit(Stage mainWindow, Scene beforeScene) {
        this.mainWindow = mainWindow;
        this.beforeScene = beforeScene;
    }
}
