/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
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
