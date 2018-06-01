package com.ccydsz.cloudtest.base;

import android.view.View;

public interface Activity {
//    public void loadData();
    public void setupUI();
    public void setupNavigationView();
    public void updateNavigationView();
    public void leftNavigationViewAction(View view);
    public void rightNavigationViewAction(View view);

}
