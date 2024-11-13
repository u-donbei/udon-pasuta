/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.event;

import jp.udonbei.udonpasuta.control.TextPanel;
import jp.udonbei.udonpasuta.function.TriConsumer;
import jp.udonbei.udonpasuta.object.character.Udonbei;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * イベント(トリガー処理)を表す。
 */
@Slf4j
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Event {
    /**
     * 管理用のID
     */
    @NonNull
    private final String id;
    /**
     * トリガーを引いたときに実行される処理
     */
    @NonNull
    private final TriConsumer<Udonbei, TextPanel, Map<String, Object>> run;

    /**
     * 実行するかどうかの判断に使うPredicate
     */
    @Builder.Default
    private final Predicate<Udonbei> judgement = udonbei -> true;
    /**
     * 実行の際に必要としている引数
     */
    @Builder.Default
    private final Map<String, Class<?>> requiredArgs = new HashMap<>();

    /**
     * 条件を満たした場合は{@link #run}の処理を実行する。
     *
     * @param udonbei 判断に使うプレイヤー
     * @param textPanel 表示用のTextPanel
     */
    public void run(Udonbei udonbei, TextPanel textPanel) {
        run(udonbei, textPanel, new HashMap<>());
    }

    /**
     * 条件を満たした場合は{@link #run}の処理を実行する。
     *
     * @param udonbei 判断に使うプレイヤー
     * @param textPanel 表示用のTextPanel
     * @param args 渡す引数
     */
    public void run(Udonbei udonbei, TextPanel textPanel, Map<String, Object> args) {
        if (judgement.test(udonbei)) {
            if (!checkArgs(args)) {
                throw new IllegalArgumentException("argsが不正です。正しい値は" + requiredArgs + "です。");
            }
            run.accept(udonbei, textPanel, args);
        }
    }

    /**
     * 引数の数、型などが{@link #requiredArgs}と一致しているか確認する
     * @param args 確認する引数
     * @return 確認した結果
     */
    private boolean checkArgs(Map<String, Object> args) {
        if (requiredArgs.size() != args.size()) {
            log.warn("args.size() is invalid");
            return false;
        }
        for (String key : requiredArgs.keySet()) {
            if (!args.containsKey(key)) {
                return false;
            }
            if (!(args.get(key).getClass() == requiredArgs.get(key))) {
                return false;
            }
        }
        return true;
    }
}
