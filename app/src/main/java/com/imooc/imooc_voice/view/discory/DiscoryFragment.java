package com.imooc.imooc_voice.view.discory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.imooc_voice.R;

public class DiscoryFragment extends Fragment {

    public static Fragment newInstance() {
        return new DiscoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_discory_layout, null);
        return inflate;
    }
}
