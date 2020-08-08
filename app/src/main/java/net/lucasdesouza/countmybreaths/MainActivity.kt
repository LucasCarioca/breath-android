package net.lucasdesouza.countmybreaths

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vibrator  = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_counter, R.id.navigation_history, R.id.navigation_info))
        navView.setupWithNavController(navController)
    }

    fun vibrateShort() {
        vibrator.vibrate(50)
    }

    fun vibrateMed() {
        vibrator.vibrate(200)
    }

    fun vibrateLong() {
        vibrator.vibrate(500)
    }

    fun toastShort(content: String) {
        runOnUiThread {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        }
    }
    fun toastLong(content: String) {
        runOnUiThread {
            Toast.makeText(this, content, Toast.LENGTH_LONG).show()
        }
    }
}