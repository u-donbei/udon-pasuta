/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.sound.se;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Data;

import java.nio.file.Path;
import java.util.Objects;

/**
 * 一つのSEを表す。
 */
@Data
public final class SE {
    private final Path soundFile;
    private final Media media;
    private final MediaPlayer player;
    private boolean endOfMedia;

    /**
     * コンストラクタ。
     * @param soundFile 音のファイル。mp3は確実に使用できます。
     */
    public SE(Path soundFile) {
        this.soundFile = soundFile;
        this.media = new Media(soundFile.toUri().toString());
        player = new MediaPlayer(media);
        player.setOnEndOfMedia(() -> endOfMedia = true);
        player.setOnStopped(() -> endOfMedia = true);
    }

    /**
     * 音を再生する。
     */
    public void play() {
        endOfMedia = false;
        player.play();
    }

    /**
     * 音を停止する。
     * @return 停止したかどうか。再生されていない場合はfalseを返します。
     */
    public boolean stop() {
        if (endOfMedia) {
            player.stop();
            return true;
        }
        return false;
    }

    /**
     * 音量を設定する。
     * @param volume 音量
     */
    public void setVolume(int volume) {
        player.setVolume((double) volume / 100);
    }
}
