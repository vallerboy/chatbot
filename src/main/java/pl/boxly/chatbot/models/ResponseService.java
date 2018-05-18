package pl.boxly.chatbot.models;


import org.springframework.stereotype.Service;

@Service
public class ResponseService {


    public String createResponseForOneSubscription(WooResponseModel wooResponseModel){
        String statusText =  "To zamówienie jest " + ( wooResponseModel.getStatus().contains("active") ? " aktywne " : " wstrzymane ");
        String nextPeriod = "Następna dostawa i płatność: " + wooResponseModel.getNextPaymentDate();
        String startPeriod = "Subskrybcja rozpoczęła się: " + wooResponseModel.getStartDate();
        String interval = "Co ile dostajesz paczkę?: " + wooResponseModel.getBillingInterval() + " " + wooResponseModel.getBillingPeriod();

        return statusText + "\n" + nextPeriod + "\n" + startPeriod + "\n" + interval;
    }

}
