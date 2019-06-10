package com.wj.admin.controller.op;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.dto.ServiceDTO;
import com.wj.core.service.op.ServeService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "/v1/service", tags = "服务接口模块")
@RestController
@RequestMapping("/v1/service/")
public class ServeController {

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
    public Object findByFamilyId(Integer familyId) {
        List<OpService> list = serviceService.findByFamilyId(familyId);
        return ResponseMessage.ok(list);
    }

    /**
     * 根据id查询服务详情
     * @param id
     * @return OpService
     * @author thz
     */
    @ApiOperation(value = "根据id查询服务详情", notes = "根据id查询服务详情")
    @GetMapping("findById")
    public Object findById(Integer id) {
        OpService opService = serviceService.findById(id);
        return ResponseMessage.ok(opService);
    }

    /**
     * 全部服务接口
     * @param type
     * @return List<ServiceDTO>
     * @author thz
     */
    @ApiOperation(value = "全部服务接口", notes = "全部服务接口")
    @GetMapping("all")
    public Object all(Integer type) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<ServiceDTO> list = serviceService.allList(type, userId);
        return ResponseMessage.ok(list);
    }

    /**
     * 订阅接口
     * @param serviceId
     * @param isSubscribe
     * @return Object
     * @author thz
     */
    @ApiOperation(value = "订阅接口", notes = "订阅接口")
    @GetMapping("subscribe")
    public Object subscribe(Integer serviceId, Integer isSubscribe) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        Object o = serviceService.saveFamilyService(serviceId, userId, isSubscribe);
        return ResponseMessage.ok();
    }
}
