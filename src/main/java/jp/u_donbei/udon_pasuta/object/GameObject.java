/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.object;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * 物を表す。
 */
@Data
public abstract class GameObject implements HasTexture {
	private double x, y;
	private Image image;
	private transient ImageView view;
	private boolean contactable;

	/**
	 * コンストラクタ。
	 * 画像を初期化する。
	 * @param image 表示する画像
	 */
	public GameObject(Path image) {
		try {
			setImage(new Image(image.toUri().toURL().toExternalForm()));
			view = new ImageView(getImage());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 座標と表示を同期する。
	 * {@link #image}と{@link #view}も同期します。
	 */
	public void updateView() {
		view.setImage(image);
		view.setTranslateX(getX());
		view.setTranslateY(getY());
	}

	/**
	 * 対象のGameObjectに当たっている(1ピクセルでも重なっている)かどうか判定する
	 */
	public boolean isContact(GameObject target) {
		Bounds bounds = getView().getBoundsInParent();
		Bounds targetBounds = target.getView().getBoundsInParent();
		return isContactable() && bounds.intersects(targetBounds);
	}
}
