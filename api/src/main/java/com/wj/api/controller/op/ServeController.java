package com.wj.api.controller.op;

import com.wj.api.utils.JwtUtil;
import com.wj.api.utils.ResultUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "/v1/api/service", tags = "服务接口模块")
@RestController
@RequestMapping("/v1/api/service/")
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
        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", list);
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
        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", opService);
    }

//    /**
//     * 发现接口
//     * @param type
//     * @return FindServiceDTO
//     * @author thz
//     */
//    @ApiOperation(value = "发现接口", notes = "发现接口")
//    @GetMapping("find")
//    public Object find(Integer type) {
//        FindServiceDTO list = serviceService.findList(type);
//        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", list);
//    }
//
//    /**
//     * 政务服务接口
//     * @param type
//     * @return FindServiceDTO
//     * @author thz
//     */
//    @ApiOperation(value = "政务服务接口", notes = "政务服务接口")
//    @GetMapping("government")
//    public Object government(Integer type) {
//        GovernmentServiceDTO list = serviceService.governmentList(type);
//        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", list);
//    }

    /**
     * 全部服务接口
     * @param type
     * @return List<ServiceDTO>
     * @author thz
     */
    @ApiOperation(value = "全部服务接口", notes = "全部服务接口")
    @GetMapping("all")
    public Object all(Integer type) {
//        Map<String, List<OpService>> mapList = serviceService.allList(type);
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<ServiceDTO> list = serviceService.allList(type, userId);
        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", list);
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
        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", o);
    }
}
