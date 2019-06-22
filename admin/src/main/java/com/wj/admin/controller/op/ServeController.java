package com.wj.admin.controller.op;

import com.wj.admin.controller.user.UserRoleController;
import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.dto.OpServiceDTO;
import com.wj.core.entity.user.dto.ServiceDTO;
import com.wj.core.service.op.ServeService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Api(value = "/v1/service", tags = "服务接口模块")
@RestController
@RequestMapping("/v1/service/")
public class ServeController {

    private final static Logger logger = LoggerFactory.getLogger(ServeController.class);

    @Autowired
    private ServeService serviceService;

    @ApiOperation(value = "添加/更新服务", notes = "添加/更新服务")
    @PostMapping("addService")
    public ResponseMessage addService(@RequestBody OpService service) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("添加/更新服务接口:/v1/service/addService userId=" + claims.get("userId"));
        serviceService.saveService(service);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "用户订阅服务列表排行榜", notes = "用户订阅服务列表排行榜")
    @GetMapping("subscribeList")
    public ResponseMessage<List<OpService>> subscribeList() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("用户订阅服务列表接口:/v1/service/subscribeList userId=" + claims.get("userId"));
        List<OpService> serviceList = serviceService.findAll();
        serviceList.forEach(OpService -> {
            OpService.setSubscribeNum(serviceService.findCountByServiceId(OpService.getId()));
        });
        //排序 降序
        Collections.sort(serviceList, new Comparator<OpService>(){
            public int compare(OpService o1, OpService o2) {
                return o2.getSubscribeNum()-o1.getSubscribeNum();
            }});
        return ResponseMessage.ok(serviceList);
    }

    @ApiOperation(value = "服务分页列表", notes = "服务分页列表")
    @GetMapping("findAll")
    public ResponseMessage<Page<OpService>> findAll(String title, Integer status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("广告分页信息 接口:/v1/service/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<OpService> page = serviceService.findAllPage(title, status, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "发现管理/政务服务", notes = "发现管理/政务服务")
    @PostMapping("updateType")
    public ResponseMessage updateType(@RequestBody OpServiceDTO serviceDTO) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("发现管理/政务服务 接口:/v1/service/updateType userId=" + claims.get("userId"));
        serviceService.updateType(serviceDTO.getType(), serviceDTO.getId());
        return ResponseMessage.ok();
    }
}
