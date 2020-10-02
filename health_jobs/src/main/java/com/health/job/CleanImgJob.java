package com.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.service.SetmealService;
import com.health.utils.QiNiuUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cleanImgJob")
public class CleanImgJob {

    @Reference
    private SetmealService setmealService;

    /**
     * 任务执行的方法
     */
    public void cleanImg(){
        //1. 获取7牛上的所有图片
        List<String> img7Niu = QiNiuUtils.listFile();
        //2. 获取数据库中套餐的所有图片
        List<String> imgInDb = setmealService.findImgs();
        //3. 7牛的减去数据库
        img7Niu.removeAll(imgInDb);
        //4. 删除
        String[] need2Delete = img7Niu.toArray(new String[]{});
        QiNiuUtils.removeFiles(need2Delete);
    }
}
