package com.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.HealthException;
import com.health.dao.CheckItemDao;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.CheckItem;
import com.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: health_parent
 * @description: 检查项管理业务层实现类
 * @author: hw
 * @create: 2020-09-26 19:39
 **/
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //查询检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //新增检查项
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //检查项分页查询
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //Mapper接口方式的调用
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //模糊查询、判断是否有查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    //删除检查项
    @Override
    public void deleteById(int id) throws HealthException {
        //查询检查项是否被检查组使用了
        int cnt = checkItemDao.findCountByCheckItemId(id);

        //判断是否被使用
        if (cnt > 0) {
            //数据库存在数据证明被使用了、不能删除
            throw new HealthException("被检查组使用、不能删除");
        }
        //不存在、直接删除
        checkItemDao.deleteById(id);
    }

    //通过id查询检查项
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    //编辑检查项
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
