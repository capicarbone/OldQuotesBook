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
 * (build: 2015-05-05 20:00:12 UTC)
 * on 2015-05-10 at 23:25:48 UTC 
 * Modify at your own risk.
 */

package com.appspot.quotesbookapp.quotesclient;

/**
 * Service definition for Quotesclient (v1).
 *
 * <p>
 * API for quotes clients.
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link QuotesclientRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Quotesclient extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the filez.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the quotesclient library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://quotesbookapp.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "quotesclient/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Quotesclient(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Quotesclient(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the GcmClient collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Quotesclient quotesclient = new Quotesclient(...);}
   *   {@code Quotesclient.GcmClient.List request = quotesclient.gcmClient().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public GcmClient gcmClient() {
    return new GcmClient();
  }

  /**
   * The "gcmClient" collection of methods.
   */
  public class GcmClient {

    /**
     * Create a request for the method "gcmClient.save".
     *
     * This request holds the parameters needed by the quotesclient server.  After setting any optional
     * parameters, call the {@link Save#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public Save save() throws java.io.IOException {
      Save result = new Save();
      initialize(result);
      return result;
    }

    public class Save extends QuotesclientRequest<Void> {

      private static final String REST_PATH = "gcm_client/save";

      /**
       * Create a request for the method "gcmClient.save".
       *
       * This request holds the parameters needed by the the quotesclient server.  After setting any
       * optional parameters, call the {@link Save#execute()} method to invoke the remote operation. <p>
       * {@link Save#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected Save() {
        super(Quotesclient.this, "POST", REST_PATH, null, Void.class);
      }

      @Override
      public Save setAlt(java.lang.String alt) {
        return (Save) super.setAlt(alt);
      }

      @Override
      public Save setFields(java.lang.String fields) {
        return (Save) super.setFields(fields);
      }

      @Override
      public Save setKey(java.lang.String key) {
        return (Save) super.setKey(key);
      }

      @Override
      public Save setOauthToken(java.lang.String oauthToken) {
        return (Save) super.setOauthToken(oauthToken);
      }

      @Override
      public Save setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Save) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Save setQuotaUser(java.lang.String quotaUser) {
        return (Save) super.setQuotaUser(quotaUser);
      }

      @Override
      public Save setUserIp(java.lang.String userIp) {
        return (Save) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key("gcm_id")
      private java.lang.String gcmId;

      /**

       */
      public java.lang.String getGcmId() {
        return gcmId;
      }

      public Save setGcmId(java.lang.String gcmId) {
        this.gcmId = gcmId;
        return this;
      }

      @Override
      public Save set(String parameterName, Object value) {
        return (Save) super.set(parameterName, value);
      }
    }

  }

  /**
   * An accessor for creating requests from the Quotes collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Quotesclient quotesclient = new Quotesclient(...);}
   *   {@code Quotesclient.Quotes.List request = quotesclient.quotes().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Quotes quotes() {
    return new Quotes();
  }

  /**
   * The "quotes" collection of methods.
   */
  public class Quotes {

    /**
     * Create a request for the method "quotes.all".
     *
     * This request holds the parameters needed by the quotesclient server.  After setting any optional
     * parameters, call the {@link All#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public All all() throws java.io.IOException {
      All result = new All();
      initialize(result);
      return result;
    }

    public class All extends QuotesclientRequest<com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection> {

      private static final String REST_PATH = "quotes/all";

      /**
       * Create a request for the method "quotes.all".
       *
       * This request holds the parameters needed by the the quotesclient server.  After setting any
       * optional parameters, call the {@link All#execute()} method to invoke the remote operation. <p>
       * {@link All#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected All() {
        super(Quotesclient.this, "GET", REST_PATH, null, com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public All setAlt(java.lang.String alt) {
        return (All) super.setAlt(alt);
      }

      @Override
      public All setFields(java.lang.String fields) {
        return (All) super.setFields(fields);
      }

      @Override
      public All setKey(java.lang.String key) {
        return (All) super.setKey(key);
      }

      @Override
      public All setOauthToken(java.lang.String oauthToken) {
        return (All) super.setOauthToken(oauthToken);
      }

      @Override
      public All setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (All) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public All setQuotaUser(java.lang.String quotaUser) {
        return (All) super.setQuotaUser(quotaUser);
      }

      @Override
      public All setUserIp(java.lang.String userIp) {
        return (All) super.setUserIp(userIp);
      }

      @Override
      public All set(String parameterName, Object value) {
        return (All) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "quotes.some".
     *
     * This request holds the parameters needed by the quotesclient server.  After setting any optional
     * parameters, call the {@link Some#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public Some some() throws java.io.IOException {
      Some result = new Some();
      initialize(result);
      return result;
    }

    public class Some extends QuotesclientRequest<com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection> {

      private static final String REST_PATH = "quotes/some";

      /**
       * Create a request for the method "quotes.some".
       *
       * This request holds the parameters needed by the the quotesclient server.  After setting any
       * optional parameters, call the {@link Some#execute()} method to invoke the remote operation. <p>
       * {@link Some#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected Some() {
        super(Quotesclient.this, "GET", REST_PATH, null, com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public Some setAlt(java.lang.String alt) {
        return (Some) super.setAlt(alt);
      }

      @Override
      public Some setFields(java.lang.String fields) {
        return (Some) super.setFields(fields);
      }

      @Override
      public Some setKey(java.lang.String key) {
        return (Some) super.setKey(key);
      }

      @Override
      public Some setOauthToken(java.lang.String oauthToken) {
        return (Some) super.setOauthToken(oauthToken);
      }

      @Override
      public Some setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Some) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Some setQuotaUser(java.lang.String quotaUser) {
        return (Some) super.setQuotaUser(quotaUser);
      }

      @Override
      public Some setUserIp(java.lang.String userIp) {
        return (Some) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.Integer limit;

      /**

       */
      public java.lang.Integer getLimit() {
        return limit;
      }

      public Some setLimit(java.lang.Integer limit) {
        this.limit = limit;
        return this;
      }

      @Override
      public Some set(String parameterName, Object value) {
        return (Some) super.set(parameterName, value);
      }
    }

  }

  /**
   * Builder for {@link Quotesclient}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Quotesclient}. */
    @Override
    public Quotesclient build() {
      return new Quotesclient(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link QuotesclientRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setQuotesclientRequestInitializer(
        QuotesclientRequestInitializer quotesclientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(quotesclientRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
