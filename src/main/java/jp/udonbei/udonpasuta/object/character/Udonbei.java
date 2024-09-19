/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object.character;

import javafx.scene.image.Image;
import jp.udonbei.udonpasuta.object.Animation;
import jp.udonbei.udonpasuta.texture.TextureUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * うどんべい(プレイヤー)を表す。
 */
@Slf4j
public class Udonbei extends GameCharacter implements Animation {
    private static final List<Image> TEXTURES;
    private int currentTextureIndex;

    static {
        TEXTURES = List.of(
                new Image(TextureUtil.getTextureThrow("udon").toUri().toString()),
                new Image(TextureUtil.getTextureThrow("udon_step").toUri().toString()));
    }

    public Udonbei() {
        super(TextureUtil.getTextureThrow("udon"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextTexture() {
        changeTexture(++currentTextureIndex % 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeTexture(int id) {
        setImage(TEXTURES.get(id));
    }
}
