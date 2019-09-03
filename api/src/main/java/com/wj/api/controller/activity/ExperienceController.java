package com.wj.api.controller.activity;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.experience.Experience;
import com.wj.core.service.experience.ExperienceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/experienceActivity", tags = "体验券模块")
@RestController
@RequestMapping("/v1/experienceActivity/")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @ApiOperation(value = "体验券列表")
    @GetMapping("list")
    public ResponseMessage<Page<Experience>> list(Integer community, Integer pageNum, Integer pageSize) {
        return ResponseMessage.ok(experienceService.getExperienceListByCommunity(pageNum, pageSize, community));
    }

    @ApiOperation(value = "体验券详情")
    @GetMapping("detail")
    public ResponseMessage<Experience> detail(Integer id) {
        return ResponseMessage.ok(experienceService.getExperienceById(id));
    }

    @ApiOperation(value = "领取体验券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "experience", dataType = "Experience", value = "领取体验券")
    })
    @PostMapping("receive")
    public ResponseMessage receive(@RequestBody Experience experience) {
        experienceService.updateExperienceIsShow(experience);
        return ResponseMessage.ok();
    }
}
