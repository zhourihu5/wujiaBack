package com.wj.api.controller.op;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.api.utils.ResultUtil;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.dto.ServiceAllDTO;
import com.wj.core.entity.user.dto.ServiceDTO;
import com.wj.core.service.op.ServeService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseMessage<List<OpService>> findByFamilyId(Integer familyId) {
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
    public ResponseMessage<OpService> findById(Integer id) {
        OpService opService = serviceService.findById(id);
        return ResponseMessage.ok(opService);
    }

    /**
     * 服务接口
     * @param type
     * @return ServiceAllDTO
     * @author thz
     */
    @ApiOperation(value = "服务接口", notes = "服务接口")
    @ApiImplicitParam(name = "type", dataType = "Integer", value = "1.我的服务 2.发现 3.政务 4.全部服务", required = true)
    @GetMapping("findListByType")
    public ResponseMessage<ServiceAllDTO> findListByType(Integer type) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        ServiceAllDTO serviceAllDTO = serviceService.findListByType(type, userId);
        return ResponseMessage.ok(serviceAllDTO);
    }

    /**
     * 订阅接口
     * @param serviceId
     * @param isSubscribe
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "订阅接口", notes = "订阅接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", dataType = "Integer", value = "服务ID", required = true),
            @ApiImplicitParam(name = "isSubscribe", dataType = "Integer", value = "0.否 1.是", required = true)})
    @PostMapping("subscribe")
    public ResponseMessage subscribe(Integer serviceId, Integer isSubscribe) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        Object o = serviceService.saveFamilyService(serviceId, userId, isSubscribe);
        return ResponseMessage.ok();
    }
}
