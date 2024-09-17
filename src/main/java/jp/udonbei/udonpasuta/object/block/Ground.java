/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.object.block;

import jp.udonbei.udonpasuta.texture.TextureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ground extends Block {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ground.class);

    public Ground() {
        super(TextureUtil.getTexture("ground").orElseThrow(() -> {
            LOGGER.error("Texture ground not found.");
            return new IllegalStateException("Texture ground not found.");
        }), false);
    }
}
