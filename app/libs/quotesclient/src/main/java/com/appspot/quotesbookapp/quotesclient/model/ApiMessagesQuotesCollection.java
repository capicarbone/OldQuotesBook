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
 * Model definition for ApiMessagesQuotesCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the quotesclient. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ApiMessagesQuotesCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<ApiMessagesQuoteMsg> quotes;

  static {
    // hack to force ProGuard to consider ApiMessagesQuoteMsg used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(ApiMessagesQuoteMsg.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<ApiMessagesQuoteMsg> getQuotes() {
    return quotes;
  }

  /**
   * @param quotes quotes or {@code null} for none
   */
  public ApiMessagesQuotesCollection setQuotes(java.util.List<ApiMessagesQuoteMsg> quotes) {
    this.quotes = quotes;
    return this;
  }

  @Override
  public ApiMessagesQuotesCollection set(String fieldName, Object value) {
    return (ApiMessagesQuotesCollection) super.set(fieldName, value);
  }

  @Override
  public ApiMessagesQuotesCollection clone() {
    return (ApiMessagesQuotesCollection) super.clone();
  }

}
