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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jp.udonbei.udonpasuta.object.character.GameCharacter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * 物を表す。
 */
@Slf4j
@Data
public abstract class GameObject implements HasTexture {
    private double x, y;
    private double beforeX, beforeY;
    private Image image;
    private transient ImageView view;
    private transient Rectangle hitRect;
    private boolean contactable;

    /**
     * コンストラクタ。
     * 画像を初期化します。
     * 当たり判定の長方形の大きさは画像と等しくなるよう設定されます。
     *
     * @param image 表示する画像
     */
    public GameObject(Path image) {
        try {
            setImage(new Image(image.toUri().toURL().toExternalForm()));
            view = new ImageView(getImage());
            hitRect = new Rectangle(getImage().getWidth(), getImage().getHeight());
            hitRect.setFill(Color.TRANSPARENT);
        } catch (MalformedURLException e) {
            log.error("An fatal error occurred:", e);
        }
    }

    /**
     * コンストラクタ。
     * 画像を初期化する。
     *
     * @param image 表示する画像
     * @param width 当たり判定の長方形の幅
     * @param height 当たり判定の長方形の高さ
     */
    public GameObject(Path image, double width, double height) {
        try {
            setImage(new Image(image.toUri().toURL().toExternalForm()));
            view = new ImageView(getImage());
            hitRect = new Rectangle(width, height);
            hitRect.setFill(Color.TRANSPARENT);
        } catch (MalformedURLException e) {
            log.error("An fatal error occurred:", e);
        }
    }

    /**
     * X座標を設定する。
     * 当たり判定の長方形の座標も設定します。
     * @param x X座標
     */
    public void setX(double x) {
        this.x = x;
        getHitRect().setTranslateX(x);
    }

    /**
     * Y座標を設定する。
     * 当たり判定の長方形の座標も設定します。
     * @param y Y座標
     */
    public void setY(double y) {
        this.y = y;
        getHitRect().setTranslateY(y);
    }

    /**
     * 座標と表示を同期する。
     * {@link #image}と{@link #view}も同期します。
     */
    public void synchronize() {
        view.setImage(image);
        view.setTranslateX(getX());
        view.setTranslateY(getY());
        beforeX = x;
        beforeY = y;
    }

    /**
     * 移動した差分を取得する。
     * {@link #synchronize()}を実行後に実行すると<b>必ず0が返る</b>ので注意してください。
     * @return X座標の差分
     */
    public double getDiffX() {
        return beforeX - x;
    }

    /**
     * 移動した差分を取得する。
     * {@link #synchronize()}を実行後に実行すると<b>必ず0が返る</b>ので注意してください。
     * @return Y座標の差分
     */
    public double getDiffY() {
        return beforeY - y;
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
        Bounds bounds = getHitRect().getBoundsInParent(), targetBounds = target.getHitRect().getBoundsInParent();

        double top = (bounds.getMinY() - targetBounds.getMaxY());
        double bottom = (bounds.getMaxY() - targetBounds.getMinY());

        double left = (bounds.getMinX() - targetBounds.getMaxX());
        double right = (bounds.getMaxX() - targetBounds.getMinX());

        //変数名 != (-)1は接触で動けなくなるのを防いでいる
        if (top < 0 && top > -10 && top != -1) {
            target.moveY(top);
            return Optional.of(PushBackDirection.TOP);
        } else if (left < 0 && left > -10 && left != -1) {
            target.moveX(left);
            return Optional.of(PushBackDirection.LEFT);
        } else if (bottom > 0 && bottom < 10 && bottom != 1) {
            target.moveY(bottom);
            return Optional.of(PushBackDirection.BOTTOM);
        } else if (right > 0 && right < 10 && bottom != 1) {
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
