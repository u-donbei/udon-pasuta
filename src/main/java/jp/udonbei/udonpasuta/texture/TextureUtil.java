/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.texture;

import jp.udonbei.udonpasuta.path.PathConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
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
     *
     * @param name テクスチャのファイル名(拡張子は除く)
     *             .pngと.jpgの両方のファイルが存在している場合、.pngが選択されます。
     * @return テクスチャファイル。ファイルが存在しない場合は{@link Optional#empty()}
     */
    public static Optional<Path> getTexture(String name) {
        //パフォーマンス向上のため、nameがnullの場合は無条件でOptional.empty()を返す
        if (name == null) {
            return Optional.empty();
        }

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

    /**
     * テクスチャを取得する。
     * 対応している形式は.png, .jpgです。
     *
     * @param name テクスチャのファイル名(拡張子は除く)
     *             .pngと.jpgの両方のファイルが存在している場合、.pngが選択されます。
     * @return テクスチャファイル。
     * @throws IllegalStateException ファイルが存在しない場合
     */
    public static Path getTextureThrow(String name) {
        Optional<Path> path = getTexture(name);

        if (path.isEmpty()) {
            LOGGER.error("Texture {} not found.", name);
            throw new IllegalStateException("Texture" + name + " not found.");
        }
        return path.get();
    }
}
