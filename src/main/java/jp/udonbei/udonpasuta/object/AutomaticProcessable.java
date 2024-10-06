/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object;

/**
 * ゲームループのティックに合わせて自動で何らかの処理をすることができることを表す。
 */
public interface AutomaticProcessable {
    /**
     * 自動処理を行う。
     */
    void handle();
}
