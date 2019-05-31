package com.api.controller.card;


import com.api.filter.ResponseMessage;
import com.wj.core.service.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CardController {


    @Autowired
    private CardService cardService;

    @RequestMapping("/card/save")
    public ResponseMessage saveUserCard(String token) {
        cardService.saveUserCard(token, null);
        return ResponseMessage.ok();
    }


}
