package com.health.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.constant.RedisMessageConstant;
import com.health.entity.Result;
import com.health.pojo.Order;
import com.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @program: health_parent
 * @description:
 * @author: hw
 * @create: 2020-09-27 17:16
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约提交
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> orderInfo) {
        //验证前段传过的验证码与redis的验证码是否一致
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + orderInfo.get("telephone");
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(key);
        if (StringUtils.isEmpty(codeInRedis)) {
            return new Result(true, "请重新获取验证码");
        }
        if (!codeInRedis.equals(orderInfo.get("validateCode"))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //防止重复提交、移除验证码
        jedis.del(key);
        //设置预约类型health_mobile是给手机端公众号使用的
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        Order order = orderService.submit(orderInfo);
        return new Result(true, MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 订单详情
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String, String> orderInfo=orderService.findById(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
