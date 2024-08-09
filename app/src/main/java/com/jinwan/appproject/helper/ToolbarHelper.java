package com.jinwan.appproject.helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.jinwan.appproject.R;

public class ToolbarHelper {

    private Toolbar toolbar;

    private FragmentLoader fragmentLoader;
    public ToolbarHelper(AppCompatActivity activity, int toolbarId) {
        toolbar = activity.findViewById(toolbarId);
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

}
