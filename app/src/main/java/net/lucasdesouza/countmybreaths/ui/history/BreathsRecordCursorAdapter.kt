package net.lucasdesouza.countmybreaths.ui.history

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import net.lucasdesouza.countmybreaths.R


class BreathsRecordCursorAdapter(context: Context?, cursor: Cursor?) :
    CursorAdapter(context, cursor, 0) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.breath_record_list_item, parent, false)
    }

    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val timestampTextView = view.findViewById(R.id.timestampTextView) as TextView
        val breathsTextView = view.findViewById(R.id.breathsTextView) as TextView
        val breathBarView = view.findViewById(R.id.breathBarView) as ProgressBar
        val timestamp: String = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
        val breaths: Int = cursor.getInt(cursor.getColumnIndexOrThrow("breaths"))
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd hh:mm")
        val date = java.util.Date(timestamp.toLong() * 1000)
        sdf.format(date)
        timestampTextView.text = date.toString()
        breathsTextView.text = "$breaths Breaths per minute"
        breathBarView.progress = breaths
    }
}