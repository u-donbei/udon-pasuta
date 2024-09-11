/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.u_donbei.udon_pasuta.object.character;

import javafx.scene.image.Image;
import jp.u_donbei.udon_pasuta.object.Animation;
import jp.u_donbei.udon_pasuta.texture.TextureUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * うどんべい(プレイヤー)を表す。
 */
@Slf4j
public class Udonbei extends GameCharacter implements Animation {
	private final List<Image> TEXTURES;
	private int currentTextureIndex;

	public Udonbei() {
		super(TextureUtil.getTextureThrow("udon"));

		TEXTURES = List.of(getImage(), new Image(TextureUtil.getTextureThrow("udon_step").toUri().toString()));
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
