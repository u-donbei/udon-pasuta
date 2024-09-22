/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jp.udonbei.udonpasuta.object.block.Block;
import jp.udonbei.udonpasuta.object.character.GameCharacter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 物を表す。
 */
@Slf4j
@Data
public abstract class GameObject implements HasTexture {
    private double x, y;
    private Image image;
    private transient ImageView view;
    private boolean contactable;

    /**
     * コンストラクタ。
     * 画像を初期化する。
     *
     * @param image 表示する画像
     */
    public GameObject(Path image) {
        try {
            setImage(new Image(image.toUri().toURL().toExternalForm()));
            view = new ImageView(getImage());
        } catch (MalformedURLException e) {
            log.error("An fatal error occurred:", e);
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
     * @param target 判定する対象
     * @return 判定した結果、当たっていたか
     */
    public boolean isContact(GameObject target) {
        Bounds bounds = getView().getBoundsInParent();
        Bounds targetBounds = target.getView().getBoundsInParent();
        return isContactable() && bounds.intersects(targetBounds);
    }

    /**
     * 当たり判定などに応じて押し戻す。
     *
     * @param target 押し戻す対象
     * @return 押し戻した方向(上から突っ込んで来た場合は上に押し戻します)
     */
    public Optional<PushBackDirection> pushBack(GameCharacter target) {
        if (!isContact(target)) {
            return Optional.empty();
        }
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
        } else if (right > 0 && right < 5 && bottom != 1) {
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
