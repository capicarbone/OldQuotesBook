package com.medic.quotesbook.models;

import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.google.api.client.json.GenericJson;

/**
 * Created by capi on 25/01/15.
 */
public class Quote implements MessageBasedModelInterface {

    private String body;
    private String aditional;
    private Author author;
    // private Date dateRealized;


    public Quote(GenericJson message) {
        fromMessage(message);
    }

    public Quote(String body, String aditional) {
        this.body = body;
        this.aditional = aditional;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAditional() {
        return aditional;
    }

    public void setAditional(String aditional) {
        this.aditional = aditional;
    }

    @Override
    public void fromMessage(GenericJson message) {

        this.body = ((ApiMessagesQuoteMsg) message).getBody();
        this.aditional = ((ApiMessagesQuoteMsg) message).getAditional();
        this.author = new Author(((ApiMessagesQuoteMsg) message).getAuthor());
    }

    @Override
    public GenericJson toMessage() {
        return null;
    }
}
