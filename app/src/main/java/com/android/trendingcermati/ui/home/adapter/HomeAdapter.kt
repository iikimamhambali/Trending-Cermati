package com.android.trendingcermati.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.trendingcermati.R
import com.android.trendingcermati.entity.User
import com.android.trendingcermati.helper.StatusState

class HomeAdapter(diffCallback: DiffUtil.ItemCallback<User> = MainItemCallback) :
    PagedListAdapter<User, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        const val ITEM_CONTENT = 1
        const val ITEM_PROGRESS = 2
    }

    lateinit var status: StatusState

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_CONTENT -> HomeViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_item_user,
                        parent,
                        false
                    )
                )

            ITEM_PROGRESS -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_item_loading,
                    parent,
                    false
                )
            )
            else -> throw RuntimeException("Error view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> getItem(holder.adapterPosition)?.let { holder.bind(it) }
            is LoadingViewHolder -> holder.bind(status)
        }
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        itemCount.minus(1) -> ITEM_PROGRESS
        else -> ITEM_CONTENT
    }

    object MainItemCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

}