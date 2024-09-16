/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.u_donbei.udon_pasuta.object.block;

import javafx.geometry.Bounds;
import jp.u_donbei.udon_pasuta.object.GameObject;
import jp.u_donbei.udon_pasuta.object.character.GameCharacter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Optional;

/**
 * ブロック(地面、建物など)を表す。
 */
@Slf4j
public abstract class Block extends GameObject {
	public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;
	public Block(Path image, boolean contactable) {
		super(image);
		setContactable(contactable);
	}
}
