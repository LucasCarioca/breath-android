package net.lucasdesouza.countmybreaths.ui.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.lucasdesouza.countmybreaths.R
import java.util.*
import kotlin.concurrent.schedule

class CounterFragment : Fragment() {

    private lateinit var counterViewModel: CounterViewModel

    private var count = 0
    private var timer = 30
    private var isCounting = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        counterViewModel =
                ViewModelProviders.of(this).get(CounterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_counter, container, false)
        initCounter(root)
        return root
    }

    private fun initCounter(root: View?) {
        var breathButton: AppCompatImageButton = root!!.findViewById(R.id.breathButton)
        setCountLabel(root, count)
        isCounting = false
        setBreathButtonListener(root, breathButton)
    }

    private fun setBreathButtonListener(root: View?, button: AppCompatImageButton) {
        button.setOnClickListener {
            if (!isCounting) {
                isCounting = true
                setTimer(root)
            }
            count += 1
            setCountLabel(root, count)
        }
    }

    private fun setCountLabel(root: View?, count: Int) {
        var textView: TextView = root!!.findViewById(R.id.current_breaths_count)
        textView.text = "${count} Breaths"
    }

    private fun setTimerLabel(root: View?, timer: Int) {
        var textView: TextView = root!!.findViewById(R.id.current_time_remaining)
        textView.text = "${timer} Seconds remaining"
    }

    private fun setTimer(root: View?) {
        Timer("CountDown", false).schedule(1000) {
            timer -= 1
            setTimerLabel(root, timer)
            if (!(timer <= 0)) {
                setTimer(root)
            } else {
                var textView: TextView = root!!.findViewById(R.id.current_time_remaining)
                var breathButton: AppCompatImageButton = root!!.findViewById(R.id.breathButton)
                val bpm = count * 2
                textView.text = "$bpm Breaths per minute"
                isCounting = false
                count = 0
                timer = 30
                setBreathButtonListener(root, breathButton)
            }
        }
    }
}