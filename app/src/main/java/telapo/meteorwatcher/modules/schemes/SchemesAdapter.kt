package telapo.meteorwatcher.modules.schemes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.scheme.Scheme

class SchemesAdapter(private val context: Context) :
    RecyclerView.Adapter<SchemesViewHolder>() {

    private val items = mutableListOf<Scheme>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.schemes_row_item, parent, false
        )
        return SchemesViewHolder(view)

    }

    fun Update(items: List<Scheme>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SchemesViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvVersion.text = item.version.toString()
    }

    override fun getItemCount(): Int = items.size
}