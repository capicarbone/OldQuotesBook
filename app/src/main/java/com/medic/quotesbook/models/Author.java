package com.medic.quotesbook.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesAuthorMsg;
import com.google.api.client.json.GenericJson;

/**
 * Created by capi on 01/02/15.
 */
public class Author implements MessageBasedModelInterface, Parcelable {

    private String firstName;
    private String lastName;
    private String shortDescription;
    private String biography;
    private String pictureUrl;

    public Author(GenericJson message) {
        fromMessage(message);
    }

    public Author(String firstName, String lastName, String shortDescription, String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.shortDescription = shortDescription;
        this.biography = biography;
    }

    public Author(Parcel in){
        firstName = in.readString();
        lastName = in.readString();
        shortDescription = in.readString();
        biography = in.readString();
        pictureUrl = in.readString();

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getFullName(){

        if (this.lastName == null){
            return this.firstName;
        }

        if (this.firstName == null){
            return this.lastName;
        }

        return this.firstName + " " + this.lastName;
    }

    public String getFullPictureURL(){
        return "http://quotesbookapp.appspot.com/" + getPictureUrl();
    }

    @Override
    public void fromMessage(GenericJson message) {
        ApiMessagesAuthorMsg msg = (ApiMessagesAuthorMsg) message;
        this.firstName = msg.getFirstName();
        this.lastName = msg.getLastName();
        this.shortDescription = msg.getShortDescription();
        this.biography = msg.getBiography();
        this.pictureUrl = msg.getPictureUrl();
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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(shortDescription);
        dest.writeString(biography);
        dest.writeString(pictureUrl);
    }

    public static final Parcelable.Creator<Author> CREATOR
            = new Parcelable.Creator<Author>() {
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
