/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.object;

/**
 * 動くことを表す。
 */
public interface Movable {
	/**
	 * X方向に動く。
	 * @param x X方向の差分
	 */
	void moveX(double x);
	/**
	 * Y方向に動く。
	 * @param y Y方向の差分
	 */
	void moveY(double y);
}
