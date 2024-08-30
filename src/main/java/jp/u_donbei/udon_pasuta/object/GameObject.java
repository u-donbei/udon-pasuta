package jp.u_donbei.udon_pasuta.object;

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
	private int x, y;
	private Image image;
	private transient ImageView view;

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
	 */
	public void updateView() {
		view.setTranslateX(getX());
		view.setTranslateY(getY());
	}
}
