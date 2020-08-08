package net.lucasdesouza.countmybreaths.ui.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_counter.*
import net.lucasdesouza.countmybreaths.MainActivity
import net.lucasdesouza.countmybreaths.R
import java.util.*
import kotlin.concurrent.schedule


class CounterFragment : Fragment(R.layout.fragment_counter) {

    private var count = 0
    private var timer = 30
    private var isCounting = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateCountText()
        isCounting = false
        startBreathButtonListener()
    }

    private fun startBreathButtonListener() {
        breathButton.setOnClickListener {
            val activity: MainActivity? = activity as MainActivity?
            activity?.vibrateShort()
            if (!isCounting) {
                result_text.text = ""
                isCounting = true
                startTimer()
            }
            count += 1
            updateCountText()
        }
    }
    private fun updateCountText () {
        if (count == 0) {
            current_time_remaining.text = ""
        } else {
            val countText = "$count Breaths"
            current_breaths_count.text = countText
        }
    }

    private fun updateTimerText () {
        if (timer == 30) {
            current_time_remaining.text = ""
        } else {
            val timerText = "$timer Seconds left"
            current_time_remaining.text = timerText
        }
    }

    private fun startTimer() {
        Timer("CountDown", false).schedule(1000) {
            if (current_time_remaining != null) {
                timer -= 1
                updateTimerText()
                if (!(timer <= 0)) {
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
                    result_text.text = result
                    isCounting = false
                    count = 0
                    timer = 30
                    updateCountText()
                    updateTimerText()
                    startBreathButtonListener()
                }
            }
        }
    }
}