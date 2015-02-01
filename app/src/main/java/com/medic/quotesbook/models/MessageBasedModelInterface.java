package com.medic.quotesbook.models;

import com.google.api.client.json.GenericJson;

/**
 * Created by capi on 01/02/15.
 */
public interface MessageBasedModelInterface {

    public void fromMessage(GenericJson message);
    public GenericJson toMessage();
}
