package com.appentusdemo.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.appentusdemo.R
import com.appentusdemo.apiSetup.ResponseListener
import com.appentusdemo.binding.ImageListItem
import com.appentusdemo.databinding.ActivityMainBinding
import com.appentusdemo.model.GetDataResponseBean
import com.appentusdemo.utils.EndlessRecyclerScroll
import com.appentusdemo.view.adapter.ImageListAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import com.wheelr.apiSetup.RetrofitClient
import com.wheelr.utils.AppUtil


class ActivityMainViewModel(var mContext: Context) : ViewModel() {
    var activityMainBinding: ActivityMainBinding? = null
    private var IMageListItem: ImageListItem? = null
    var imageListAdapter: ImageListAdapter? = null
    var shimmring: ShimmerFrameLayout? = null
    fun init(activityMainBinding: ActivityMainBinding) {
        this.activityMainBinding = activityMainBinding
        IMageListItem = ImageListItem()
        var layoutManager = GridLayoutManager(mContext, 2)
        activityMainBinding!!.recyclerImglist.layoutManager = layoutManager
        activityMainBinding!!.recyclerImglist.setHasFixedSize(true)
        activityMainBinding!!.recyclerImglist.addOnScrollListener(object :
            EndlessRecyclerScroll(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                if (current_page <= 3)
                    callGetImageListApi(current_page)
            }
        })
        imageListAdapter = ImageListAdapter(R.layout.inflate_image_view, mContext)

        callGetImageListApi(1)
    }

    fun startShimmring(shimmring: ShimmerFrameLayout) {
        this.shimmring = shimmring;
        this.shimmring!!.startShimmerAnimation()
    }


    fun getAdapter(): ImageListAdapter? {
        return imageListAdapter
    }

    fun setImageInAdapter(imageList: List<GetDataResponseBean?>?) {
        imageListAdapter!!.setImageListItem(imageList)
        imageListAdapter!!.notifyDataSetChanged()
    }

    fun onItemClick(index: Int?) {

    }

    fun fetchList() {
        IMageListItem!!.fetchList()
    }

    val imageList: MutableLiveData<List<GetDataResponseBean>>
        get() = IMageListItem!!.imageListFinal

    fun callGetImageListApi(page: Int) {
        if (page > 1) {
            activityMainBinding!!.pgrogress.visibility = View.VISIBLE
        }
        var map: HashMap<String, String>? = HashMap<String, String>()
        map!!.put("page", page.toString())
        RetrofitClient.callApi(mContext!!, map, object : ResponseListener {
            override fun getError(response: String?) {
                response?.let { AppUtil.showToast(mContext, it) }
                activityMainBinding!!.pgrogress.visibility = View.GONE
                shimmring!!.visibility = View.GONE
            }

            override fun getData(response: List<GetDataResponseBean>?) {
                shimmring!!.visibility = View.GONE
                activityMainBinding!!.pgrogress.visibility = View.GONE
                if (response!!.size > 0)
                    IMageListItem!!.addInList(response)
                else {
                    IMageListItem!!.imageList.clear()
                }
                IMageListItem!!.fetchList()
            }
        })
    }
}