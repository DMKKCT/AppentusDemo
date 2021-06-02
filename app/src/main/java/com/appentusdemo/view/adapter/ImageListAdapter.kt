package com.appentusdemo.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.appentusdemo.R
import com.appentusdemo.R.drawable
import com.appentusdemo.R.drawable.*
import com.appentusdemo.model.GetDataResponseBean
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ImageListAdapter(
    @param:LayoutRes private val layoutId: Int,
    private val context: Context
) :
    RecyclerView.Adapter<ImageListAdapter.ImageItemView>() {
    private var imageList: List<GetDataResponseBean?>? = null
    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }

    override fun getItemCount(): Int {
        return if (imageList == null) 0 else imageList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemView {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.inflate_image_view, parent, false)
        return ImageItemView(itemView)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ImageItemView, position: Int) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(progress_animation)
            requestOptions.error(img_no_available)
            Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .load(imageList?.get(position)!!.download_url)
                .into(holder.img!!)
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun setImageListItem(rideHistoryList: List<GetDataResponseBean?>?) {
        this.imageList = rideHistoryList!!
        notifyDataSetChanged()
    }

    class ImageItemView(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        var img: ImageView? = null
        init {
            img = viewItem.findViewById(R.id.image_car_payment)
        }
    }
}