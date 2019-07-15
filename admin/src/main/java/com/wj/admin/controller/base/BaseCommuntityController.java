package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.dto.BaseCommuntityDTO;
import com.wj.core.entity.base.dto.BaseFamilyDTO;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.service.base.BaseCommuntityService;
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

import java.util.List;
import java.util.Map;

@Api(value = "/v1/communtity", tags = "社区接口模块")
@RestController
@RequestMapping("/v1/communtity/")
public class BaseCommuntityController {

    private final static Logger logger = LoggerFactory.getLogger(BaseCommuntityController.class);

    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @ApiOperation(value = "获取社区分页信息", notes = "获取社区分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseCommuntity>> findAll(Integer areaCode, String name, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取社区分页信息接口:/v1/communtity/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseCommuntity> page = baseCommuntityService.findAll(areaCode, name, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "根据ID查询社区信息", notes = "根据ID查询社区信息")
    @GetMapping("findById")
    public ResponseMessage<BaseCommuntity> findById(Integer id) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据ID查询社区信息接口:/v1/communtity/findById userId=" + claims.get("userId"));
        BaseCommuntity baseCommuntity = baseCommuntityService.findById(id);
        return ResponseMessage.ok(baseCommuntity);
    }

    @ApiOperation(value = "保存社区内容", notes = "保存社区内容")
    @PostMapping("addCommuntity")
    public ResponseMessage addCommuntity(@RequestBody BaseCommuntity communtity) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存社区内容接口:/v1/communtity/addCommuntity userId=" + claims.get("userId"));
        return ResponseMessage.ok(baseCommuntityService.saveCommuntity(communtity));
    }

    @ApiOperation(value = "查询区下所属社区", notes = "查询区下所属社区")
    @GetMapping("findByArea")
    public ResponseMessage<List<BaseCommuntity>> findByArea(Integer areaCode) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询区下所属社区接口:/v1/communtity/findByArea userId=" + claims.get("userId"));
        List<BaseCommuntity> list = baseCommuntityService.findByAreaCode(areaCode);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "根据社区查询所有用户", notes = "根据社区查询所有用户")
    @GetMapping("findUserListByCid")
    public ResponseMessage<List<SysUserInfoDTO>> findUserListByCid(Integer communtityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据社区查询所有用户 接口:/v1/communtity/findUserListByCid userId=" + claims.get("userId"));
        List<SysUserInfoDTO> list = baseCommuntityService.findUserListByCid(communtityId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "根据社区查询所有家庭", notes = "根据社区查询所有家庭")
    @GetMapping("findFamilyListByCode")
    public ResponseMessage<List<BaseFamilyDTO>> findFamilyListByCode(String communtityCode) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据社区查询所有家庭 接口:/v1/communtity/findFamilyListByCode userId=" + claims.get("userId"));
        List<BaseFamilyDTO> list = baseCommuntityService.findFamilyListByCode(communtityCode);
        return ResponseMessage.ok(list);
    }
}

