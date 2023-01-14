package com.example.towerssystem.interfaces;

import java.util.List;

public interface ContentCallBack<Model> {
    void onSuccess(List<Model> list);
    void  onFailure(String message);
}
