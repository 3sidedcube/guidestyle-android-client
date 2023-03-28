package com.cube.styleguide.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

/**
 * This class will register a listener for device's accelerator and checks for shake actions
 * In case of a shake action, it will open up the style guide bottom sheet
 */
open class ShakeSensorListener(private val handler: StyleGuideHandler) : SensorEventListener {
	companion object {
		fun createInstance(handler: StyleGuideHandler) {
			ShakeSensorListener(handler)
		}
	}

	private var acceleration = 0f
	private var currentAcceleration = 0f
	private var lastAcceleration = 0f

	fun registerListener() {
		handler.sensorManager.registerListener(this, handler.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
		)
	}

	fun unregisterListener() {
		handler.sensorManager.unregisterListener(this)
	}

	override fun onSensorChanged(event: SensorEvent?) {
		if (event == null) {
			return
		}
		val x = event.values[0]
		val y = event.values[1]
		val z = event.values[2]
		lastAcceleration = currentAcceleration

		currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
		val delta: Float = currentAcceleration - lastAcceleration
		acceleration = acceleration * 0.9f + delta

		if (acceleration > 12) {
			handler.onShakeDetected()
		}
	}

	override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
		// Empty
	}

	/**
	 * The activity that wants to use this class, needs to implement this interface
	 */
	interface StyleGuideHandler {
		fun onShakeDetected()
		val sensorManager: SensorManager
		fun registerListener()
		fun unregisterListener()
	}
}