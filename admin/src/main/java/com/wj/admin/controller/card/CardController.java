package com.wj.admin.controller.card;


import com.wj.admin.controller.op.ServiceCategoryController;
import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.PadModule;
import com.wj.core.entity.card.dto.CreateCardDTO;
import com.wj.core.service.card.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.util.List;

@Api(value="/v1/card", tags="卡片接口模块")
@RestController
@RequestMapping("/v1")
public class CardController {


    @Autowired
    private CardService cardService;


    @ApiOperation(value="添加卡片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardDTO", dataType = "CreateCardDTO", value = "卡片实体", required = true),
            @ApiImplicitParam(name = "file", dataType = "MultipartFile", value = "卡片实体", required = true)
    })
    @PostMapping("/card/save")
    public ResponseMessage save(@ModelAttribute CreateCardDTO cardDTO, @RequestParam("file") MultipartFile file) {
        cardService.saveCard(cardDTO, file);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="停止卡片推送")
    @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID", required = true)
    @PostMapping("/card/remove")
    public ResponseMessage remove(Integer id) {
        cardService.removeCard(id);
        return ResponseMessage.ok();
    }


    @ApiOperation(value="卡片列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "status", dataType = "Integer", value = "卡片状态 0 推送中 1 停止推送"),
            @ApiImplicitParam(name = "type", dataType = "Integer", value = "卡片类型 0.功能 1.外链 2.内链 3.图文")
    })
    @GetMapping("/card/list")
    public ResponseMessage<Page<OpCard>> list(Integer pageNo, Integer pageSize, Integer status, Integer type) {
        return ResponseMessage.ok(cardService.getList(pageNo, pageSize, type, status));
    }

    @ApiOperation(value="获取内链卡片模块")
    @GetMapping("/card/module")
    public ResponseMessage<List<PadModule>> moduleList() {
        return  ResponseMessage.ok(cardService.getPadModuleList());
    }

}
