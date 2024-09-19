/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object.character;

import jp.udonbei.udonpasuta.texture.TextureUtil;

/**
 * おにぎりくんのクラス。
 */
public class Onigiri extends GameCharacter {
    /**
     * コンストラクタ。
     */
    public Onigiri() {
        super(TextureUtil.getTextureThrow("onigiri"));
    }
}
