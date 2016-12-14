package com.jokerslab.newsdemo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jokerslab.newsdemo.AddingType.END;
import static com.jokerslab.newsdemo.AddingType.NEW;
import static com.jokerslab.newsdemo.AddingType.START;

/**
 * Created by sayem on 12/14/2016.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({NEW,START,END})
public @interface AddingType {
    int NEW = 1;
    int START = 2;
    int END = 3;
}
