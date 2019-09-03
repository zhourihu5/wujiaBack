package com.wj.admin.controller.experience;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.entity.experience.dto.ExperienceDTO;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.experience.ExperienceService;
import com.wj.core.util.mapper.BeanMapper;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/experience", tags = "体验券模块")
@RestController
@RequestMapping("/v1/experience/")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @ApiOperation(value="保存体验券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "experience", dataType = "Experience", value = "体验券对象")
    })
    @PostMapping("save")
    public ResponseMessage save(@RequestBody ExperienceDTO experienceDTO) {
        Experience experience = BeanMapper.map(experienceDTO, Experience.class);
        experienceService.saveExperience(experience);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="删除体验券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID")
    })
    @PostMapping("remove")
    public ResponseMessage remove(@RequestBody Experience experience) {
        experienceService.removeExperience(experience.getId());
        return ResponseMessage.ok();
    }


    @ApiOperation(value="体验券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", dataType = "String", value = "结束时间"),
            @ApiImplicitParam(name = "status", dataType = "String", value = "状态"),
            @ApiImplicitParam(name = "name", dataType = "String", value = "体验券名称")
    })
    @GetMapping("list")
    public ResponseMessage<Page<Experience>> list(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String name) {
        return ResponseMessage.ok(experienceService.getExperienceList(pageNum, pageSize, startDate, endDate, status, name));
    }


    @ApiOperation(value = "上架/下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "experience", dataType = "Experience", value = "体验券对象")
    })
    @PostMapping("updateIsShow")
    public ResponseMessage updateIsShow(@RequestBody Experience experience) {
        experienceService.updateExperienceIsShow(experience);
        return ResponseMessage.ok();
    }


    @ApiOperation(value="领取体验券用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
    })
    @GetMapping("receiveUserList")
    public ResponseMessage<Page<ExperienceCode>> receiveUserList(Integer experienceId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        return ResponseMessage.ok(experienceService.findByExperienceIdAndUserId(experienceId, pageable));
    }
}
