package me.fs.demo.service;

import me.fs.demo.bean.bo.DemoBO;
import me.fs.demo.bean.vo.DemoVO;

import java.util.List;

/**
 * demo相关的业务处理逻辑
 *
 * @author FS
 */
public interface DemoService {
    /**
     * 新增一条数据，并查询出当前已存在的所有类似数据
     *
     * @param demoBO 新增的数据
     * @return 新增后，已存在的所有类似数据
     */
    List<DemoVO> addThenDisplay(DemoBO demoBO);
}
