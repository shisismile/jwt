package com.smile.controller;


import com.smile.service.SysCaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api(tags = "测试")
@RestController
@RequestMapping("test")
@AllArgsConstructor
public class TestController {

    @ApiOperation("回声测试")
    @GetMapping("/echo/{message}")
    @RequiresPermissions("sys:echo")
    public String test(@ApiParam(name = "message",value = "回复信息") @PathVariable String message){
        return message;
    }


}
