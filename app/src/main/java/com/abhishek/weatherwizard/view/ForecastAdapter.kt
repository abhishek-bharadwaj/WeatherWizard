package com.abhishek.weatherwizard.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhishek.weatherwizard.R
import kotlinx.android.synthetic.main.item_forecast_data.view.*

class ForecastAdapter(val context: Context) : RecyclerView.Adapter<ForecastAdapter.ForecastVH>() {

    private val inflater = LayoutInflater.from(context)

    private var forecastData: List<Pair<String, Int>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ForecastVH(inflater.inflate(R.layout.item_forecast_data, parent, false))

    override fun getItemCount() = forecastData.size

    override fun onBindViewHolder(holder: ForecastVH, position: Int) {
        val data = forecastData[position]
        val itemView = holder.itemView

        itemView.tv_name.text = data.first
        itemView.tv_temp.text = context.getString(R.string.temp_str, data.second)
    }

    fun updateData(forecastData: List<Pair<String, Int>>) {
        this.forecastData = forecastData
        notifyDataSetChanged()
    }

    inner class ForecastVH(val view: View) : RecyclerView.ViewHolder(view)
}