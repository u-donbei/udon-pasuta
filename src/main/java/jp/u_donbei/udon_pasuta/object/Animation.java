/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.object;

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
	 * @param id テクスチャID。IDは各クラスが関連付けます。
	 */
	void changeTexture(int id);
}
