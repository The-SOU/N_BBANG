package com.theone.n_bbang.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.theone.n_bbang.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SearchAddressAdapter() : RecyclerView.Adapter<SearchAddressAdapter.ViewHolder>() {
    var dataList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_address, parent, false))
    }

    class ViewHolder(var getView: View): RecyclerView.ViewHolder(getView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.getView
        val context = view.context
        val value = dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

}
