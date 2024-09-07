/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.pane;

import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jp.u_donbei.udon_pasuta.control.TextPanel;
import jp.u_donbei.udon_pasuta.map.GameMap;
import jp.u_donbei.udon_pasuta.object.block.Block;
import jp.u_donbei.udon_pasuta.object.character.GameCharacter;
import lombok.Getter;

import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ゲームのメインペイン。
 */
public class MainPane extends AnchorPane {
	/**
	 * ゲームのブロックやキャラクターを配置する
	 */
	private Pane gamePane;
	/**
	 * テキストを表示する。
	 */
	@Getter
	private TextPanel textPanel;
	/**
	 * スクロールするためのSubScene
	 */
	private SubScene subScene;
	private List<GameCharacter> characters;
	private GameMap map;

	/**
	 * コンストラクタ。
	 * mapを元にImageViewを追加するので処理に時間がかかる可能性があります。
	 * @param map
	 */
	public MainPane(GameMap map) {
		gamePane = new Pane();
		textPanel = new TextPanel();
		subScene = new SubScene(gamePane, 800, 500);
		Camera camera = new PerspectiveCamera();
		subScene.setCamera(camera);

		characters = new ArrayList<>();
		this.map = map;

		//表示位置・大きさの設定
		AnchorPane.setTopAnchor(subScene, 0d);
		AnchorPane.setLeftAnchor(subScene, 0d);
		AnchorPane.setRightAnchor(subScene, 0d);

		AnchorPane.setBottomAnchor(textPanel, 0d);
		AnchorPane.setLeftAnchor(textPanel, 0d);
		AnchorPane.setRightAnchor(textPanel, 0d);

		textPanel.setPrefHeight(200d);

		getChildren().addAll(subScene, textPanel);

		//ImageViewを追加
		for (Block[] blocks : map.getBlocks()) {
			for (Block block : blocks) {
				addBlock(block);
			}
		}
	}

	/**
	 * キャラクターを追加する。
	 * @param character 追加するキャラクター
	 */
	public void addCharacter(GameCharacter character) {
		gamePane.getChildren().add(character.getView());
		characters.add(character);
		character.updateView();
	}

	/**
	 * ブロックを追加する。
	 * @param block 追加するブロック
	 */
	public void addBlock(Block block) {
		gamePane.getChildren().add(block.getView());
		map.setBlock(block);
	}

	public Camera getCamera() {
		return subScene.getCamera();
	}

	public List<Block> getGameMap() {
		List<Block> res = new ArrayList<>();
		for (Block[] blocks : map.getBlocks()) {
			res.addAll(Arrays.asList(blocks));
		}
		return res;
	}
}
