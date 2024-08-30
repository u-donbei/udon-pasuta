package jp.u_donbei.udon_pasuta.object.block;

import jp.u_donbei.udon_pasuta.path.PathConstants;
import jp.u_donbei.udon_pasuta.texture.TextureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Ground extends Block {
	private static final Logger LOGGER = LoggerFactory.getLogger(Ground.class);
	public Ground() {
		super(TextureUtil.getTexture("ground").orElseThrow(() -> {
			LOGGER.error("Texture ground not found.");
			return new IllegalStateException("Texture ground not found.");
		}));
	}
}
