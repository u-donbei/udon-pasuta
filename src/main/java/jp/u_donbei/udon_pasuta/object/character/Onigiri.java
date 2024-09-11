/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.u_donbei.udon_pasuta.object.character;

import jp.u_donbei.udon_pasuta.texture.TextureUtil;

/**
 * おにぎりくんのクラス。
 */
public class Onigiri extends GameCharacter {
	/**
	 * {@inheritDoc}
	 */
	public Onigiri() {
		super(TextureUtil.getTextureThrow("onigiri"));
	}
}
