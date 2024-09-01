package jp.u_donbei.udon_pasuta.object.block;

import jp.u_donbei.udon_pasuta.path.PathConstants;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * デバッグ用のブロック
 */
public class DebugBlock extends Block {
	public DebugBlock() {
		super(Paths.get(PathConstants.TEXTURES.getPath() + "debug.png"));
	}
}
