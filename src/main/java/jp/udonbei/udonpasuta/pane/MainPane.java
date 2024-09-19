/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.pane;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jp.udonbei.udonpasuta.control.TextPanel;
import jp.udonbei.udonpasuta.map.GameMap;
import jp.udonbei.udonpasuta.object.block.Block;
import jp.udonbei.udonpasuta.object.character.GameCharacter;
import lombok.Getter;

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
    private final SubScene subScene;
    private final List<GameCharacter> characters;
    private final GameMap map;

    /**
     * コンストラクタ。
     * mapを元にImageViewを追加するので処理に時間がかかる可能性があります。
     *
     * @param map 生成されたGameMap
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
     *
     * @param character 追加するキャラクター
     */
    public void addCharacter(GameCharacter character) {
        gamePane.getChildren().add(character.getView());
        characters.add(character);
        character.updateView();
    }

    /**
     * ブロックを追加する。
     *
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

    public List<GameCharacter> getCharacters() {
        return characters;
    }
}
