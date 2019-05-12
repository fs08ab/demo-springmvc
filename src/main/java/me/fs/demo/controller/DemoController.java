package me.fs.demo.controller;

import me.fs.demo.bean.bo.DemoBO;
import me.fs.demo.bean.vo.DemoVO;
import me.fs.demo.service.DemoService;
import me.fs.framework.bean.ResultModel;
import me.fs.framework.exception.concrete.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * demo相关的接口
 *
 * @author FS
 */
@Controller
public class DemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @RequestMapping("/demo")
    @ResponseBody
    public String requestDemo() {
        LOGGER.info("spring mvc log testing");
        return "spring mvc demo";
    }

    @RequestMapping("/demo_db")
    @ResponseBody
    public ResultModel requestDbDemo(@RequestBody DemoVO demoVO) {
        if (demoVO == null) {
            throw new ParamException();
        }

        DemoBO demoBO = new DemoBO();
        BeanUtils.copyProperties(demoVO, demoBO);
        List<DemoVO> demoVOS = demoService.addThenDisplay(demoBO);
        return new ResultModel(demoVOS);
    }
}
