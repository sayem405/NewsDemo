package com.jokerslab.newsdemo;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by sayem on 11/16/2016.
 */

public interface DataBinder<T> {
    public void bindData(T data);
}
