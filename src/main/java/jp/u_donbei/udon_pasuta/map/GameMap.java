/*
 * Copyright (c) 2024 u-donbei. All Rights Reserved.
 */

package jp.u_donbei.udon_pasuta.map;

import jp.u_donbei.udon_pasuta.object.block.Block;
import jp.u_donbei.udon_pasuta.object.block.DebugBlock;
import jp.u_donbei.udon_pasuta.object.block.Ground;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * ゲームのマップを表す。
 */
@Slf4j
public class GameMap {
	public static final int MAP_W = 80, MAP_H = 45;
	/**
	 * マップデータ。
	 */
	private Block[][] blocks;

	/**
	 * コンストラクタ。
	 *
	 * @param isBuild {@link #blocks}を初期化(ブロックを配置)するかどうか
	 */
	public GameMap(boolean isBuild) {
		blocks = new Block[MAP_W][MAP_H];
		if (isBuild) {
			build();
		}
	}

	/**
	 * {@link #blocks}を初期化(ブロックを配置)する。
	 */
	public void build() {
		for (int j = 0; j < blocks.length; j++) {
			Block[] blocks = this.blocks[j];
			for (int i = 0; i < blocks.length; i++) {
				Block block = new Ground();
				if (i == 5 && j == 5) {
					block = new DebugBlock();
				}
				if (i == 5 && j == 6) {
					block = new DebugBlock();
				}
				if (i == 5 && j == 7) {
					block = new DebugBlock();
				}
				block.setX(j * Block.DEFAULT_WIDTH);
				block.setY(i * Block.DEFAULT_HEIGHT);
				setBlock(block);
			}
		}
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	/**
	 * ブロックを追加する。
	 * blockの座標から配列のインデックスを計算します。
	 * @param block 追加するブロック
	 */
	public void setBlock(Block block) {
		blocks[(int) block.getX() / Block.DEFAULT_WIDTH][(int) block.getY() / Block.DEFAULT_HEIGHT] = block;
		block.updateView();
	}
}
