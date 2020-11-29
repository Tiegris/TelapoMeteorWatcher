package telapo.meteorwatcher.modules.schemes

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.scheme.Scheme

class SchemesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: TextView
    val tvVersion: TextView
    val bDelete: ImageButton
    val bDetails: ImageButton

    var item: Scheme? = null
    var listener: IListener? = null

    init {
        tvName = itemView.findViewById(R.id.tvSchemeName)
        tvVersion = itemView.findViewById(  R.id.tvSchemeVer)
        bDelete = itemView.findViewById(R.id.bDelete)
        bDelete.setOnClickListener {
            item?.let { it1 -> listener?.Delete(it1) }
        }
        bDetails = itemView.findViewById(R.id.bDetails)
        bDetails.setOnClickListener {
            item?.let { it2 -> listener?.Details(it2) }
        }
    }
}