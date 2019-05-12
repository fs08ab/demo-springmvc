package me.fs.framework.exception;

import me.fs.framework.bean.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 对所有的controller抛出的异常进行捕获处理<br/>
 * 在spring 3.2中，新增了@ControllerAdvice 注解，可以用于定义@ExceptionHandler、@InitBinder、@ModelAttribute<br/>
 * 被@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法，会作用在被@RequestMapping注解的方法上<br/>
 * 分别增强controller：全局异常捕获处理；在controller方法执行前初始化数据绑定器WebDataBinder；把需要的值绑定到Model
 *
 * @author FS
 */
@ControllerAdvice
@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 全局异常捕捉处理
     *
     * @param e controller抛出的异常
     * @return 抛出异常时返回的数据
     */
    @ExceptionHandler(value = Exception.class)
    public ResultModel handleException(Exception e) {
        BaseException exception = BaseException.boxException(e);
        LOGGER.error(exception.getExMessage());
        return new ResultModel(exception);
    }
}
