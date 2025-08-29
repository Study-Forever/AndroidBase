package com.base.library.components

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRVAdapter<T, VB : ViewBinding>(
    private val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<BaseRVAdapter<T, VB>.BaseViewHolder>() {

    lateinit var context: Context

    abstract fun bind(binding: VB, item: T, position: Int)

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = createBinding(inflater, parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder.binding, items[position], position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newItems: List<T>, clearAndAdd: Boolean = false) {
        if (clearAndAdd) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            addItems(newItems)
        }
    }

    fun setItem(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun updateItem(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    private fun addItems(newItems: List<T>) {
        val startIndex = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startIndex, newItems.size)
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): T? = items.getOrNull(position)

    inner class BaseViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}
