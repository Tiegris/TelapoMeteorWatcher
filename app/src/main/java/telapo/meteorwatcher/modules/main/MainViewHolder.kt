package telapo.meteorwatcher.modules.main

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.observation.Observation

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvObservationTitle: TextView
    val tvObservationDate: TextView
    val ibDelete: ImageButton
    val ibUpload: ImageButton
    val ibStat: ImageButton

    var item: Observation? = null
    var listener: IMainListener? = null

    init {
        tvObservationTitle = itemView.findViewById(R.id.tvObservationTitle)
        tvObservationDate = itemView.findViewById(  R.id.tvObservationDate)
        ibDelete = itemView.findViewById(R.id.ibDelete)
        ibDelete.setOnClickListener {
            item?.let { it1 -> listener?.Delete(it1) }
        }
        ibUpload = itemView.findViewById(R.id.ibUpload)
        ibUpload.setOnClickListener {
            item?.let { it2 -> listener?.Upload(it2) }
        }
        ibStat = itemView.findViewById(R.id.ibStat)
        ibStat.setOnClickListener {
            item?. let {it3 -> listener?.ShowStat(it3) }
        }
    }
}