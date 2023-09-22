package com.app.newsapp.dashboard.headlines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import java.util.LinkedList

class HeadlineAdapter(
    private val list: LinkedList<HeadlineModel>,
    private val baseFragment: BaseFragment
) :
    Adapter<HeadlineAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_headline_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position], position, baseFragment)
    }

    class Holder(itemView: View) : ViewHolder(itemView) {

        private val articleTitleTv = itemView.findViewById<TextView>(R.id.article_title_tv)
        private val articleIm = itemView.findViewById<RoundedImageView>(R.id.article_im)
        private val articleDescTv = itemView.findViewById<TextView>(R.id.article_desc_tv)
        private val articleDateTv = itemView.findViewById<TextView>(R.id.article_date_tv)
        private val articleSourceTv = itemView.findViewById<TextView>(R.id.article_source_tv)

        fun onBind(
            headlineModel: HeadlineModel,
            position: Int,
            baseFragment: BaseFragment
        ) {
            articleTitleTv.text = headlineModel.title
            articleDescTv.text = headlineModel.description
            articleDateTv.text = headlineModel.publishedAt
            articleSourceTv.text = headlineModel.sourceName

            Picasso.get().load(headlineModel.urlToImage).into(articleIm)
            itemView.setOnClickListener {

            }
        }
    }
}