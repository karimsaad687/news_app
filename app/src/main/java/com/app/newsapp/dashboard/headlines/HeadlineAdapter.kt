package com.app.newsapp.dashboard.headlines

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.app.newsapp.utils.DateUtils
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
        holder.onBind(list[position], baseFragment)
    }

    class Holder(itemView: View) : ViewHolder(itemView) {

        private val articleTitleTv = itemView.findViewById<TextView>(R.id.article_title_tv)
        private val articleIm = itemView.findViewById<RoundedImageView>(R.id.article_im)
        private val articleDescTv = itemView.findViewById<TextView>(R.id.article_desc_tv)
        private val articleDateTv = itemView.findViewById<TextView>(R.id.article_date_tv)
        private val articleSourceTv = itemView.findViewById<TextView>(R.id.article_source_tv)

        fun onBind(
            headlineModel: HeadlineModel,
            baseFragment: BaseFragment
        ) {
            articleTitleTv.text = headlineModel.title
            articleDescTv.text = headlineModel.description
            articleDateTv.text = DateUtils.convertIsoFormatToLocalTime(headlineModel.publishedAt)
            articleSourceTv.text = headlineModel.sourceName

            if (headlineModel.sourceName.length < 5) {
                articleSourceTv.visibility = GONE
            }

            if (headlineModel.description.length < 5) {
                articleDescTv.visibility = GONE
            }

            if (headlineModel.urlToImage.length < 5) {
                articleIm.visibility = GONE
            } else {
                Picasso.get().load(headlineModel.urlToImage).into(articleIm)
            }
            if (URLUtil.isValidUrl(headlineModel.url)) {
                itemView.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(headlineModel.url))
                    startActivity(baseFragment.requireContext(), browserIntent, null)
                }
            }
        }
    }
}