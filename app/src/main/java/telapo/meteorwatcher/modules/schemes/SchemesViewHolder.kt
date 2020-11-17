package telapo.meteorwatcher.modules.schemes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R

class SchemesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: TextView
    val tvVersion: TextView

    init {
        tvName = itemView.findViewById(R.id.tvSchemeName)
        tvVersion = itemView.findViewById(  R.id.tvObservationCounter)
    }
}