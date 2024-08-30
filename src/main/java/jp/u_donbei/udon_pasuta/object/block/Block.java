package jp.u_donbei.udon_pasuta.object.block;

import jp.u_donbei.udon_pasuta.object.GameObject;

import java.nio.file.Path;

/**
 * ブロック(地面、建物など)を表す。
 */
public abstract class Block extends GameObject {
	public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;
	public Block(Path image) {
		super(image);
	}
}
