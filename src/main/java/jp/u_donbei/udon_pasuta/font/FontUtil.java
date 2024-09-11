/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.u_donbei.udon_pasuta.font;

import javafx.scene.text.Font;

/**
 * フォントに関するユーティリティクラス
 */
public final class FontUtil {
	/**
	 * PixelMplus12Regularのフォントを取得する。
	 * @param size フォントサイズ
	 * @return 取得したフォント
	 */
	public static Font getPixelMplus12Regular(double size) {
		return Font.loadFont(FontUtil.class.getResourceAsStream("/font/PixelMplus/PixelMplus12-Regular.ttf"), size);
	}
}
