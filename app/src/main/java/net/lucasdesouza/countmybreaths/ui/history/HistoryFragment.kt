package net.lucasdesouza.countmybreaths.ui.history

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_history.*
import net.lucasdesouza.countmybreaths.R
import net.lucasdesouza.countmybreaths.data.BreathRecordContract

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var dbHelper: BreathRecordContract.BreathRecordDbHelper
    private var cursor: Cursor? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = BreathRecordContract.getHelper(this.requireContext())
        cursor = BreathRecordContract.fetchAll(dbHelper)
        var breathsRecordAdapter = BreathsRecordCursorAdapter(this.requireContext(), cursor)
        breathRecordListView.adapter = breathsRecordAdapter
    }

    override fun onDestroy() {
        BreathRecordContract.helperClose(dbHelper)
        super.onDestroy()
    }
}