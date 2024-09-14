/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.u_donbei.udon_pasuta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jp.u_donbei.udon_pasuta.managers.AppStartManager;
import jp.u_donbei.udon_pasuta.path.PathConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static int exitCode = 0;
	public static void main(String[] args) {
		LOGGER.info("Application Start.");

		//引数に応じて開発環境か否かを設定する
		LOGGER.info("Setting develop environment.");
		if (args.length == 0) {
			production();
		} else {
			String develop = args[0];
			if (develop.equals("--develop")) {
				LOGGER.info("Using develop environment.");
				System.setProperty("udon.develop", "true");
			} else {
				production();
			}
		}
		launch(args);
		LOGGER.info("Application Exit.");
		System.exit(exitCode);
	}

	private static void production() {
		LOGGER.info("Using production environment.");
		System.setProperty("udon.develop", "false");
	}

	@Override
	public void start(Stage stage)  {
		Pane main = null;
		try {
			LOGGER.debug("debug test");
			LOGGER.info("Loading FXML.");
			main = FXMLLoader.load(getClass().getResource("/title.fxml"));
		} catch (IOException e) {
			LOGGER.error("FXML Load failed: ", e);
			exitCode = 1;
			Platform.exit();
		}

		Scene mainScene = new Scene(main);

		stage.setScene(mainScene);
        stage.setTitle("うどんべいのパスタ退治");
		stage.getIcons().add(new Image(PathConstants.TEXTURES.toURI() + "/udon.png"));
        stage.show();

		LOGGER.info("Waiting start.");
		AppStartManager.waitStart(stage);
	}
}
