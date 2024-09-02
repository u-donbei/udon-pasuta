package jp.u_donbei.udon_pasuta.object.block;

import javafx.geometry.Bounds;
import jp.u_donbei.udon_pasuta.object.GameObject;
import jp.u_donbei.udon_pasuta.object.character.GameCharacter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Optional;

/**
 * ブロック(地面、建物など)を表す。
 */
@Slf4j
public abstract class Block extends GameObject {
	public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;
	public Block(Path image, boolean contactable) {
		super(image);
		setContactable(contactable);
	}

	/**
	 * 当たり判定などに応じて押し戻す。
	 * @param target 押し戻す対象
	 * @return 押し戻した方向(上から突っ込んで来た場合は上に押し戻します)
	 */
	public Optional<PushBackDirection> pushBack(GameCharacter target) {
		if (!isContact(target)) return Optional.empty();
		Bounds bounds = getView().getBoundsInParent(), targetBounds = target.getView().getBoundsInParent();

		double horizontal = getX() - target.getX();
		double vertical = (bounds.getMinY() - targetBounds.getMaxY());

		//if (horizontal > 0) {
		//	target.moveX(-horizontal);
		//}
		if (vertical < 0) {
			target.moveY(vertical);
			return Optional.of(PushBackDirection.TOP);
		}
		return Optional.empty();
	}

	public enum PushBackDirection {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
	}
}
