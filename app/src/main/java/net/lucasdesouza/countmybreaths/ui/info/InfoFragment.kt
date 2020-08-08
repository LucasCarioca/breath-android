package net.lucasdesouza.countmybreaths.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_info.*
import net.lucasdesouza.countmybreaths.R
import net.lucasdesouza.countmybreaths.data.BreathRecordContract

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var dbHelper: BreathRecordContract.BreathRecordDbHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = BreathRecordContract.getHelper(this.requireContext())
        deleteAllDataButton.setOnClickListener {
            BreathRecordContract.deleteAll(dbHelper)
            BreathRecordContract.fetchAll(dbHelper)
        }
    }

    override fun onDestroy() {
        BreathRecordContract.helperClose(dbHelper)
        super.onDestroy()
    }
}