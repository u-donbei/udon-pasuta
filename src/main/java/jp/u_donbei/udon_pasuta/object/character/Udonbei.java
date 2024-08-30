package jp.u_donbei.udon_pasuta.object.character;

import jp.u_donbei.udon_pasuta.texture.TextureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * うどんべい(プレイヤー)を表す。
 */
public class Udonbei extends GameCharacter {
	private static final Logger LOGGER = LoggerFactory.getLogger(Udonbei.class);

	public Udonbei() {
		super(TextureUtil.getTexture("udon").orElseThrow(() -> {
			LOGGER.error("Texture udon not found.");
			return new IllegalStateException("Texture udon not found.");
		}));
	}
}
