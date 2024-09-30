/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.path;

import jp.udonbei.udonpasuta.Main;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Slf4j
public enum PathConstants {
    APP_HOME(System.getenv("LOCALAPPDATA") + "\\udon-pasuta\\app\\"),
    TEXTURES(APP_HOME + "textures\\"),
    BGM(APP_HOME + "se\\");
    private String path;

    PathConstants(String path) {
        this.path = path;
    }

    public File toFile() {
        return new File(getPath());
    }

    public Path toPath() {
        return Paths.get(getPath());
    }

    public URI toURI() {
        return toPath().toUri();
    }

    @Override
    public String toString() {
        return path;
    }
    public static void update() {
        APP_HOME.path = Main.isDevelop() ? "input\\" : System.getenv("LOCALAPPDATA") + "\\udon-pasuta\\app\\";
        TEXTURES.path = APP_HOME + "textures\\";
        BGM.path = APP_HOME + "se\\" ;
    }
}
