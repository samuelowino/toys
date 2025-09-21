package com.owino.whackamole.view
import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.owino.whackamole.R
import com.owino.whackamole.model.Score
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class ScoreHistoryAdapter(val context: Context,val scores: List<Score>): RecyclerView.Adapter<ScoreHistoryAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_score_layout,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val score = scores[position]
        val timestamp = convertDateTimeToString(score.timestamp)
        holder.timestampView.text = timestamp
        if (score.score > 0)
            holder.scoreView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.whacked_mole))
        else
            holder.scoreView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.adverse))
    }
    override fun getItemCount(): Int = scores.size
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val timestampView: TextView = itemView.findViewById(R.id.item_score_timestamp_entry_view)
        val scoreView: ImageView = itemView.findViewById(R.id.item_score_value_entry_view)
    }
    fun convertDateTimeToString(date: Date): String {
        val format = if (date.isToday()) "HH:mm:ss"
        else if (date.isThisMonth()) "dd HH:mm:ss"
        else if (date.isSameyear()) "MMM dd HH:mm"
        else "YYYY MMMM dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.format(date)
    }
    fun Date.isToday(): Boolean {
        val thisCalendar = Calendar.getInstance()
        thisCalendar.time = this
        val today = Calendar.getInstance()
        return thisCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                thisCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                thisCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
    }
    fun Date.isThisMonth(): Boolean {
        val thisCalendar = Calendar.getInstance()
        thisCalendar.time = this
        val today = Calendar.getInstance()
        return thisCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                thisCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
    }
    fun Date.isSameyear(): Boolean {
        val thisCalendar = Calendar.getInstance()
        thisCalendar.time = this
        val today = Calendar.getInstance()
        return thisCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
    }
}