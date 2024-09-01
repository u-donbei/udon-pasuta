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
		for (Block[] blocks : blocks) {
			for (int i = 0; i < blocks.length; i++) {
				blocks[i] = new Ground();
				if (i == 10) {
					blocks[i] = new DebugBlock();
				}
				if (i == 11) {
					log.debug("11.{}", blocks[i - 1]);
				}
			}
		}
	}

	public Block[][] getBlocks() {
		return blocks;
	}
}
