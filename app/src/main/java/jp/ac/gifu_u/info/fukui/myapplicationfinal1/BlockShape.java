package jp.ac.gifu_u.info.fukui.myapplicationfinal1;

import android.graphics.Color;

import java.util.Random;

public class BlockShape {
    public int[][] shape;
    public int x, y;
    public int color;

    public BlockShape(int[][] shape, int color) {
        this.shape = shape;
        this.color = color;
        this.x = 3;
        this.y = 0;
    }

    private static final int[][][] SHAPES = {
            {{1, 1, 1, 1}},                // I型
            {{1, 1}, {1, 1}},              // O型
            {{0, 1, 0}, {1, 1, 1}},        // T型
            {{0, 1, 1}, {1, 1, 0}},        // S型
            {{1, 1, 0}, {0, 1, 1}},        // Z型
            {{1, 0, 0}, {1, 1, 1}},        // J型
            {{0, 0, 1}, {1, 1, 1}},        // L型
            //{{0, 1, 1}, {1, 1, 1}},        // b型
            //{{1, 1, 0}, {1, 1, 1}}         // d型

    };

    private static final int[] COLORS = {
            Color.CYAN,        // I型
            Color.YELLOW,      // O型
            Color.MAGENTA,     // T型
            Color.GREEN,       // S型
            Color.RED,         // Z型
            Color.BLUE,        // J型
            Color.rgb(255,165,0) // L型 (オレンジ)
    };

    public static BlockShape createRandom() {
        Random rand = new Random();
        int index = rand.nextInt(SHAPES.length);
        return new BlockShape(SHAPES[index], COLORS[index]);
    }

    class Block {
        public int x;
        public int y;

        public Block(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
