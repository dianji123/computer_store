package com.hzh.computer_store.controller;

import com.hzh.computer_store.controller.ex.*;
import com.hzh.computer_store.utils.JsonResult;
import com.hzh.computer_store.entity.User;
import com.hzh.computer_store.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController //作用等同于@Controller+@ResponseBody
@RequestMapping("users") //请求路径的映射 相对项目路径
public class UserController extends BaseController{

    public static final int AVATAR_MAX_SIZE = 10*1024*1024;
    private static final List<String> AVATAR_TYPE = new ArrayList<>(Arrays.asList("jpg", "jpeg", "png", "bmp"));

    @Autowired
    private IUserService userService;

    //注册请求
    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
        //调用userService的reg方法时可能出现异常,由父类@ExceptionHandler(ServiceException.class)捕获
        userService.reg(user);
        return new JsonResult<>(OK, "用户注册成功");
    }

    //登录请求
    @RequestMapping("login")
    //@ResponseBody
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);

        //向session对象中完成数据的绑定(这个session是全局的,项目的任何位置都可以访问)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(OK, "登录成功", data);
    }

    //修改密码请求
    @RequestMapping("change_password")
    public JsonResult<User> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK, "密码修改成功");
    }

    //查询用户数据请求
    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    //修改个人资料请求
    @RequestMapping("change_info")
    public JsonResult<User> changeInfo(User user, HttpSession session) {
        //控制层给业务层传递uid
        Integer uid = getUidFromSession(session);
        userService.changeInfo(uid, user);
        return new JsonResult<>(OK, "修改成功");
    }

    //上传个人头像
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file) {

        /**
         * 1.接受文件的参数的参数名必须与前端传递文件的控件的name属性的值一致
         * 如：<input type="file" name="file">
         * 不一致时使用@RequestParam("file") MultipartFile f
         * 2.springmvc接口 MultipartFile 类型是MultipartFile并且参数名和前端上传文件的name值
         * 相同,则会自动把整体的数据包传递给file
         */
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }
        //判断文件类型
        String contentType = file.getContentType();
        //如果集合包含某个元素则返回值为true
        if (contentType == null || !AVATAR_TYPE.contains(contentType.split("/")[1])) {
            throw new FileTypeException("文件类型不支持");
        }

        //上传文件的路径 .../upload/文件名
        /**
         * session.getServletContext()获取当前Web应用程序的上下文对象(每次启动tomcat都会创建一个新的上下文对象)
         * getRealPath("/upload")的/代表当前web应用程序的根目录,通过该相对路径获取绝对路径,返回一个路径字符串,
         * 如果不能进行映射返回null,单斜杠可要可不要
         */
        //服务器临时路径
        String parent = session.getServletContext().getRealPath("/upload");
        System.out.println(parent);

        //File对象指向这个路径,通过判断File是否存在判断该路径是否存在
        File dir = new File(parent);
        if (!dir.exists()) { //检测目录是否存在
            //创建当前目录
            dir.mkdirs();
        }

        //获取文件名称(包括后缀)
        //用UUID工具生成一个新的字符串作为文件名,避免因为文件名重复发生文件覆盖
        String originalFilename = file.getOriginalFilename();
        //System.out.println("originalFilename="+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String fileName = UUID.randomUUID().toString().toUpperCase()+suffix;
        //System.out.println("UUID:" + fileName);

        //在dir目录下创建fileName文件(此时为空文件)
        File dest = new File(dir, fileName);

        //把参数file中的数据写入到空文件dest中
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            //先捕获FileStateException异常,因为它包含在IOException
            throw new FileStateException("文件异常状态");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + fileName;
        userService.changeAvatar(uid, avatar, username);

        //返回用户头像路径给前端,展示头像
        return new JsonResult<>(OK, "修改成功", avatar);
    }


}
