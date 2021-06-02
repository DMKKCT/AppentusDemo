package com.appentusdemo.binding

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appentusdemo.model.GetDataResponseBean
import java.util.*

class ImageListItem : BaseObservable() {

    public val imageList: MutableList<GetDataResponseBean> = ArrayList()
    val imageListFinal = MutableLiveData<List<GetDataResponseBean>>()

    fun addInList(bd: List<GetDataResponseBean>) {
        imageList.addAll(bd)
    }

    fun fetchList() {
        imageListFinal.setValue(imageList)
    }
}
