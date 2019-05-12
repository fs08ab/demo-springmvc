package me.fs.demo.service.impl;

import me.fs.demo.bean.bo.DemoBO;
import me.fs.demo.bean.po.DemoPO;
import me.fs.demo.bean.vo.DemoVO;
import me.fs.demo.mapper.DemoMapper;
import me.fs.demo.service.DemoService;
import me.fs.framework.exception.concrete.DataException;
import me.fs.framework.exception.concrete.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author FS
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DemoVO> addThenDisplay(DemoBO demoBO) {
        if (demoBO == null) {
            throw new ParamException();
        }

        DemoPO demoPO = new DemoPO();
        BeanUtils.copyProperties(demoBO, demoPO);
        int i = demoMapper.upsertDemo(demoPO);
        if (i <= 0) {
            throw new DataException();
        }
        List<DemoVO> demoVOs = demoMapper.selectDemo(demoBO);
        if (CollectionUtils.isEmpty(demoVOs)) {
            throw new DataException();
        }
        return demoVOs;
    }
}
