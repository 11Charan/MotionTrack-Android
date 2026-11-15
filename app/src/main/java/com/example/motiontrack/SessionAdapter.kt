package com.example.motiontrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionAdapter(
    private val sessions: List<Session>
) :
    RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val scoreText: TextView = itemView.findViewById(R.id.itemScoreText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionViewHolder {

        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_session,
                    parent,
                    false
                )

        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SessionViewHolder,
        position: Int
    ) {
        val session = sessions[position]
        holder.nameText.text = session.name
        holder.scoreText.text = "Movement score: ${session.score}"
        val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        holder.dateText.text = formatter.format(Date(session.date))
    }

    override fun getItemCount(): Int {
        return sessions.size
    }
}