package jp.u_donbei.udon_pasuta.object;

/**
 * 動くことを表す。
 */
public interface Movable {
	/**
	 * X方向に動く。
	 * @param x X方向の差分
	 */
	void moveX(int x);
	/**
	 * Y方向に動く。
	 * @param y Y方向の差分
	 */
	void moveY(int y);
}
