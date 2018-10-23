package com.example.aletta.nokiaowerinternet.base;

public abstract class BasePresenter<T extends BaseView> {

    private T view;

    public BasePresenter(T view) {
        this.view = view;
    }
}
