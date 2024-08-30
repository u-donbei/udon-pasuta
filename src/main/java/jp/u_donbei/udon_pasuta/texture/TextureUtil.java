package jp.u_donbei.udon_pasuta.texture;

import jp.u_donbei.udon_pasuta.path.PathConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * テクスチャに関するユーティリティクラス
 */
public final class TextureUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(TextureUtil.class);
	/**
	 * テクスチャを取得する。
	 * 対応している形式は.png, .jpgです。
	 * @param name テクスチャのファイル名(拡張子は除く)
	 *             .pngと.jpgの両方のファイルが存在している場合、.pngが選択されます。
	 * @return テクスチャファイル。ファイルが存在しない場合は{@link Optional#empty()}
	 */
	public static Optional<Path> getTexture(String name) {
		Path base = Paths.get(PathConstants.TEXTURES.getPath());
		Path png = Paths.get(base.toString(), name + ".png");
		Path jpg = Paths.get(base.toString(), name + ".jpg");

		//png優先で探索
		if (Files.exists(png)) {
			return Optional.of(png);
		} else if (Files.exists(jpg)) {
			return Optional.of(jpg);
		} else {
			return Optional.empty();
		}
	}
}
