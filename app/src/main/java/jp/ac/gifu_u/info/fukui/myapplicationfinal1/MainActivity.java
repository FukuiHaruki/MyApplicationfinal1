package jp.ac.gifu_u.info.fukui.myapplicationfinal1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        // センサー準備
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer == null) {
                Toast.makeText(this, "加速度センサーが利用できません", Toast.LENGTH_LONG).show();
            }
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
        float accelX = event.values[0]; // X軸方向の加速度（左右の傾き）

        if (accelX > 5.0f) {//?fでどれくらい傾けたら動くのかを決められる
            gameView.moveBlockLeft();  // 端末を左に傾けたら左へ移動
        } else if (accelX < -5.0f) {
            gameView.moveBlockRight(); // 端末を右に傾けたら右へ移動
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 不要
    }
}
