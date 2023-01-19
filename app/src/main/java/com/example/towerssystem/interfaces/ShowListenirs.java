package com.example.towerssystem.interfaces;

import com.example.towerssystem.models.ShowCategorie;

import java.util.List;

public interface ShowListenirs {
    void onSuccess(List<ShowCategorie> list);
    void onFailure(String message);
}
