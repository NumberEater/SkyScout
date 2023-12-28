package org.skylinerobotics.skyscout

import android.app.Dialog
import android.content.Context

class TeamNumberDialog(context: Context) {
    private var teamNumber = 0
    private val dialog = Dialog(context)

    fun show() {

    }

    fun getTeamNumber(): Int {
        return teamNumber
    }
}