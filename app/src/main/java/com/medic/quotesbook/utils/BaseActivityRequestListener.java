package com.medic.quotesbook.utils;

import com.medic.quotesbook.models.Quote;

/**
 * Created by capi on 12/04/15.
 */
public interface BaseActivityRequestListener {

    public void showQuote(Quote quote, boolean dayquote);
    public void showOption(int i);


}
