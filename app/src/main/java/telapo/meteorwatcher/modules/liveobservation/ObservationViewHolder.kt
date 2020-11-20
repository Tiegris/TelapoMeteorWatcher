package telapo.meteorwatcher.modules.liveobservation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R

class ObservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvSwarmName: TextView


    init {
        tvSwarmName = itemView.findViewById(R.id.tvObservationSwarmName)
        //tvVersion = itemView.findViewById(  R.id.tvSchemeVer)
    }
}