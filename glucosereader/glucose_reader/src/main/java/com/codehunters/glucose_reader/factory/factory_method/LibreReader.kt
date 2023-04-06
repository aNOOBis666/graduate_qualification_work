package com.codehunters.glucose_reader.factory.factory_method

import com.codehunters.glucose_reader.factory.GlucoseCreator
import com.codehunters.glucose_reader.sensor.Sensor
import com.codehunters.glucose_reader.sensor.typed_sensors.LibreSensor

class LibreReader: GlucoseCreator {
    override fun connectSensor(): Sensor {
        return LibreSensor()
    }
}