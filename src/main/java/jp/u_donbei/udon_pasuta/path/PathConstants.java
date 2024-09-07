/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.path;

import lombok.Getter;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public enum PathConstants {
	APP_HOME(System.getenv("LOCALAPPDATA") + "\\udon-pasuta\\app\\"),
	TEXTURES(APP_HOME + "textures\\");
	private String path;
	PathConstants (String path) {
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
}
