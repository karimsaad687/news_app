package com.app.newsapp.onboarding.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.newsapp.R
import java.util.LinkedList

class CountryAdapter(
    private val list: LinkedList<CountryModel>,
    private val countryFragment: CountryFragment
) :
    Adapter<CountryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_country_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position], position, countryFragment)
    }

    class Holder(itemView: View) : ViewHolder(itemView) {

        private val countryNameTv = itemView.findViewById<TextView>(R.id.country_tv)

        fun onBind(countryModel: CountryModel, position: Int, countryFragment: CountryFragment) {
            countryNameTv.text = countryModel.name
            itemView.setBackgroundResource(
                if (countryModel.selected) R.drawable.country_item_border else R.drawable.country_item
            )
            itemView.setOnClickListener {
                countryModel.selected = !countryModel.selected
                countryFragment.onCountrySelected(countryModel, position)
            }
        }
    }
}