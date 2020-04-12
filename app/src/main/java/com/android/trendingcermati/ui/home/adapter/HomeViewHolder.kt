package com.android.trendingcermati.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.trendingcermati.R
import com.android.trendingcermati.entity.User
import com.android.trendingcermati.extention.loadFromUrlWithPlaceholder
import kotlinx.android.synthetic.main.layout_item_user.view.*

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(items: User) {
        with(itemView) {
            ivAvatar.loadFromUrlWithPlaceholder(
                items.avatarUrl,
                R.drawable.ic_logo_user,
                R.drawable.ic_logo_user
            )
            tvName.text = items.login
            tvRole.text = items.type
        }
    }
}