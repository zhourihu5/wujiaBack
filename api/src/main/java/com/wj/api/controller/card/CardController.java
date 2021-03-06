package com.wj.api.controller.card;


import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.dto.CardDTO;
import com.wj.core.entity.card.dto.CardDetailDTO;
import com.wj.core.service.card.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wj.api.utils.JwtUtil.*;

@Api(value="/v1/card", tags="卡片接口模块")
@RestController
@RequestMapping("/v1")
public class CardController {


    @Autowired
    private CardService cardService;

    @ApiOperation(value="添加卡片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "卡片ID", required = true)})
    @PostMapping("/card/save")
    public ResponseMessage saveUserCard(Integer id) {
        Integer userId = getUserIdFromToken(getJwtToken());
        cardService.saveUserCard(userId, id);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="获取用户卡片")
    @GetMapping("/card/user")
    public ResponseMessage<List<CardDTO>> getUserCard() {
        Integer userId = getUserIdFromToken(getJwtToken());
        List<CardDTO> list = cardService.getUserCard(userId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value="获取推送给用户的所有卡片")
    @GetMapping("/card/all")
    public ResponseMessage<List<OpCard>> getUserAllCard() {
        Integer userId = getUserIdFromToken(getJwtToken());
        List<OpCard> list = cardService.getCardList(userId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value="删除卡片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "卡片ID", required = true)})
    @PostMapping("/card/remove")
    public ResponseMessage removeCard(@RequestParam("id") Integer id) {
        Integer userId = getUserIdFromToken(getJwtToken());
        cardService.removeUserCard(userId, id);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="卡片详情")
    @GetMapping("/card/detail")
    @ApiImplicitParam(name = "id", dataType = "Integer", value = "卡片ID", required = true)
    public ResponseMessage<CardDetailDTO> getDetail(Integer id) {
        return ResponseMessage.ok(cardService.getCardDetail(id));
    }

}
