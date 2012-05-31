package com.tutorial.listview.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ListWithCustomAdapterActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SimpleListFragment fragment = new SimpleListFragment();
        transaction.replace(R.id.main_layout_container, fragment);
        transaction.commit();
    }
}