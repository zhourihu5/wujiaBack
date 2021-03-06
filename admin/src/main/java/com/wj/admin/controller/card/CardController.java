package com.wj.admin.controller.card;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.PadModule;
import com.wj.core.entity.card.dto.CreateCardDTO;
import com.wj.core.service.card.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="/v1/card", tags="卡片接口模块")
@RestController
@RequestMapping("/v1")
public class CardController {


    @Autowired
    private CardService cardService;


    @ApiOperation(value="添加卡片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardDTO", dataType = "CreateCardDTO", value = "卡片实体", required = true)
    })
    @PostMapping(value = "/card/save")
    public ResponseMessage save(@RequestBody CreateCardDTO cardDTO) {
        cardService.saveCard(cardDTO);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="停止卡片推送")
    @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID", required = true)
    @PostMapping("/card/remove")
    public ResponseMessage remove(@RequestBody CreateCardDTO cardDTO) {
        cardService.removeCard(cardDTO.getId());
        return ResponseMessage.ok();
    }


    @ApiOperation(value="卡片列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "status", dataType = "Integer", value = "卡片状态 0 推送中 1 停止推送"),
            @ApiImplicitParam(name = "type", dataType = "Integer", value = "卡片类型 0.功能 1.外链 2.内链 3.图文")
    })
    @GetMapping("/card/list")
    public ResponseMessage<Page<OpCard>> list(Integer pageNum, Integer pageSize, Integer status, Integer type) {
        return ResponseMessage.ok(cardService.getList(pageNum, pageSize, type, status));
    }

    @ApiOperation(value="获取内链卡片模块")
    @GetMapping("/card/module")
    public ResponseMessage<List<PadModule>> moduleList() {
        return  ResponseMessage.ok(cardService.getPadModuleList());
    }

}
