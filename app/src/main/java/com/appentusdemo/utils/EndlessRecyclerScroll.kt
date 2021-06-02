package com.appentusdemo.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerScroll(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    var current_page = 1
    var fistVisibleItem = 0
    var lastVisibleItem = 0
    var loading = true
    private var previousTotal = 0
    private var totalItemCount = 0
    var visibleItemCount = 0
    private val visibleThreshold = 1
    abstract fun onLoadMore(i: Int)
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager.itemCount
        fistVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
        if (loading) {
            val i = totalItemCount
            if (i > previousTotal) {
                loading = false
                previousTotal = i
            }
        }
        if (!loading && totalItemCount == lastVisibleItem + visibleThreshold) {
            loading = true
            current_page++
            onLoadMore(current_page)
        }
    }
}