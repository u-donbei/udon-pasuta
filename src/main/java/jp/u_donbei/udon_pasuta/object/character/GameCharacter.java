package jp.u_donbei.udon_pasuta.object.character;

import jp.u_donbei.udon_pasuta.object.GameObject;
import jp.u_donbei.udon_pasuta.object.Movable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

/**
 * ゲームのキャラクター。
 */
@Setter
@Getter
public abstract class GameCharacter extends GameObject implements Movable {
	/**
	 * 残り体力。
	 */
	private int hp;
	/**
	 * コンストラクタ。
	 * 画像を初期化する。
	 *
	 * @param image 表示する画像
	 */
	public GameCharacter(Path image) {
		super(image);
	}

	@Override
	public void moveX(double x) {
		setX(getX() + x);
	}

	@Override
	public void moveY(double y) {
		setY(getY() + y);
	}
}
