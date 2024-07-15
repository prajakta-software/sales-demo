package com.prajakta_softwaredevloper.salesforcedemo.View.adapter;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No action needed
    }

    @Override
    public void afterTextChanged(Editable s) {
        // No action needed
    }
}