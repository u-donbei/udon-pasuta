package jp.u_donbei.udon_pasuta.control;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import jp.u_donbei.udon_pasuta.font.FontUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * テキストを少しずつ変更できるクラス。
 */
@Slf4j
public class TextPanel extends BorderPane {
	private Label textLabel;
	private ImageView imageView;
	private Button forwardButton;

	public TextPanel() {
		textLabel = new Label();
		imageView = new ImageView();
		forwardButton = new Button("▼");

		setCenter(textLabel);
		setLeft(imageView);
		setBottom(forwardButton);
		//進むボタンは最初は無効にしておく
		forwardButton.setDisable(true);

		//スタイルの設定
		textLabel.setTextFill(Color.WHITE);
		textLabel.setFont(FontUtil.getPixelMplus12Regular(20));
		setStyle("""
				-fx-background-color: rgba(0, 0, 0, 0.5);
				-fx-border-width: 2;
				-fx-border-color: white;
				-fx-border-insets: 5px;
				-fx-border-style: solid;
				""");
	}

	/**
	 * テキストを少しづつ変更する。
	 *
	 * @param text   変更するテキスト
	 * @param millis 一文字づつ変更する間隔
	 * @param image  表示する画像(nullの場合は無し)
	 */
	public void changeTextLittleByLittle(String text, long millis, Image image) {
		changeTextLittleByLittle(text, millis, image, () -> {});
	}

	/**
	 * テキストを少しづつ変更する。
	 *
	 * @param text   変更するテキスト
	 * @param millis 一文字づつ変更する間隔
	 * @param image  表示する画像(nullの場合は無し)
	 * @param textChangeExited テキストの変更が終了したときに呼び出す処理
	 */
	public void changeTextLittleByLittle(String text, long millis, Image image, Runnable textChangeExited) {
		//UIの初期化を行う
		if (image != null) {
			imageView.setImage(image);
		}

		StringReader reader = new StringReader(text);
		int length = text.length();
		AtomicBoolean isExit = new AtomicBoolean(false);

		//Serviceを利用してバックグラウンドで書き換える
		Service<Void> changeService = new Service<>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<>() {
					@Override
					protected Void call() {
						try {
							StringBuilder builder = new StringBuilder();
							for (int i = 0; i < length; i++) {
								builder.append((char) reader.read());
								updateMessage(builder.toString());
								TimeUnit.MILLISECONDS.sleep(millis);
							}
						} catch (IOException | InterruptedException e) {
							log.error("An Exception has occurred. text={}", text, e);
						}
						isExit.set(true);
						return null;
					}
				};
			}
		};
		textLabel.textProperty().bind(changeService.messageProperty());

		changeService.setOnSucceeded(e -> {
			textChangeExited.run();
			forwardButton.setDisable(false);
		});
		changeService.start();
	}
}
