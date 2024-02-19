package org.skylinerobotics.skyscout.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.settings.SettingsDatabase

class TeamNumberDialog(private val context: Context, private val onFinishCallback: (teamNumber: Int) -> Unit) {
    private var teamNumber = 0
    private val dialog: AlertDialog
    private val teamNumberEntry: EditText

    init {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Please enter team number.")
        builder.setMessage(getScoutingPosition())

        val layout = inflateDialogLayoutView()
        teamNumberEntry = layout.findViewById(R.id.team_number_entry)
        builder.setView(layout)

        builder.setPositiveButton("Done") { _, _, ->
            doneButtonAction()
        }

        dialog = builder.create()
    }

    private fun getScoutingPosition(): String {
        val database = SettingsDatabase(context)
        return database.loadSetting("scouting-position") ?: return ""
    }

    private fun doneButtonAction() {
        if (isNumberValid()) {
            teamNumber = parseTeamNumber()
        } else {
            teamNumber = 0
        }
        onFinishCallback(teamNumber)
    }

    private fun isNumberValid(): Boolean {
        val entryValue = teamNumberEntry.text.toString()

        return entryValue != ""
    }

    private fun inflateDialogLayoutView(): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.dialog_team_number, null)
    }

    private fun parseTeamNumber(): Int {
        val entryValue = teamNumberEntry.text.toString()
        return entryValue.toInt()
    }


    fun show() {
        dialog.show()
    }
}