package jp.ac.gifu_u.info.fukui.myapplicationfinal1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int cols = 10; // 横10
    private final int rows = 20; // 縦20
    private final int blockSize = 60;

    private BlockShape currentBlock;
    private Paint paint;
    private SurfaceHolder holder;
    private Handler handler = new Handler();
    private Runnable gameLoop;

    private int[][] field = new int[rows][cols]; // 各セルの色（0=空）

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();

        currentBlock = BlockShape.createRandom(); // 初期位置

        gameLoop = new Runnable() {
            @Override
            public void run() {
                update();
                draw();
                handler.postDelayed(this, 1000); // 1秒ごとに更新
            }
        };
    }

    public void moveBlockLeft() {
        if (canMove(-1, 0)) {
            currentBlock.x--;
            draw();
        }
    }

    public void moveBlockRight() {
        if (canMove(1, 0)) {
            currentBlock.x++;
            draw();
        }
    }

    private void update() {
        if (canMove(0, 1)) {
            currentBlock.y++;
        } else {
            // 固定処理
            for (int i = 0; i < currentBlock.shape.length; i++) {
                for (int j = 0; j < currentBlock.shape[i].length; j++) {
                    if (currentBlock.shape[i][j] != 0) {
                        int fx = currentBlock.x + j;
                        int fy = currentBlock.y + i;
                        if (fy >= 0 && fy < rows && fx >= 0 && fx < cols) {
                            field[fy][fx] = currentBlock.color; // 色を保存
                        }
                    }
                }
            }
            currentBlock = BlockShape.createRandom();
        }
    }

    private boolean canMove(int dx, int dy) {
        for (int i = 0; i < currentBlock.shape.length; i++) {
            for (int j = 0; j < currentBlock.shape[i].length; j++) {
                if (currentBlock.shape[i][j] != 0) {
                    int newX = currentBlock.x + j + dx;
                    int newY = currentBlock.y + i + dy;
                    if (newX < 0 || newX >= cols || newY >= rows) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.CYAN);

            // フィールドの固定ブロック描画
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    if (field[y][x] != 0) {
                        paint.setColor(field[y][x]);
                        int left = x * blockSize;
                        int top = y * blockSize;
                        canvas.drawRect(left, top, left + blockSize, top + blockSize, paint);
                    }
                }
            }

            // 現在のブロックを描画
            for (int i = 0; i < currentBlock.shape.length; i++) {
                for (int j = 0; j < currentBlock.shape[i].length; j++) {
                    if (currentBlock.shape[i][j] != 0) {
                        int left = (currentBlock.x + j) * blockSize;
                        int top = (currentBlock.y + i) * blockSize;
                        canvas.drawRect(left, top, left + blockSize, top + blockSize, paint);
                    }
                }
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        handler.post(gameLoop); // ゲーム開始
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        handler.removeCallbacks(gameLoop); // 停止
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
}
