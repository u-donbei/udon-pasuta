/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.event;

import jp.udonbei.udonpasuta.control.TextPanel;
import jp.udonbei.udonpasuta.object.character.Udonbei;

import java.util.*;

/**
 * イベントを監視し、実行する。
 */
public final class EventManager {
    /**
     * 登録されたイベントを格納する。
     */
    private final List<Event> events = new ArrayList<>();

    /**
     * イベントを登録する。
     * @param event 登録するイベント
     * @return インデックス
     */
    public int register(Event event) {
        events.add(event);
        return events.indexOf(event);
    }

    /**
     * 登録されているイベントの一覧を返す。
     * リストはコピーされた物です。
     *
     * @return イベントの一覧
     */
    public List<Event> getRegisteredEvents() {
        List<Event> res = List.of();
        Collections.copy(events, res);
        return res;
    }

    /**
     * 登録されている全てのイベントを実行する。
     * @param player 使用するプレイヤー
     * @param textPanel 表示用のTextPanel
     */
    public void runEvents(Udonbei player, TextPanel textPanel) {
        events.forEach(event -> event.run(player, textPanel));
    }
}
