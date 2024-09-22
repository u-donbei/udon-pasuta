/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udon_pasuta.test;

import jp.udonbei.udonpasuta.path.PathConstants;
import jp.udonbei.udonpasuta.texture.TextureUtil;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TextureUtilTest {
    @Test
    public void getTextureTest() {
        //正常系:存在するpngをロードできるか
        Optional<Path> res = TextureUtil.getTexture("udon");
        if (res.isPresent()) {
            assertEquals(res.get(), Paths.get(PathConstants.TEXTURES + "udon.png"));
        } else {
            fail();
        }

        //正常系:存在するjpgをロードできるか
        res = TextureUtil.getTexture("test1");
        if (res.isPresent()) {
            assertEquals(res.get(), Paths.get(PathConstants.TEXTURES + "test1.jpg"));
        } else {
            fail();
        }

        //正常系:同名のpngとjpgの両方が存在する場合、pngが優先されるか
        res = TextureUtil.getTexture("test");
        if (res.isPresent()) {
            assertEquals(res.get(), Paths.get(PathConstants.TEXTURES + "test.png"));
        } else {
            fail();
        }

        //異常系:引数がnullの場合、Optional.empty()が返されるか
        res = TextureUtil.getTexture(null);
        assertEquals(res, Optional.empty());

        //異常系:引数の画像が存在しない場合、Optional.empty()が返されるか
        res = TextureUtil.getTexture("hoge");
        assertEquals(res, Optional.empty());
    }

    @Test
    public void getTextureThrowTest() {
        //正常系:存在するpngをロードできるか
        assertDoesNotThrow(() -> {
            TextureUtil.getTextureThrow("udon");
        });

        //正常系:存在するjpgをロードできるか
        assertDoesNotThrow(() -> {
            TextureUtil.getTextureThrow("test1");
        });

        //正常系:同名のpngとjpgの両方が存在する場合、pngが優先されるか
        Path img = TextureUtil.getTextureThrow("test");
        assertEquals(img, Paths.get(PathConstants.TEXTURES + "test.png"));

        //異常系:引数がnullの場合、IllegalStateExceptionがスローされるか
        assertThrows(IllegalStateException.class, () -> TextureUtil.getTextureThrow(null));

        //異常系:引数の画像が存在しない場合、Optional.empty()が返されるか
        assertThrows(IllegalStateException.class, () -> TextureUtil.getTextureThrow("hoge"));
    }

}
