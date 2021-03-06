package com.js.xd.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.js.xd.model.User;
import com.js.xd.rocketmq.ProducerTest;
import com.js.xd.service.WXDataService;
import com.js.xd.service.XDPushDataService;
import com.js.xd.service.XDUserService;
import com.js.xd.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
@Api(value = "swagger 测试接口", description = "swagger 测试接口")
public class UserController {

    @Autowired
    private XDUserService userService;
    @Autowired
    private WXDataService wxDataService;
    @Autowired
    private XDPushDataService xdPushDataService;
    @Autowired
    private ProducerTest ProducerTest;
    @Value("${a.1.a}")
    private List<String> testProperties;

    @GetMapping("bbb")
    @ResponseBody
    @ApiOperation("数据库测试接口")
    public Object getTest() {
        //构建查询条件，使用wrapper构建条件  mybatis plus自带，wrapper的各种方法都有中文解释
        Wrapper<User> userWrapper = new EntityWrapper<>();
        userWrapper.eq("userName", 1);
        //使用继承了mybatis plus提供的BaseMapper  就可以使用父类中的方法进行crud
        List<User> user = userService.selectList(userWrapper);

        return user;
    }


    @PostMapping("getWXopenid")
    @ApiOperation("调用微信接口获取openid")
    public Object getWXopenid(@RequestBody Map<String, String> params) {
        try {
            String result = wxDataService.getWXOpenId(params.get("code"));
            return ResultUtil.success(1, result, "");
        } catch (Exception e) {
            return ResultUtil.fail(0, "获取openid失败，请重试！");
        }
    }

    @PostMapping("adminLogin")
    @ApiOperation("管理员登录接口")
    public Object login(@RequestBody User user) throws Exception {

        Map<String,String> rows = userService.adminLogin(user);
        if(rows.isEmpty()){
            return ResultUtil.fail("用户名或密码错误！");
        }
        return ResultUtil.success(1,rows,"");
    }

    @PostMapping("rocketMqTest")
    @ApiOperation("rocketTestInterface")
    public Object recketTest() throws Exception {

         ProducerTest.sendProducerTransaction();
         return "1";
    }

    @PostMapping("propertiesTest")
    @ApiOperation("properties")
    public Object propertiesTest() throws Exception {
        return testProperties.toString();
    }
}

