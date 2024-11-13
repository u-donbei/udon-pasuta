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
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * イベントを監視し、実行する。
 */
@Slf4j
public final class EventManager {
    /**
     * 登録されたイベントを格納する。
     */
    private final List<Event> events = new ArrayList<>();
    private final Map<Integer, Map<String, Object>> args = new HashMap<>();

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
     * イベントに渡す引数を設定する。
     * @param index イベントのインデックス
     * @param args 渡す引数
     */
    public void setArgs(int index, Map<String, Object> args) {
        this.args.put(index, args);
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
        events.forEach(event -> {
            if (args.containsKey(events.indexOf(event))) {
                log.debug("args={}", args.get(events.indexOf(event)));
                event.run(player, textPanel, args.get(events.indexOf(event)));
            } else {
                event.run(player, textPanel);
            }
        });
    }
}
