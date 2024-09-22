/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object.block;

import javafx.geometry.Bounds;
import jp.udonbei.udonpasuta.object.GameObject;
import jp.udonbei.udonpasuta.object.character.GameCharacter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ブロック(地面、建物など)を表す。
 */
@Slf4j
public abstract class Block extends GameObject {
    public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;

    /**
     * 生成された全てのインスタンス
     */
    private static final List<Block> GENERATED_INSTANCES = new ArrayList<>();

    public Block(Path image, boolean contactable) {
        super(image);
        setContactable(contactable);
        GENERATED_INSTANCES.add(this);
    }

    @Override
    public Optional<PushBackDirection> pushBack(GameCharacter target) {
        if (!isContact(target)) {
            return Optional.empty();
        }
        Bounds bounds = getView().getBoundsInParent(), targetBounds = target.getView().getBoundsInParent();

        double top = (bounds.getMinY() - targetBounds.getMaxY());
        double bottom = (bounds.getMaxY() - targetBounds.getMinY());

        double left = (bounds.getMinX() - targetBounds.getMaxX());
        double right = (bounds.getMaxX() - targetBounds.getMinX());

        boolean isLeftObject = GENERATED_INSTANCES.stream()
                .filter(GameObject::isContactable)
                .anyMatch(t -> t.getView().getBoundsInParent().contains(getX() - 1, getY()));
        boolean isRightObject = GENERATED_INSTANCES.stream()
                .filter(GameObject::isContactable)
                .anyMatch(t -> t.getView().getBoundsInParent().contains(getX() + Block.DEFAULT_WIDTH + 1, getY()));
        boolean isTopObject = GENERATED_INSTANCES.stream()
                .filter(GameObject::isContactable)
                .anyMatch(t -> t.getView().getBoundsInParent().contains(getX(), getY() - 1));
        boolean isBottomObject = GENERATED_INSTANCES.stream()
                .filter(GameObject::isContactable)
                .anyMatch(t -> t.getView().getBoundsInParent().contains(getX(), getY() + Block.DEFAULT_HEIGHT + 1));

        //変数名 != (-)1は接触で動けなくなるのを防いでいる
        if (top < 0 && top > -5 && top != -1 && !isTopObject) {
            target.moveY(top);
            return Optional.of(PushBackDirection.TOP);
        } else if (left < 0 && left > -5 && left != -1 && !isLeftObject) {
            target.moveX(left);
            return Optional.of(PushBackDirection.LEFT);
        } else if (bottom > 0 && bottom < 5 && bottom != 1 && !isBottomObject) {
            target.moveY(bottom);
            return Optional.of(PushBackDirection.BOTTOM);
        } else if (right > 0 && right < 5 && bottom != 1 && !isRightObject) {
            target.moveX(right);
            return Optional.of(PushBackDirection.RIGHT);
        }

        return Optional.empty();
    }
}
