package abc.red1.handler;

import abc.red1.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public R exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return R.error(ex.getMessage());
    }



    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public R exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        //获得异常信息
        String message = ex.getMessage();
        //包含重复的  键值对  关键字
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            //已存在
            String msg = username + "已存在";
            return R.error(msg);
        }else{
            //未知错误
            return R.error("未知错误");
        }
    }
}