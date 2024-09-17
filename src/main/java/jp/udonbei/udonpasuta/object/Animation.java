/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object;

/**
 * アニメーションできることを表す。
 */
public interface Animation {
    /**
     * 次のテクスチャに変更する。
     */
    void nextTexture();

    /**
     * テクスチャを変更する。
     *
     * @param id テクスチャID。IDは各クラスが関連付けます。
     */
    void changeTexture(int id);
}
