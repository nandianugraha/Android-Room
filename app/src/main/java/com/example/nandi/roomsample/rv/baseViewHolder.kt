package com.example.nandi.roomsample.rv

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<D>(view: View): RecyclerView.ViewHolder(view){
    abstract fun onBind(data: D)
}