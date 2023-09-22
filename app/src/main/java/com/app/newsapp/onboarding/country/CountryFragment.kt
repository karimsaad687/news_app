package com.app.newsapp.onboarding.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.app.newsapp.Onboarding
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.app.newsapp.utils.CountryUtils
import com.app.newsapp.utils.SharedPreferencesUtils
import java.util.LinkedList

class CountryFragment : BaseFragment() {

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var root: View
    private lateinit var confirmBtn: Button
    private var countrySelected = false
    private var oldSelectedIndex = -1
    private lateinit var countryModels: LinkedList<CountryModel>
    private var uiInitialized=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!uiInitialized) {
            uiInitialized=true
            root = inflater.inflate(R.layout.fragment_countries, container, false)

            confirmBtn = root.findViewById(R.id.confirm_btn)
            countryModels = CountryUtils.getAllCountries()
            val recycler = root.findViewById<RecyclerView>(R.id.recycler)
            countryAdapter = CountryAdapter(countryModels, this)
            recycler.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            recycler.adapter = countryAdapter

            confirmBtn.setOnClickListener {
                if (countrySelected) {
                    navigateTo(R.id.categoryFragment)
                }
            }
        }
        return root
    }

    fun onCountrySelected(model: CountryModel, position: Int) {
        SharedPreferencesUtils.setCountry(requireContext(), model.iso)
        countrySelected = model.selected
        confirmBtn.alpha = if (model.selected) 1.0f else 0.3f
        if (oldSelectedIndex > -1 && oldSelectedIndex!=position && countryModels[oldSelectedIndex].selected) {
            countryModels[oldSelectedIndex].selected=false
            countryAdapter.notifyItemChanged(oldSelectedIndex)
        }
        countryAdapter.notifyItemChanged(position)
        oldSelectedIndex=position
    }

    override fun onResume() {
        super.onResume()
        (activity as Onboarding).setTitle(activity.getString(R.string.choose_country))
    }
}