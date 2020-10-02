package com.health.service;

import com.health.HealthException;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    //查询检查项
    List<CheckItem> findAll();

    //新增检查项
    void add(CheckItem checkItem);

    //检查项分页查询
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    //删除检查项
    void deleteById(int id) throws HealthException;

    //通过id查询检查项
    CheckItem findById(int id);

    //编辑检查项
    void update(CheckItem checkItem);
}
