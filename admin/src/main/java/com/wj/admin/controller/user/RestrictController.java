package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.entity.user.SysRestrict;
import com.wj.core.entity.user.dto.RestrictDTO;
import com.wj.core.service.user.RestrictService;
import com.wj.core.util.mapper.BeanMapper;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/restrict", tags = "限行接口模块")
@RestController
@RequestMapping("/v1/restrict/")
public class RestrictController {

    private final static Logger logger = LoggerFactory.getLogger(RestrictController.class);

    @Autowired
    private RestrictService restrictService;

    @ApiOperation(value = "新增/修改限行", notes = "新增/修改限行")
    @PostMapping("addRestrict")
    public ResponseMessage addRestrict(@RequestBody RestrictDTO restrictDTO) {
        logger.info("新增/修改限行接口:/v1/restrict/addRestrict");
        SysRestrict restrict = BeanMapper.map(restrictDTO, SysRestrict.class);
        restrictService.saveRestrict(restrict);
        return ResponseMessage.ok();
    }

}
