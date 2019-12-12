package com.benrostudios.weggi

import android.net.wifi.hotspot2.pps.HomeSp
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class rec_adapter(private val list: List<home_frag.History>,val listener: (Int) -> Unit)
    : RecyclerView.Adapter<LocViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LocViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: LocViewHolder, position: Int) {
        val transaction: home_frag.History = list[position]
        holder.bind(transaction,position,listener)
    }

    override fun getItemCount(): Int = list.size

}

class LocViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.sample, parent, false)) {

    private var mLocView: TextView? = null
    private var mDateView: TextView? = null



    init {
        mLocView = itemView.findViewById(R.id.sloaction)
        mDateView = itemView.findViewById(R.id.sdate)

    }

    fun bind(historys: home_frag.History,pos: Int, listener: (Int) -> Unit) = with(itemView) {
        mLocView?.text = historys.location
        mDateView?.text = historys.mDate
        val cvItem = findViewById(R.id.cvItem) as CardView
        cvItem.setOnClickListener {
            listener(pos)
        }

    }


}