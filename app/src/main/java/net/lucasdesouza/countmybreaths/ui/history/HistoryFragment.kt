package net.lucasdesouza.countmybreaths.ui.history

import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import androidx.appcompat.app.AlertDialog
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
        breathRecordListView.onItemLongClickListener = android.widget.AdapterView.OnItemLongClickListener {
            _, _, _, index ->
            val dialogBuilder = AlertDialog.Builder(this.requireContext())
            dialogBuilder.setMessage("Do you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Delete") { _, _ ->
                    BreathRecordContract.deleteById(dbHelper, index)
                    breathsRecordAdapter.swapCursor(BreathRecordContract.fetchAll(dbHelper))
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete item")
            alert.show()
            false
        }
    }

    override fun onDestroy() {
        BreathRecordContract.helperClose(dbHelper)
        super.onDestroy()
    }
}