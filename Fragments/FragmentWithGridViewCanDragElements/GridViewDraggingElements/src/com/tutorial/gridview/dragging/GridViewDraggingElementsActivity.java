package com.tutorial.gridview.dragging;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class GridViewDraggingElementsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        GridViewDraggableFragment fragment = new GridViewDraggableFragment();
        transaction.replace(R.id.main_layout_container, fragment);
        transaction.commit();
    }
}