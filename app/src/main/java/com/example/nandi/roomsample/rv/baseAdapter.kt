package com.example.nandi.roomsample.rv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseAdapter<D, VH: BaseViewHolder<D>>: RecyclerView.Adapter<VH>(){
    var datas: MutableList<D> = ArrayList()

    override fun getItemCount() = datas.size

    protected fun getView(parent: ViewGroup?, viewType: Int) =
            LayoutInflater.from(parent?.context).inflate(getItemResourceLayout(viewType), parent, false)

    protected abstract fun getItemResourceLayout(viewType: Int): Int

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(datas[position])
    }

    open fun setData(data: List<D>?){
        datas = data!!.toMutableList()
        notifyDataSetChanged()
    }
}