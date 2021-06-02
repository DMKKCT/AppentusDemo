package com.appentusdemo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.appentusdemo.R
import com.appentusdemo.model.GetDataResponseBean
import com.appentusdemo.viewModel.ActivityMainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ImageListAdapter(
    @param:LayoutRes private val layoutId: Int,
    private val context: Context
) :
    RecyclerView.Adapter<ImageListAdapter.HistoryRideItemView>() {
    private var historyRideList: List<GetDataResponseBean?>? = null
    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }

    override fun getItemCount(): Int {
        return if (historyRideList == null) 0 else historyRideList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRideItemView {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.inflate_image_view, parent, false)
        return HistoryRideItemView(itemView)
    }

    override fun onBindViewHolder(holder: HistoryRideItemView, position: Int) {
//        val requestOptions = RequestOptions()
//        requestOptions.placeholder(null)
//        requestOptions.error(null)
//        holder.img?.let {
//            Glide.with(context!!)
//                .setDefaultRequestOptions(requestOptions)
//                .asBitmap()
//                .load(historyRideList?.get(position)!!.download_url)
//                .into(it)
            holder.text!!.setText("" + position)
            val requestOptions = RequestOptions()
            requestOptions.placeholder(null)
            requestOptions.error(null)
            Glide.with(context!!)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .load(historyRideList?.get(position)!!.download_url)
                .into(holder.img!!)
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun setImageListItem(rideHistoryList: List<GetDataResponseBean?>?) {
        this.historyRideList = rideHistoryList!!
        notifyDataSetChanged()
    }

    class HistoryRideItemView(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
//        fun bind(context: Context, downloadURL:String) {

        //            viewModelDemo.fetchRideHistoryAt(position)
//            binding.setVariable(BR.tripModel, viewModelDemo)
//            binding.setVariable(BR.tripModel, position)
//
//            binding.executePendingBindings()
        var img: ImageView? = null;
        var text: TextView? = null
        init {
            img = viewItem.findViewById(R.id.image_car_payment)
            text = viewItem.findViewById(R.id.txtTest)

        }
//        }
    }

}