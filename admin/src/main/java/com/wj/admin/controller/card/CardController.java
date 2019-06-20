package com.wj.admin.controller.card;


import com.wj.admin.controller.op.ServiceCategoryController;
import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.card.OpCard;
import com.wj.core.service.card.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CardController {

    private final static Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @GetMapping("/card/save")
    public ResponseMessage saveUserCard(@RequestBody OpCard card) {
        cardService.saveUserCard("", null);
        return ResponseMessage.ok();
    }

    @GetMapping("/card/all")
    public ResponseMessage<List<OpCard>> getUserAllCard() {
        List<OpCard> list = cardService.getCardList("");
        return ResponseMessage.ok(list);
    }

    @PostMapping("/card/remove")
    public ResponseMessage removeCard() {
        cardService.removeUserCard("", 1);
        return ResponseMessage.ok();
    }

}
