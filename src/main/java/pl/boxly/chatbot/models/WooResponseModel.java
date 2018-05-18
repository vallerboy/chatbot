package pl.boxly.chatbot.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class WooResponseModel {
    private String status;
    private int id;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("next_payment_date")
    private String nextPaymentDate;

    @JsonProperty("billing_period")
    private String billingPeriod;

    @JsonProperty("billing_interval")
    private String billingInterval;


}
