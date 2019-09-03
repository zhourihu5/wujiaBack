package com.wj.api.controller.activity;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.entity.experience.dto.ExperienceMessageDTO;
import com.wj.core.service.experience.ExperienceCodeService;
import com.wj.core.service.experience.ExperienceService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "/v1/experienceActivity", tags = "体验券模块")
@RestController
@RequestMapping("/v1/experienceActivity/")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ExperienceCodeService experienceCodeService;

    @ApiOperation(value = "体验券列表")
    @GetMapping("list")
    public ResponseMessage<Page<Experience>> list(Integer community, Integer pageNum, Integer pageSize) {
        return ResponseMessage.ok(experienceService.getExperienceListByCommunity(community, pageNum, pageSize));
    }

    @ApiOperation(value = "体验券详情")
    @GetMapping("detail")
    public ResponseMessage<Experience> detail(Integer id) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(experienceService.getExperienceById(userId, id));
    }

    @ApiOperation(value = "领取体验券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "experience", dataType = "Experience", value = "体验券code实体")
    })
    @PostMapping("receive")
    public ResponseMessage<ExperienceMessageDTO> receive(@RequestBody Experience experience) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        String userName = (String) claims.get("userName");
        return ResponseMessage.ok(experienceService.receiveExperience(userId, userName, experience.getId()));
    }

    @ApiOperation(value = "我的体验券列表")
    @GetMapping("experienceCodeList")
    public ResponseMessage<Page<ExperienceCode>> experienceCodeList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        return ResponseMessage.ok(experienceCodeService.findByUserIdStart(userId, pageable));
    }

    @ApiOperation(value = "我的体验券失效列表")
    @GetMapping("experienceCodeInvalidList")
    public ResponseMessage<Page<ExperienceCode>> experienceCodeInvalidList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        return ResponseMessage.ok(experienceCodeService.findByUserIdEnd(userId, pageable));
    }



}
