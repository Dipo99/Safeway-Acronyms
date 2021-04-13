package com.acronyms.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acronyms.R
import com.acronyms.model.AcronymResponse
import com.acronyms.utils.inflate
import kotlinx.android.synthetic.main.item_search.view.*

class AcronymAdapter(
    private val context: Context,
    private val lfList: List<AcronymResponse.Lf>
) : RecyclerView.Adapter<AcronymAdapter.ViewHolder>() {

    override fun onBindViewHolder(foViewHolder: ViewHolder, fiPosition: Int) {
        val info = lfList[fiPosition]

        foViewHolder.tvTitle.text = info.lf
        foViewHolder.tvDescription.text =
            context.getString(R.string.tv_description, info.since, info.freq)
    }

    override fun onCreateViewHolder(foViewGroup: ViewGroup, fiPosition: Int): ViewHolder {
        return ViewHolder(foViewGroup.inflate(R.layout.item_search))
    }

    override fun getItemCount(): Int {
        return lfList.size
    }

    class ViewHolder(foView: View) : RecyclerView.ViewHolder(foView) {
        var tvTitle: TextView = foView.tvLf
        var tvDescription: TextView = foView.tvDescription
    }
}