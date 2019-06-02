package com.smile.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

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
