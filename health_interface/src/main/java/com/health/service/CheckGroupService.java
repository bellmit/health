package com.health.service;

import com.health.HealthException;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    //新增检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //分页查询检查组
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    //通过id查询
    CheckGroup findById(int id);

    //通过检查组id查询选择的检查项id
    List<Integer> findCheckItemIdsByCheckGroup(int id);

    //提交修改
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    //删除检查组
    void deleteById(int id) throws HealthException;

    //查询所有
    List<CheckGroup> findAll();
}
