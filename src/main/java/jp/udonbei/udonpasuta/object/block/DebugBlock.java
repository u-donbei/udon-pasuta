/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object.block;

import jp.udonbei.udonpasuta.texture.TextureUtil;

import java.nio.file.Paths;

/**
 * デバッグ用のブロック
 */
public class DebugBlock extends Block {
    public DebugBlock() {
        super(TextureUtil.getTexture("debug").orElse(Paths.get("")), true);
    }
}
