package com.wj.admin.controller.op;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.dto.OpServiceDTO;
import com.wj.core.service.op.BannerService;
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

@Api(value = "/v1/banner", tags = "轮播图接口模块")
@RestController
@RequestMapping("/v1/banner/")
public class BannerController {

    private final static Logger logger = LoggerFactory.getLogger(BannerController.class);

    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "添加/更新轮播图", notes = "添加/更新轮播图")
    @PostMapping("saveBanner")
    public ResponseMessage saveBanner(@RequestBody OpBanner banner) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("添加/更新轮播图 接口:/v1/banner/saveBanner userId=" + claims.get("userId"));
        bannerService.saveBanner(banner);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "轮播图分页列表", notes = "轮播图分页列表")
    @GetMapping("findAll")
    public ResponseMessage<Page<OpBanner>> findAll(Integer type, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("轮播图分页列表 接口:/v1/banner/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<OpBanner> page = bannerService.findAll(type, pageable);
        return ResponseMessage.ok(page);
    }
}
