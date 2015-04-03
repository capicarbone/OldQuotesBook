package com.medic.quotesbook.models;

import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesAuthorMsg;
import com.google.api.client.json.GenericJson;

/**
 * Created by capi on 01/02/15.
 */
public class Author implements MessageBasedModelInterface {

    private String fistName;
    private String lastName;
    private String shortDescription;
    private String biography;
    private String pictureURL;

    public Author(GenericJson message) {
        fromMessage(message);
    }

    public Author(String fistName, String lastName, String shortDescription, String biography) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.shortDescription = shortDescription;
        this.biography = biography;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
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

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
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
            return this.fistName;
        }

        if (this.fistName == null){
            return this.lastName;
        }

        return this.fistName + " " + this.lastName;
    }

    @Override
    public void fromMessage(GenericJson message) {
        ApiMessagesAuthorMsg msg = (ApiMessagesAuthorMsg) message;
        this.fistName = msg.getFirstName();
        this.lastName = msg.getLastName();
        this.shortDescription = msg.getShortDescription();
        this.biography = msg.getBiography();
        this.pictureURL = msg.getPictureUrl();
    }

    @Override
    public GenericJson toMessage() {
        return null;
    }
}
