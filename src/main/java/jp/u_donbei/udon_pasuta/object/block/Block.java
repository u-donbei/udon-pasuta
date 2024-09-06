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

		double top = (bounds.getMinY() - targetBounds.getMaxY());
		double bottom = (bounds.getMaxY() - targetBounds.getMinY());

		double left = (bounds.getMinX() - targetBounds.getMaxX());
		double right = (bounds.getMaxX() - targetBounds.getMinX());

		//変数名 != (-)1は接触で動けなくなるのを防いでいる
		if (top < 0 && top > -5 && top != -1) {
			target.moveY(top);
			return Optional.of(PushBackDirection.TOP);
		} else if (left < 0 && left > -5 && left != -1) {
			target.moveX(left);
			return Optional.of(PushBackDirection.LEFT);
		} else if (bottom > 0 && bottom < 5 && bottom != 1) {
			target.moveY(bottom);
			return Optional.of(PushBackDirection.BOTTOM);
		}  else if (right > 0 && right < 5 && bottom != 1) {
			target.moveX(right);
			return Optional.of(PushBackDirection.RIGHT);
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
