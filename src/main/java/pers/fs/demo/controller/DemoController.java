package pers.fs.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.fs.demo.bean.bo.DemoBO;
import pers.fs.demo.bean.vo.DemoVO;
import pers.fs.framework.bean.ResultModel;
import pers.fs.framework.exception.concrete.ParamException;
import pers.fs.demo.service.DemoService;

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
