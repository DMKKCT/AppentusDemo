package com.appentusdemo.apiSetup;

import com.appentusdemo.model.GetDataResponseBean;

import java.util.List;

public interface ResponseListener {
    void getData(List<GetDataResponseBean> response);

    void getError(String error);
}
