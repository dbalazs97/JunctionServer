package com.example.aletta.nokiaowerinternet.home;

import com.example.aletta.nokiaowerinternet.base.BasePresenter;
import com.example.aletta.nokiaowerinternet.base.BaseView;

public class HomeContract {

    interface HomeView extends BaseView {

    }

    abstract  class HomePresenter extends BasePresenter<HomeView>{

        public HomePresenter(HomeView view) {
            super(view);
        }
    }

}
