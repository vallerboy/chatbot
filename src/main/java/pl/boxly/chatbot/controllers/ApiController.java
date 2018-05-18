package pl.boxly.chatbot.controllers;


import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import pl.boxly.chatbot.models.ChatFuelResponseModel;
import pl.boxly.chatbot.models.Config;
import pl.boxly.chatbot.models.ResponseService;
import pl.boxly.chatbot.models.WooResponseModel;
import pl.boxly.chatbot.models.primitive.TextResponseModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApiController {

    final RestTemplate restTemplate;

    final ResponseService responseService;

    @Autowired
    public ApiController(ResponseService responseService) {
        this.restTemplate = new RestTemplate();
        this.responseService = responseService;
    }

    @GetMapping("/subscription/status/{id}")
    public ResponseEntity subscriptionStatus(@PathVariable("id") int id){
       ResponseEntity<WooResponseModel[]> wooResponseModel  = restTemplate.getForEntity(new Config.UrlBuilder(Config.UrlType.SUBSCRIPTION)
                .addQuestionMark()
                .addUserIDParametr(Config.userID)
                .addAndMark()
                .addUserSecret(Config.userSecret)
                .addAndMark()
                .addIncludeParametr(id)
                .build(), WooResponseModel[].class);



        List<TextResponseModel> textResponseModelList = new ArrayList<>();
        textResponseModelList.add(new TextResponseModel(responseService.createResponseForOneSubscription(wooResponseModel.getBody()[0])));

        return ResponseEntity.ok(textResponseModelList);
    }


    @GetMapping("/subscription/all/{email}")
    public ResponseEntity subscriptionStatus(@PathVariable("email") String email){
        ResponseEntity<WooResponseModel[]> userResponse  = restTemplate.getForEntity(new Config.UrlBuilder(Config.UrlType.WOOCOMMERCE)
                .addCustomer()
                .addQuestionMark()
                .addUserIDParametr(Config.userID)
                .addAndMark()
                .addUserSecret(Config.userSecret)
                .addAndMark()
                .addEmailParameter(email)
                .build(), WooResponseModel[].class);

        List<TextResponseModel> textResponseModelList = new ArrayList<>();


        WooResponseModel[] userArray = userResponse.getBody();

        if(Arrays.isNullOrEmpty(userArray)){
            textResponseModelList.add(new TextResponseModel("Nie znaleziono takiego użytkownika :("));
            return ResponseEntity.ok(textResponseModelList);
        }

        ResponseEntity<WooResponseModel[]> subscribeResponse  = restTemplate.getForEntity(new Config.UrlBuilder(Config.UrlType.SUBSCRIPTION)
                .addQuestionMark()
                .addUserIDParametr(Config.userID)
                .addAndMark()
                .addUserSecret(Config.userSecret)
                .addAndMark()
                .addCustomerParametr(userArray[0].getId()) //Zakładamy, że znajdzie jednego usera
                .build(), WooResponseModel[].class);

        WooResponseModel[] subscriptionArray = userResponse.getBody();
        if(Arrays.isNullOrEmpty(subscriptionArray)){
            textResponseModelList.add(new TextResponseModel("Ejo! Nie masz żadnych aktywnych boxów"));
            return ResponseEntity.ok(textResponseModelList);
        }

        for (WooResponseModel wooResponseModel : subscriptionArray) {
            textResponseModelList.add(new TextResponseModel(responseService.createResponseForOneSubscription(wooResponseModel)));
        }

        return null;
    }


}
