package com.codehunters.presenter

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences

class UserData {
    companion object {
        private const val mmol = "mmol/L"
        private const val mgdl = "mg/dL"
        private const val lo_th = "lo_threshold"
        private const val hi_th = "hi_threshold"
        private const val db_path = "db_path"
        private const val UNIT = "unit"
        fun get_multiplier(c: Context) : Float {
            return if(get_unit(c) == mmol) 1.0f else 18.0f
        }
        fun get_unit(c: Context) : String? {
            return getDefaultSharedPreferences(c).getString("unit", mmol)
        }
    }
}