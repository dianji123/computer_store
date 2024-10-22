package com.hzh.computer_store.controller;

import com.hzh.computer_store.controller.ex.*;
import com.hzh.computer_store.utils.JsonResult;
import com.hzh.computer_store.service.ex.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {

    //操作成功的状态码
    public static final int OK = 200;

    /**
     * 1.@ExceptionHandler表示该方法用于处理捕获抛出的异常
     * 抛出ServiceException异常就会被拦截到handleException方法
     * 此时handleException方法就是请求处理方法,返回值就是需要传递给前端的数据
     * 2.被ExceptionHandler修饰后如果项目发生异常,那么异常对象就会被自动传递给此方法的参数列表上,
     * 所以形参就需要写Throwable e用来接收异常对象
     * 3.参数可传数组@ExceptionHandler({})
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        } else if (e instanceof UsernameNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户密码错误的异常");
        }
        return result;
    }

    /**
     * 获取session对象中的uid,username
     * @param session session对象
     * @return 当前登录的用户uid,username的值
     */
    public final Integer getUidFromSession(HttpSession session) {
        //getAttribute返回的是Object对象,需要转换为字符串再转换为包装类
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    public final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

}
