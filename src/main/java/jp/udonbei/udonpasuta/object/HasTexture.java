/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object;

import javafx.scene.image.Image;

/**
 * テクスチャがあることを表す。
 */
public interface HasTexture {
    /**
     * 画像を取得する。
     *
     * @return 現在表示されている画像
     */
    Image getImage();

    /**
     * 画像を設定する。
     *
     * @param image 表示する画像
     */
    void setImage(Image image);
}
