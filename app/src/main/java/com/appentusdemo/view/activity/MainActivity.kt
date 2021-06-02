package com.appentusdemo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.appentusdemo.R
import com.appentusdemo.databinding.ActivityMainBinding
import com.appentusdemo.viewModel.ActivityMainViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity() {
    val context = this
    var activityMainBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding!!.activityModel = ActivityMainViewModel(context)
        activityMainBinding!!.activityModel!!.startShimmring(activityMainBinding!!.sflMockContent)
        if (savedInstanceState == null) {
            activityMainBinding!!.activityModel!!.init(activityMainBinding!!)
        }
        activityMainBinding!!.executePendingBindings()
        setupListUpdate()
    }

    private fun setupListUpdate() {

//        activityMainBinding!!.activityModel!!.fetchList()
        activityMainBinding!!.activityModel!!.imageList.observe(context,
            { rideHistoryList ->
                if (rideHistoryList.isEmpty()) {
                    activityMainBinding!!.recyclerImglist.setVisibility(View.GONE)
                } else {
                    activityMainBinding!!.recyclerImglist.setVisibility(View.VISIBLE)
                    activityMainBinding!!.activityModel!!.setImageInAdapter(rideHistoryList)
                }
            })
    }
}