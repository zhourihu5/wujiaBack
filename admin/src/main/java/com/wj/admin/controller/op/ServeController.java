package com.wj.admin.controller.op;

import com.wj.admin.controller.user.UserRoleController;
import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.dto.ServiceDTO;
import com.wj.core.service.op.ServeService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/service", tags = "服务接口模块")
@RestController
@RequestMapping("/v1/service/")
public class ServeController {

    private final static Logger logger = LoggerFactory.getLogger(ServeController.class);

    @Autowired
    private ServeService serviceService;

    /**
     * 我的服务列表
     * @param familyId
     * @return List<OpService>
     * @author thz
     */
    @ApiOperation(value = "根据id查询服务详情", notes = "根据id查询服务详情")
    @GetMapping("findByFamilyId")
    public ResponseMessage<List<OpService>> findByFamilyId(Integer familyId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据id查询服务详情接口:/v1/service/findByFamilyId userId=" + claims.get("userId"));
        List<OpService> list = serviceService.findByFamilyId(familyId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "添加/更新服务", notes = "添加/更新服务")
    @PostMapping("addService")
    public ResponseMessage addService(@RequestBody OpService service) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("添加/更新服务接口:/v1/service/addService userId=" + claims.get("userId"));
        serviceService.saveService(service);
        return ResponseMessage.ok();
    }

}
