/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.object.character;

import javafx.scene.image.Image;
import jp.u_donbei.udon_pasuta.object.Animation;
import jp.u_donbei.udon_pasuta.texture.TextureUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * うどんべい(プレイヤー)を表す。
 */
public class Udonbei extends GameCharacter implements Animation {

	private final List<Image> textures;
	private int currentTextureIndex;

	public Udonbei() {
		super(TextureUtil.getTextureThrow("udon_step"));

		textures = new ArrayList<>() {
			{
				add(getImage());
				add(new Image(TextureUtil.getTextureThrow("udon_step").toUri().toString()));
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void nextTexture() {
		changeTexture(currentTextureIndex++ % 2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeTexture(int id) {
		setImage(textures.get(id));
	}
}
