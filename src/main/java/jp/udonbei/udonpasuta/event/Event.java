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
import lombok.*;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * イベント(トリガー処理)を表す。
 */
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
    private final BiConsumer<Udonbei, TextPanel> run;

    /**
     * 実行するかどうかの判断に使うPredicate
     */
    @Builder.Default()
    private final Predicate<Udonbei> judgement = args -> true;

    /**
     * 条件を満たした場合は{@link #run}の処理を実行する。
     *
     * @param udonbei 判断に使うプレイヤー
     * @param textPanel 表示用のTextPanel
     */
    public void run(Udonbei udonbei, TextPanel textPanel) {
        if (judgement.test(udonbei)) {
            run.accept(udonbei, textPanel);
        }
    }
}
