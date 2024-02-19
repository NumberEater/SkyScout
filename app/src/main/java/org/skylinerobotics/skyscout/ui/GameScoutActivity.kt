package org.skylinerobotics.skyscout.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.skylinerobotics.skyscout.R

class GameScoutActivity : AppCompatActivity() {

    private lateinit var backPressedCallback: OnBackPressedCallback

    private lateinit var autonFragment: AutonScoutFragment
    private lateinit var teleopFragment: TeleopScoutFragment
    private lateinit var infoFragment: InfoScoutFragment

    private var teamNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_scout)

        initBackPressedCallback()
        initBottomNav()

        promptTeamNumber()
    }

    private fun initBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showLeaveWarningDialog()
            }
        }
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private fun promptTeamNumber() {
        val dialog = TeamNumberDialog(this, ::teamNumberFinishCallback)
        dialog.show()
    }

    private fun teamNumberFinishCallback(number: Int) {
        teamNumber = number
        initScoutFragments()
        loadFragment(autonFragment)
    }

    private fun initScoutFragments() {
        autonFragment = AutonScoutFragment(teamNumber)
        teleopFragment = TeleopScoutFragment(teamNumber)
        infoFragment = InfoScoutFragment(teamNumber)
    }

    private fun initBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_auton -> {
                    loadFragment(autonFragment)
                    true
                }
                R.id.navigation_teleop -> {
                    loadFragment(teleopFragment)
                    true
                }
                R.id.navigation_info -> {
                    loadFragment(infoFragment)
                    true
                }
                else -> true
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }

    private fun showLeaveWarningDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to quit?")
        builder.setNegativeButton("No") { _, _ -> }
        builder.setPositiveButton("Yes") { _, _ ->
            finish()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}