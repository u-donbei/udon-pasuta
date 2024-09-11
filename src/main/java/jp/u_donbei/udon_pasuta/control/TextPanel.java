/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.u_donbei.udon_pasuta.control;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jp.u_donbei.udon_pasuta.font.FontUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * テキストを少しずつ変更できるクラス。
 */
@Slf4j
public class TextPanel extends BorderPane {
	private final Label textLabel;
	private final ImageView imageView;
	private final Button forwardButton;

	public TextPanel() {
		textLabel = new Label();
		imageView = new ImageView();
		forwardButton = new Button("▼");

		setCenter(textLabel);
		setLeft(imageView);
		setBottom(forwardButton);
		//進むボタンは最初は無効にしておく
		forwardButton.setDisable(true);
		forwardButton.setVisible(false);

		//スタイルの設定
		textLabel.setTextFill(Color.WHITE);
		textLabel.setFont(FontUtil.getPixelMplus12Regular(20));

		getStyleClass().add("text-panel");

		forwardButton.getStyleClass().add("forward-button");
		forwardButton.setFont(FontUtil.getPixelMplus12Regular(12));
		BorderPane.setAlignment(forwardButton, Pos.CENTER);
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
		changeTextLittleByLittle(text, millis, image, textChangeExited, () -> {});
	}

	/**
	 * テキストを少しづつ変更する。
	 *
	 * @param text   変更するテキスト
	 * @param millis 一文字づつ変更する間隔
	 * @param image  表示する画像(nullの場合は無し)
	 * @param textChangeExited テキストの変更が終了したときに呼び出す処理
	 * @param forwarded ▼ボタンが押された時の処理
	 */
	public void changeTextLittleByLittle(String text, long millis, Image image, Runnable textChangeExited, Runnable forwarded) {
		changeTextLittleByLittle(text, millis, image, textChangeExited, forwarded, false);
	}

	/**
	 * テキストを少しづつ変更する。
	 *
	 * @param text   変更するテキスト
	 * @param millis 一文字づつ変更する間隔
	 * @param image  表示する画像(nullの場合は無し)
	 * @param textChangeExited テキストの変更が終了したときに呼び出す処理
	 * @param forwarded ▼ボタンが押された時の処理
	 * @param isEnterNestedLoop {@link Platform#enterNestedEventLoop(Object)}でネストされたイベントループに入り、処理を一時停止するかどうか
	 */
	public void changeTextLittleByLittle(String text, long millis, Image image, Runnable textChangeExited, Runnable forwarded, boolean isEnterNestedLoop) {
		//UIの初期化を行う
		forwardButton.setDisable(true);
		forwardButton.setVisible(true);
		if (image != null) {
			imageView.setImage(image);
		}

		StringReader reader = new StringReader(text);
		Service<Void> changeService = getChangeService(text, millis, reader, isEnterNestedLoop);
		textLabel.textProperty().bind(changeService.messageProperty());

		changeService.setOnSucceeded(e -> {
			textChangeExited.run();
			forwardButton.setDisable(false);
			forwardButton.requestFocus();
		});

		forwardButton.setOnAction(e -> {
			//ラベルの書き換えができないため、バインドを解除する
			textLabel.textProperty().unbind();
			forwarded.run();
			textLabel.setText("");
			forwardButton.setDisable(true);
			forwardButton.setVisible(false);
		});
		changeService.start();
	}

	private static Service<Void> getChangeService(String text, long millis, StringReader reader, boolean isEnterNestedLoop) {
		int length = text.length();

		//Serviceを利用してバックグラウンドで書き換える
		return new Service<>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<>() {
					@Override
					protected Void call() {
						try {
							AtomicReference<Object> rval = new AtomicReference<>();
							if (isEnterNestedLoop) {
								Platform.runLater(() -> rval.set(Platform.enterNestedEventLoop("text-panel-change-text-loop")));
							}
							StringBuilder builder = new StringBuilder();
							for (int i = 0; i < length; i++) {
								builder.append((char) reader.read());
								updateMessage(builder.toString());
								TimeUnit.MILLISECONDS.sleep(millis);
							}
							if (isEnterNestedLoop) {
								Platform.runLater(() -> Platform.exitNestedEventLoop("text-panel-change-text-loop", rval.get()));
							}
						} catch (Exception e) {
							log.error("An Exception has occurred. text={}", text, e);
						}
						return null;
					}
				};
			}
		};
	}
}
