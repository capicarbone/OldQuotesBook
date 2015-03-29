/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-03-29 at 06:38:47 UTC 
 * Modify at your own risk.
 */

package com.appspot.quotesbookapp.quotesclient.model;

/**
 * Model definition for ApiMessagesQuoteMsg.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the quotesclient. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ApiMessagesQuoteMsg extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String aditional;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private ApiMessagesAuthorMsg author;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String body;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String date;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String key;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAditional() {
    return aditional;
  }

  /**
   * @param aditional aditional or {@code null} for none
   */
  public ApiMessagesQuoteMsg setAditional(java.lang.String aditional) {
    this.aditional = aditional;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public ApiMessagesAuthorMsg getAuthor() {
    return author;
  }

  /**
   * @param author author or {@code null} for none
   */
  public ApiMessagesQuoteMsg setAuthor(ApiMessagesAuthorMsg author) {
    this.author = author;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBody() {
    return body;
  }

  /**
   * @param body body or {@code null} for none
   */
  public ApiMessagesQuoteMsg setBody(java.lang.String body) {
    this.body = body;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDate() {
    return date;
  }

  /**
   * @param date date or {@code null} for none
   */
  public ApiMessagesQuoteMsg setDate(java.lang.String date) {
    this.date = date;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public ApiMessagesQuoteMsg setKey(java.lang.String key) {
    this.key = key;
    return this;
  }

  @Override
  public ApiMessagesQuoteMsg set(String fieldName, Object value) {
    return (ApiMessagesQuoteMsg) super.set(fieldName, value);
  }

  @Override
  public ApiMessagesQuoteMsg clone() {
    return (ApiMessagesQuoteMsg) super.clone();
  }

}
