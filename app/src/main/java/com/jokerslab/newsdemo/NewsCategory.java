package com.jokerslab.newsdemo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.jokerslab.newsdemo.NewsCategory.ECONOMY;
import static com.jokerslab.newsdemo.NewsCategory.EDUCATION;
import static com.jokerslab.newsdemo.NewsCategory.FOREIGN_AFFAIR;
import static com.jokerslab.newsdemo.NewsCategory.LIFE_STYLE;
import static com.jokerslab.newsdemo.NewsCategory.SPORTS;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by sayem on 11/30/2016.
 */

@Retention(SOURCE)
@IntDef({SPORTS, ECONOMY, LIFE_STYLE, EDUCATION, FOREIGN_AFFAIR})
public @interface NewsCategory {
    int SPORTS = 1;
    int ECONOMY = 2;
    int LIFE_STYLE = 3;
    int EDUCATION = 4;
    int FOREIGN_AFFAIR = 5;
}
