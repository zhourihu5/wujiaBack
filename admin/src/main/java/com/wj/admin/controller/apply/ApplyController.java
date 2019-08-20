package com.wj.admin.controller.apply;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.PadModule;
import com.wj.core.entity.card.dto.CreateCardDTO;
import com.wj.core.service.SendSms;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.card.CardService;
import com.wj.core.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Api(value="/v1/lock", tags="开锁用户审核模块")
@RestController
@RequestMapping("/v1")
public class ApplyController {


    @Autowired
    private ApplyLockService applyLockService;

    @Autowired
    private SendSms sendSms;

    @ApiOperation(value="审核接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", dataType = "String", value = "审核是否通过 0.待审核 1.通过 2.不通过"),
            @ApiImplicitParam(name = "remark", dataType = "String", value = "驳回原因"),
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID")
    })
    @GetMapping("/lock/audit")
    public ResponseMessage remove(String status, Integer id, String remark) {
        applyLockService.modityStatus(status, remark, id);
//        String message = sendSms.send(userName, smsCode);
        return ResponseMessage.ok();
    }


    @ApiOperation(value="审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "startDate", dataType = "Date", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", dataType = "Date", value = "结束时间"),
            @ApiImplicitParam(name = "status", dataType = "String", value = "审核是否通过 0.待审核 1.通过 2.不通过"),
            @ApiImplicitParam(name = "userName", dataType = "String", value = "用户名")
    })
    @GetMapping("/apply/list")
    public ResponseMessage<Page<ApplyLock>> list(Integer pageNum, Integer pageSize, Date startDate, Date endDate, String status, String userName) {
        return ResponseMessage.ok(applyLockService.getList(pageNum, pageSize, startDate, endDate, status, userName));
    }


}
