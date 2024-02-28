package org.skylinerobotics.skyscout.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.skylinerobotics.skyscout.Constants
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.settings.SettingsDatabase

class ScoutTypeSelectionDialog(private val context: Context, private val onFinishCallback: (scoutType: Constants.ScoutType) -> Unit) {
    private val dialog: AlertDialog

    private lateinit var matchScoutButton: Button
    private lateinit var pitScoutButton: Button

    init {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Select Type")
        val layout = inflateDialogLayoutView()

        matchScoutButton = layout.findViewById(R.id.match_scout_button)
        matchScoutButton.setOnClickListener { matchScoutButtonAction() }

        pitScoutButton = layout.findViewById(R.id.pit_scout_button)
        pitScoutButton.setOnClickListener { pitScoutButtonAction() }

        builder.setView(layout)

        dialog = builder.create()
    }

    private fun inflateDialogLayoutView(): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.dialog_scout_type_selection, null)
    }

    private fun matchScoutButtonAction() {
        onFinishCallback(Constants.ScoutType.MATCH)
    }

    private fun pitScoutButtonAction() {
        onFinishCallback(Constants.ScoutType.PIT)
    }

    fun show() {
        dialog.show()
    }
}