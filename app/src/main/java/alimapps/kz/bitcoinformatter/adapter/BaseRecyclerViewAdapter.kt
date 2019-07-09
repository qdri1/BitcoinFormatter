package alimapps.kz.bitcoinformatter.adapter

import alimapps.kz.bitcoinformatter.BR
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewAdapter<T>(var items: List<T>, val itemlayoutId: Int) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.RecyclerItemViewHolder<T>>() {

    var adapterClickListener: AdapterClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return RecyclerItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)

    override fun onBindViewHolder(holder: RecyclerItemViewHolder<T>, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnLongClickListener {
            adapterClickListener?.onAdapterItemLongClicked(position, item)
            true
        }
        holder.itemView.setOnClickListener {
            adapterClickListener?.onAdapterItemClicked(position, item)
        }
    }

    override fun getItemCount(): Int = items.size

    protected open fun getLayoutIdForPosition(position: Int): Int = itemlayoutId

    class RecyclerItemViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }

    }

    interface AdapterClickListener<T> {
        fun onAdapterItemClicked(position: Int, item: T) {}
        fun onAdapterItemLongClicked(position: Int, item: T) {}
    }
}