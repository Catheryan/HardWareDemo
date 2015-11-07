package com.test.hardware;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActionSensorActivity extends Activity implements SensorEventListener {
	private TextView tv;
	private TextView typeall;
	private SensorManager sensorManager;
	private float[] gravity = new float[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.showmsg);
		typeall = (TextView) findViewById(R.id.typeall);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> sensors =sensorManager.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : sensors) {
			typeall.append(sensor.getName()+"\n");
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			final float alpha = (float) 0.8;
			gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
			gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
			gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

			String accelerometer = "º”ÀŸ∂»\n" + "X:"
					+ (event.values[0] - gravity[0]) + "\n" + "Y:"
					+ (event.values[1] - gravity[1]) + "\n" + "Z:"
					+ (event.values[2] - gravity[2]);
			Log.d("z", String.valueOf(event.values[2] - gravity[2]));
			tv.setText(accelerometer);
			break;
		case Sensor.TYPE_GRAVITY:
			gravity[0] = event.values[0];
			gravity[1] = event.values[1];
			gravity[2] = event.values[2];
			break;

		default:
			break;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

}
