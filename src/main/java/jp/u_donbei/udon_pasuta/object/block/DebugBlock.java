/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.object.block;

import jp.u_donbei.udon_pasuta.path.PathConstants;
import jp.u_donbei.udon_pasuta.texture.TextureUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * デバッグ用のブロック
 */
public class DebugBlock extends Block {
	public DebugBlock() {
		super(TextureUtil.getTexture("debug").orElse(Paths.get("")), true);
	}
}
