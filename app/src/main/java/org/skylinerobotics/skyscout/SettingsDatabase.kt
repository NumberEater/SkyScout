package org.skylinerobotics.skyscout

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.RuntimeException

class SettingsDatabase(private val context: Context) {
    private val settingsFile = File(context.filesDir, "settings.json")
    private val settings: JsonObject

    init {
        if (!settingsFile.exists()) {
            settingsFile.createNewFile()
        }
        settings = loadSettingsJson()
    }

    public fun changeSetting(key: String, value: String) {
        settings.addProperty(key, value)
    }

    public fun loadSetting(key: String): String? {
        if (settings.has(key)) {
            return settings.get(key).asString
        }
        return null
    }

    public fun commit() {
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