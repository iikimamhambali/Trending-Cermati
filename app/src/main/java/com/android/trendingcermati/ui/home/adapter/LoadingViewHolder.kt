package com.android.trendingcermati.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.trendingcermati.helper.StatusState
import kotlinx.android.synthetic.main.layout_item_loading.view.*

class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: StatusState) {
        with(itemView) {
            progressItem.visibility = when (status) {
                is StatusState.LoadingPage -> View.VISIBLE
                else -> View.GONE
            }
        }
    }
}