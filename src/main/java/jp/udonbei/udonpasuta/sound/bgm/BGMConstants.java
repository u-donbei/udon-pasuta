/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.sound.bgm;

import jp.udonbei.udonpasuta.path.PathConstants;

import java.nio.file.Paths;

public enum BGMConstants {
    FIELD(new BGM(Paths.get(PathConstants.BGM.getPath(), "field.mp3")));
    private final BGM se;

    BGMConstants(BGM se) {
        this.se = se;
    }

    public BGM getSE() {
        return se;
    }
}
