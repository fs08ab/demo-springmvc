package me.fs.demo.mapper;

import me.fs.demo.bean.bo.DemoBO;
import me.fs.demo.bean.po.DemoPO;
import me.fs.demo.bean.vo.DemoVO;

import java.util.List;

/**
 * 对t_demo表的操作：增删改查
 *
 * @author FS
 */
public interface DemoMapper {
    /**
     * 插入数据
     *
     * @param demoPO 插入的对象
     * @return 受影响数据条数
     */
    int insertDemo(DemoPO demoPO);

    /**
     * 增加或修改
     *
     * @param demoPO 插入或更新的对象
     * @return 受影响的数据条数
     */
    int upsertDemo(DemoPO demoPO);

    /**
     * 删除对应的数据项
     *
     * @param id 数据主键
     * @return 受影响的数据条数
     */
    int deleteDemo(int id);

    /**
     * 修改符合条件的数据
     *
     * @param demoBO 修改时的条件
     * @return 受影响的数据条数
     */
    int updateDemo(DemoBO demoBO);

    /**
     * 查询符合条件的数据集合
     *
     * @param demoBO 查询条件
     * @return 符合条件的数据集合
     */
    List<DemoVO> selectDemo(DemoBO demoBO);
}
