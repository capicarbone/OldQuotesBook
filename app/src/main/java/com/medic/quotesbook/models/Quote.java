package com.medic.quotesbook.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.google.api.client.json.GenericJson;

/**
 * Created by capi on 25/01/15.
 */
public class Quote implements MessageBasedModelInterface, Parcelable {

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

    public Quote(Parcel in){

        body = in.readString();
        aditional = in.readString();
        author = in.readParcelable(Author.class.getClassLoader());
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(body);
        dest.writeString(aditional);
        dest.writeParcelable(author, 0);
    }

    public static final Parcelable.Creator<Quote> CREATOR
            = new Parcelable.Creator<Quote>() {
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };
}
