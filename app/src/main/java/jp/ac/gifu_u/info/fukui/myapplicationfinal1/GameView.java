package jp.ac.gifu_u.info.fukui.myapplicationfinal1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint;
    private int blockX = 100;
    private int blockY = 100;
    private SurfaceHolder holder;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void moveBlockRight() {
        blockX += 30;
        draw();
    }

    public void moveBlockLeft() {
        blockX -= 30;
        draw();
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawRect(blockX, blockY, blockX + 100, blockY + 100, paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }
}
