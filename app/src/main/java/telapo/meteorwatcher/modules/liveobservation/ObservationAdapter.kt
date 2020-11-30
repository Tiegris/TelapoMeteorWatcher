package telapo.meteorwatcher.modules.liveobservation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.observation.Observation
import telapo.meteorwatcher.dal.model.scheme.Scheme

class ObservationAdapter(private val context: Context) :
    RecyclerView.Adapter<ObservationViewHolder>() {

    private var items : MutableList<String?> = mutableListOf<String?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.observation_row_item, parent, false
        )
        return ObservationViewHolder(view)

    }

    fun Set(scheme: Scheme) {
        this.items = mutableListOf<String?>(
            scheme.swarm1,
            scheme.swarm2,
            scheme.swarm3,
            scheme.swarm4,
            scheme.swarm5,
            scheme.swarm6,
            "Spo"
        )
        while (items.remove(""));
        while (items.remove(null));

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ObservationViewHolder, position: Int) {
        val item = items[position]
        holder.tvSwarmName.text = item

        holder.tvCount.text .let {
            Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()
        }
        holder.btn_0.setOnClickListener { Observation.ActiveObservation?.AddMeteor(0, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString() }

        holder.btn_1.setOnClickListener { Observation.ActiveObservation?.AddMeteor(1, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_2.setOnClickListener { Observation.ActiveObservation?.AddMeteor(2, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_3.setOnClickListener { Observation.ActiveObservation?.AddMeteor(3, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_4.setOnClickListener { Observation.ActiveObservation?.AddMeteor(4, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_5.setOnClickListener { Observation.ActiveObservation?.AddMeteor(5, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }

        holder.btn_m1.setOnClickListener { Observation.ActiveObservation?.AddMeteor(-1, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_m2.setOnClickListener { Observation.ActiveObservation?.AddMeteor(-2, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_m3.setOnClickListener { Observation.ActiveObservation?.AddMeteor(-3, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_m4.setOnClickListener { Observation.ActiveObservation?.AddMeteor(-4, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
        holder.btn_m5.setOnClickListener { Observation.ActiveObservation?.AddMeteor(-5, item!!); holder.tvCount.text = Observation.ActiveObservation?.LatestCycle?.Swarms?.get(position)?.Count.toString()  }
    }

    override fun getItemCount(): Int = items.size
}