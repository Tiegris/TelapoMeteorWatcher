package telapo.meteorwatcher.modules.schemes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.local.AppDatabase
import telapo.meteorwatcher.dal.model.scheme.Scheme
import kotlin.concurrent.thread

interface IListener {
    fun Delete(scheme: Scheme)
    fun Details(scheme: Scheme)
}

class SchemesAdapter(private val context: Context) :
    RecyclerView.Adapter<SchemesViewHolder>() , IListener{

    val Items = mutableListOf<Scheme>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.schemes_row_item, parent, false
        )
        return SchemesViewHolder(view)
    }

    fun Update(items: List<Scheme>) {
        this.Items.clear()
        this.Items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SchemesViewHolder, position: Int) {
        val item = Items[position]
        holder.tvName.text = item.name
        holder.tvVersion.text = item.version.toString()
        holder.listener = this
        holder.item = item
    }

    override fun getItemCount(): Int = Items.size

    override fun Delete(scheme: Scheme) {
        thread {
            AppDatabase.getInstance(context).schemeDao().DeleteItem(scheme)
        }
        Items.remove(scheme)
        notifyDataSetChanged()
    }

    override fun Details(scheme: Scheme) {

    }
}