/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object;

/**
 * 動くことを表す。
 */
public interface Movable {
    /**
     * X方向に動く。
     *
     * @param x X方向の差分
     */
    void moveX(double x);

    /**
     * Y方向に動く。
     *
     * @param y Y方向の差分
     */
    void moveY(double y);
}
