package org.skylinerobotics.skyscout.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datahandler.SpeakerShotDataHandler
import org.skylinerobotics.skyscout.data.datahandler.TeleopDataHandler

class SpeakerShotMapFragment(
    private val callingFragment: GameScoutFragment,
    private val shotDataHandler: SpeakerShotDataHandler,
    private val teleopDataHandler: TeleopDataHandler,
    private val scored: Boolean,
    private val scoutPosition: String?) : Fragment() {

    private lateinit var layout: View

    private lateinit var fieldMapImageView: ImageView
    private var fieldOrientationResource = 0

    private var currentShotX = 0f
    private var currentShotY = 0f

    private var fieldMapDrawable: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_speaker_shot_map, container, false)

        fieldMapImageView = layout.findViewById(R.id.field_map)
        fieldMapImageView.setOnTouchListener(::mapTouchAction)
        setButtonActions()

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (scoutPosition?.startsWith("BLUE") == true) {
            fieldOrientationResource = R.drawable.field_orientation_1
        } else {
            fieldOrientationResource = R.drawable.field_orientation_2
        }

        Glide.with(this)
            .load(fieldOrientationResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    fieldMapDrawable = resource
                    return false
                }
            })
            .into(fieldMapImageView)
    }

    private fun setButtonActions() {
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

    private fun mapTouchAction(view: View, event: MotionEvent?): Boolean {
        if (event != null) {
            val x = event.x
            val y = event.y

            // If touch inside bounds of imageview
            if (!(x > fieldMapImageView.width || y > fieldMapImageView.height || x < 0 || y < 0)) {
                drawTouchPoint(x, y)

                if (fieldOrientationResource == R.drawable.field_orientation_1) {
                    currentShotX = x / fieldMapImageView.width
                } else {
                    currentShotX = 1.0f - (x / fieldMapImageView.width)
                }
                currentShotY = 1.0f - (y / fieldMapImageView.height)
                Log.i("SpeakerShotMapFragment", String.format("(%.2f, %.2f)", currentShotX, currentShotY))

                return true
            }
        }
        return false
    }

    private fun drawTouchPoint(x: Float, y: Float) {
        if (fieldMapDrawable != null) {
            val mutableBitmap = fieldMapDrawable!!.toBitmap().copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(mutableBitmap)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = (
                    if (fieldOrientationResource == R.drawable.field_orientation_2)
                        Color.RED
                    else
                        Color.rgb(48, 110, 255)
            )
            canvas.drawCircle(x, y, 10F, paint)
            fieldMapImageView.setImageBitmap(mutableBitmap)
        }
    }
}