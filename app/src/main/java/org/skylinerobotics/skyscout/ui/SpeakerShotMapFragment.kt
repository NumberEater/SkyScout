package org.skylinerobotics.skyscout.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datahandler.SpeakerShotDataHandler
import org.skylinerobotics.skyscout.data.datahandler.TeleopDataHandler
import org.skylinerobotics.skyscout.settings.SettingsDatabase

class SpeakerShotMapFragment(
    private val callingFragment: GameScoutFragment,
    private val shotDataHandler: SpeakerShotDataHandler,
    private val teleopDataHandler: TeleopDataHandler,
    private val scored: Boolean,
    private val position: String?) : Fragment() {

    private lateinit var fieldMapImageView: ImageView
    private lateinit var fieldMapBitmap: Bitmap

    private var fieldMapWidth = 0
    private var fieldMapHeight = 0

    private var currentShotX = 0f
    private var currentShotY = 0f

    private var fieldOrientation = 0

    private var isFieldBitmapInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_speaker_shot_map, container, false)

        setButtonActions(layout)

        fieldMapImageView = layout.findViewById(R.id.field_map)
        if (position?.startsWith("RED") == true) {
            fieldMapImageView.setImageResource(R.drawable.field_orientation_2)
            fieldOrientation = 1
        }
        fieldMapImageView.setOnTouchListener(::mapTouchAction)

        return layout
    }

    private fun setButtonActions(layout: View) {
        layout.findViewById<Button>(R.id.submit_shot_button).setOnClickListener { submitButtonAction() }
        layout.findViewById<Button>(R.id.cancel_button).setOnClickListener { cancelButtonAction() }
    }

    private fun submitButtonAction() {
        shotDataHandler.addShot(currentShotX, currentShotY, scored)
        if (scored)
            teleopDataHandler.incrementSpeakerNoteScored()
        else
            teleopDataHandler.incrementSpeakerNoteFailed()

        Toast.makeText(context, (if (scored) "Scored" else "Missed") + " Shot Recorded", Toast.LENGTH_SHORT).show()
        loadCallingFragment()
    }

    private fun cancelButtonAction() {
        loadCallingFragment()
    }

    private  fun loadCallingFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, callingFragment)
        transaction.commit()
    }

    private fun initFieldBitmap() {
        fieldMapBitmap = fieldMapImageView
            .drawable
            .toBitmap(fieldMapImageView.width, fieldMapImageView.height, Bitmap.Config.ARGB_8888)
        isFieldBitmapInitialized = true
    }

    private fun mapTouchAction(view: View, event: MotionEvent?): Boolean {
        if (event != null) {

            if (!isFieldBitmapInitialized) {
                initFieldBitmap()
                fieldMapWidth = fieldMapImageView.width
                fieldMapHeight = fieldMapImageView.height
            }

            val x = event.x
            val y = event.y

            // If touch inside bounds of imageview
            if (!(x > fieldMapWidth || y > fieldMapHeight || x < 0 || y < 0)) {
                drawTouchPoint(x, y)

                if (fieldOrientation == 1) {
                    currentShotX = 1.0f - (x / fieldMapWidth)
                } else {
                    currentShotX = x / fieldMapWidth
                }

                currentShotY = 1.0f - (y / fieldMapHeight)
                Log.i("SpeakerShotMapFragment", String.format("Shot at (%.2f, %.2f)", currentShotX, currentShotY))

                return true
            }
        }
        return false
    }

    private fun drawTouchPoint(x: Float, y: Float) {
        val mutableBitmap = fieldMapBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        canvas.drawCircle(x, y, 5F, paint)
        fieldMapImageView.setImageBitmap(mutableBitmap)
    }
}