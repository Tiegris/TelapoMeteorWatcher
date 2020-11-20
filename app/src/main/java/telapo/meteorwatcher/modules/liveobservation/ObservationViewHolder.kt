package telapo.meteorwatcher.modules.liveobservation

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R

class ObservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvSwarmName: TextView = itemView.findViewById(R.id.tvObservationSwarmName)
    val tvCount: TextView = itemView.findViewById(R.id.tvObservationCounter)

    val btn_0: Button = itemView.findViewById(R.id.btnSmall_0)

    val btn_m1: Button = itemView.findViewById(R.id.btnSmall_m1)
    val btn_m2: Button = itemView.findViewById(R.id.btnSmall_m2)
    val btn_m3: Button = itemView.findViewById(R.id.btnSmall_m3)
    val btn_m4: Button = itemView.findViewById(R.id.btnSmall_m4)
    val btn_m5: Button = itemView.findViewById(R.id.btnSmall_m5)

    val btn_1: Button = itemView.findViewById(R.id.btnSmall_1)
    val btn_2: Button = itemView.findViewById(R.id.btnSmall_2)
    val btn_3: Button = itemView.findViewById(R.id.btnSmall_3)
    val btn_4: Button = itemView.findViewById(R.id.btnSmall_4)
    val btn_5: Button = itemView.findViewById(R.id.btnSmall_5)
}