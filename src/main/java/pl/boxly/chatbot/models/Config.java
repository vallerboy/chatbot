package pl.boxly.chatbot.models;

import java.net.URI;

public class Config {
    public enum UrlType{
        SUBSCRIPTION, WOOCOMMERCE;
    }

    public static final String userID = "ck_b3ce2f3c8b45593e04bb838a3838999a6f96e1f5";
    public static final String userSecret = "cs_1370ee73feb792373943d3014f7af8da22716760";


    public static class UrlBuilder {
        private String startUrl = "";

        public UrlBuilder(UrlType urlType){
            switch (urlType){
                case WOOCOMMERCE: {
                    startUrl += "https://boxly.pl/wp-json/wc/v2/";
                    break;
                }
                case SUBSCRIPTION: {
                    startUrl += "https://boxly.pl/wp-json/wc/v1/subscriptions"; //todo edit url
                    break;
                }
            }
        }


        public UrlBuilder addQuestionMark()  {
            startUrl += '?';
            return this;
        }

        public UrlBuilder addIncludeParametr(int include){
            startUrl += "include=" + include;
            return this;
        }

        public UrlBuilder addEmailParameter(String email){
            startUrl += "email=" + email;
            return this;
        }

        public UrlBuilder addCustomerParametr(int customerId){
            startUrl += "customer=" + customerId;
            return this;
        }

        public UrlBuilder addAndMark()  {
            startUrl += '&';
            return this;
        }

        public UrlBuilder addUserIDParametr(String userId){
            startUrl += "consumer_key=" + userId;
            return this;
        }

        public UrlBuilder addUserSecret(String userSecret){
            startUrl += "consumer_secret=" + userSecret;
            return this;
        }

        public UrlBuilder addCustomer(){
            startUrl += "customers";
            return this;
        }

        public String build(){
            return startUrl;
        }

    }
}
