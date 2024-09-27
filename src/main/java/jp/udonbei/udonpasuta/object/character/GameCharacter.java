/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object.character;

import jp.udonbei.udonpasuta.object.GameObject;
import jp.udonbei.udonpasuta.object.Movable;
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
        setContactable(true);
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
