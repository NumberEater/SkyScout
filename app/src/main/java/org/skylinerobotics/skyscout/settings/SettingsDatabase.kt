package org.skylinerobotics.skyscout.settings

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File

class SettingsDatabase(context: Context) {

    private val settingsFile = File(context.filesDir, "settings.json")
    private val settings: JsonObject

    init {
        if (!settingsFile.exists()) {
            settingsFile.createNewFile()
        }
        settings = loadSettingsJson()
    }

    fun changeSetting(key: String, value: String) {
        settings.addProperty(key, value)
    }

    fun loadSetting(key: String): String? {
        if (settings.has(key)) {
            return settings.get(key).asString
        }
        return null
    }

    fun commit() {
        settingsFile.writeText(Gson().toJson(settings))
    }

    private fun loadSettingsJson(): JsonObject {
        if (getSettingsString() == "") {
            return JsonObject()
        }
        return Gson().fromJson(getSettingsString(), JsonObject::class.java)
    }

    private fun getSettingsString(): String {
        return settingsFile.readText()
    }
}