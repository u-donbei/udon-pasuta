/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.object;

import javafx.scene.image.Image;

/**
 * テクスチャがあることを表す。
 */
public interface HasTexture {
	/**
	 * 画像を取得する。
	 * @return 現在表示されている画像
	 */
	Image getImage();

	/**
	 * 画像を設定する。
	 * @param image 表示する画像
	 */
	void setImage(Image image);
}
