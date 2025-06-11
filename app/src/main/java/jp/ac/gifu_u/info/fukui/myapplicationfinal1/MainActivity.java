package jp.ac.gifu_u.info.fukui.myapplicationfinal1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView;
    private long lastMoveTime = 0;
    private static final long MOVE_INTERVAL = 300; // 移動の間隔

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float accelX = event.values[0];
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastMoveTime > MOVE_INTERVAL) {
            if (accelX > 3.0f) {//~fで傾けた時のブロックの移動速度を変えられる
                gameView.moveBlockLeft();
                lastMoveTime = currentTime;
            } else if (accelX < -3.0f) {
                gameView.moveBlockRight();
                lastMoveTime = currentTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}//不要
}
