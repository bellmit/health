package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.CheckItem;
import com.health.service.CheckItemService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: health_parent
 * @description: 检查项管理
 * @author: hw
 * @create: 2020-09-26 19:31
 **/
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询检查项
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> list = checkItemService.findAll();
        //封装结果并返回
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }

    /**
     * 新增检查项
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        //调用业务层
        checkItemService.add(checkItem);
        //响应
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 检查项分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用业务层
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        //返回封装数据
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 删除检查项
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        //调用业务
        checkItemService.deleteById(id);

        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 通过id查询
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务通过id查询检查项信息
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 编辑检查项
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkitem){
        checkItemService.update(checkitem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
