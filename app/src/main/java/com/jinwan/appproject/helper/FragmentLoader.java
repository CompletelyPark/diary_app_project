package com.jinwan.appproject.helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.jinwan.appproject.R;

public class FragmentLoader {

    private final FragmentManager fragmentManager;

    public FragmentLoader(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
