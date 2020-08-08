package net.lucasdesouza.countmybreaths.ui.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_counter.*
import net.lucasdesouza.countmybreaths.MainActivity
import net.lucasdesouza.countmybreaths.R
import net.lucasdesouza.countmybreaths.data.BreathRecordContract
import java.util.*
import kotlin.concurrent.schedule


class CounterFragment : Fragment(R.layout.fragment_counter) {

    private var count = 0
    private var timer = 0
    private var isCounting = false
    private lateinit var dbHelper: BreathRecordContract.BreathRecordDbHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = BreathRecordContract.getHelper(this.requireContext())
        updateCountText()
        isCounting = false
        startBreathButtonListener()
        startResetButtonListener()
    }

    private fun startBreathButtonListener() {
        breathButton.setOnClickListener {
            val activity: MainActivity? = activity as MainActivity?
            activity?.vibrateShort()
            if (!isCounting) {
                result_text.text = ""
                result_text.visibility = View.GONE
                resetButton.visibility = View.VISIBLE
                isCounting = true
                startTimer()
            }
            count += 1
            updateCountText()
        }
    }

    private fun startResetButtonListener() {
        resetButton.setOnClickListener {
            val activity: MainActivity? = activity as MainActivity?
            activity?.vibrateShort()
            isCounting = false
            count = 0
            timer = 0
            updateCountText()
            updateTimerText()
            result_text.text = ""
            result_text.visibility = View.GONE
            resetButton.visibility = View.GONE
        }
    }
    private fun updateCountText () {
        val countText = "$count Breaths"
        current_breaths_count.text = countText
    }

    private fun updateTimerText () {
        timeRemainingProgressBar.progress = timer
    }

    private fun startTimer() {
        Timer("CountDown", false).schedule(1000) {
            if (isCounting) {
                timer += 1
                updateTimerText()
                if (timer < 30) {
                    startTimer()
                } else {
                    val bpm = count * 2
                    if (bpm >= 30) {
                        val activity: MainActivity? = activity as MainActivity?
                        activity?.vibrateLong()
                        activity?.toastLong("$bpm Breaths per minute is HIGH. Please contact your veterinarian")
                    } else {
                        val activity: MainActivity? = activity as MainActivity?
                        activity?.vibrateMed()
                        activity?.toastShort("$bpm Breaths per minute")
                    }
                    val result = "$bpm Breaths per minute"
                    BreathRecordContract.addRecord(dbHelper, System.currentTimeMillis() / 1000, bpm)
                    result_text.text = result
                    result_text.visibility = View.VISIBLE
                    resetButton.visibility = View.GONE
                    isCounting = false
                    count = 0
                    timer = 0
                    updateCountText()
                    updateTimerText()
                    startBreathButtonListener()
                    startResetButtonListener()
                }
            }
        }
    }

    override fun onDestroy() {
        BreathRecordContract.helperClose(dbHelper)
        super.onDestroy()
    }
}