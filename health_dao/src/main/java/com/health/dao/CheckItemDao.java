package com.health.dao;

import com.github.pagehelper.Page;
import com.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    //查询检查项
    List<CheckItem> findAll();

    //新增检查下
    void add(CheckItem checkItem);

    //检查项分页查询
    Page<CheckItem> findByCondition(String queryString);

    //查询检查项是否被检查组使用
    int findCountByCheckItemId(int id);

    //删除检查项
    void deleteById(int id);

    //通过id查询检查项
    CheckItem findById(int id);

    //编辑检查项
    void update(CheckItem checkItem);
}
