package org.skylinerobotics.skyscout.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.Closeable

class SuggestionsDatabase(context: Context) : Closeable {
    private val database: SQLiteDatabase
    companion object {
        private const val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Suggestions(Name VARCHAR, Suggestion VARCHAR)"
    }

    init {
        database = context.openOrCreateDatabase(
            "suggestions.db",
            Context.MODE_PRIVATE,
            null
        )

        database.execSQL(CREATE_TABLE)
    }
    override fun close() {
        database.close()
    }

    fun addEntry(name: String, suggestion: String) {
        database.execSQL(
            "INSERT INTO Suggestions(Name, Suggestion) VALUES(\"$name\", \"$suggestion\")"
        )
    }
}
