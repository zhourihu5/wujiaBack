package com.wj.admin.controller.op;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.SysMessageUser;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.message.MessageService;
import com.wj.core.service.op.AdvService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Api(value="/v1/adv", tags="广告接口模块")
@RestController
@RequestMapping("/v1/adv/")
public class AdvController {

    private final static Logger logger = LoggerFactory.getLogger(AdvController.class);


    @Autowired
    private AdvService advService;


    @ApiOperation(value="保存广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "OpAdv", dataType = "OpAdv", value = "广告实体"),
    })
    @PostMapping("saveAdv")
    public ResponseMessage saveAdv(@RequestBody OpAdv adv) {
        advService.saveAdv(adv);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="删除广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "OpAdv", dataType = "OpAdv", value = "广告实体"),
    })
    @PostMapping("delAdv")
    public ResponseMessage delAdv(@RequestBody OpAdv adv) {
        advService.delAdv(adv.getId());
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "广告分页信息", notes = "广告分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<OpAdv>> findAll(String title, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("广告分页信息 接口:/v1/adv/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<OpAdv> page = advService.findAll(pageable);
        return ResponseMessage.ok(page);
    }


    @ApiOperation(value="保存并推送广告")
    @PostMapping("pushAdv")
    public ResponseMessage pushAdv(@RequestBody OpAdv adv) {
        OpAdv opAdv = advService.saveAdv(adv);
        if (opAdv == null) throw new ServiceException("数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        advService.pushAdv(opAdv.getId(), adv.getCommuntity());
        return ResponseMessage.ok();
    }


}
