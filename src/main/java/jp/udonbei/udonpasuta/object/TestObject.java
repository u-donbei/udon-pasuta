/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object;

import jp.udonbei.udonpasuta.path.PathConstants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestObject extends GameObject {
    /**
     * コンストラクタ。
     * 画像を初期化する。
     */
    public TestObject() {
        super(Paths.get(PathConstants.TEXTURES + "debug.png"));
        getView().setId("test-object");
    }
}
