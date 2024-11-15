package abc.red1.controller;

import abc.red1.common.BaseContext;
import abc.red1.entity.D;
import abc.red1.entity.Manager;
import abc.red1.entity.R;
import abc.red1.service.ManagerService;
import abc.red1.tools.StringTool;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {


    @Autowired
    private ManagerService service;

    private static int pageSize = 10;


    private static final StringTool TOOL = new StringTool();

    /**
     * 登录
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody Manager m) {
        log.info("login 方法参数 Manager  ：{}", m);

        //数据校验
        if (!TOOL.judgeString(m.getManagerName()) || !TOOL.judgeString(m.getManagerPassword())) {
            return R.error("数据不合法,请检查后重新输入");
        }

        //信息加密
        String md5Password = DigestUtils.md5DigestAsHex(m.getManagerPassword().getBytes());


        //查询sql
        LambdaQueryWrapper<Manager> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Manager::getManagerName, m.getManagerName());
        Manager sqlM = service.getOne(lqw);



        //找不到数据
        if (sqlM == null) {
            return R.error("用户名错误，请检查后重新输入");
        }
        log.info("查询到的数据库Manager对象 = {}", sqlM.toString());
        //System.out.println("加密密码为："+md5Password);
        //密码比对
        if (!sqlM.getManagerPassword().equals(md5Password)) {
            return R.error("密码错误，请检查后重新输入");
        }


        //判断状态
        if (sqlM.getManagerState() == 0) {
            return R.error("账号已禁用");
        }


        //可以登录了
        request.getSession().setAttribute("manager", sqlM.getManagerId());
        System.out.println("管理员登录，id =  " + sqlM.getManagerId());
        return R.success("success");
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        System.out.println("登出的id=  " + BaseContext.getCurrentId());
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 获取当前登录管理员的信息
     */
    @PostMapping("/getManager")
    public R<Manager> getManager() {
        long id = BaseContext.getCurrentId();
        Manager m = service.getById(id);
        return R.success(m);

    }


    /**
     * 根据Id获取指定管理员的信息
     */
    @PostMapping("/getManagerById")
    public R<Manager> getManagerById(@RequestBody D d) {
        log.info("打印 getManagerById 方法上传的 D 对象数据   {}", d.toString());
        Manager returnM = service.getById(d.getId());
        return R.success(returnM);

    }

    /**
     * 新增，默认是普通管理员
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Manager m) {
        //密码加密
        String md5Password = DigestUtils.md5DigestAsHex(m.getManagerPassword().getBytes());
        m.setManagerPassword(md5Password);
        m.setManagerLevel(1);
        boolean end = service.save(m);
        if (end) {
            log.info("打印 add 方法上传的 Manager 对象数据   {}", m.toString());
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }

    /**
     * 分页展示数据
     * 默认展示权限以内的数据
     */
    @PostMapping("/getPage")
    public R<IPage> getPage(@RequestBody D d) {
        long id = BaseContext.getCurrentId();
        //根据id查找对应管理员信息
        Manager m = service.getById(id);
        //构建分页page对象
        IPage<Manager> page = new Page<>(d.getNum1(), pageSize);
        //判断管理员权限
        if (m.getManagerLevel() == 0) {
            //超级管理员
            page = service.page(page);
            return R.success(service.page(page));
        } else {
            //普通管理员
            LambdaQueryWrapper<Manager> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Manager::getManagerId, id);
            return R.success(service.page(page, lqw));
        }

    }


    @PostMapping("/changeState")
    public R<String> changeStatus(@RequestBody D d) {
        Manager sqlM = service.getById(d.getId());
        sqlM.setManagerState((sqlM.getManagerState() + 1) % 2);
        //防手贱代码，保护超级管理员账号
        if (sqlM.getManagerLevel() == 0) {
            sqlM.setManagerState(0);
        }
        boolean end = service.updateById(sqlM);
        log.info("打印 changeState 方法上传的 Manager 对象数据   {}", sqlM.toString());
        if (end) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }


    /**
     * 根据id删除数据
     */
    @PostMapping("/deleteById")
    public R<String> deleteById(@RequestBody D d) {
        //根据id查找对应管理员信息
        Manager m = service.getById(d.getId());
        //判断目标是不是超级管理员
        if (m.getManagerLevel() == 0) {
            return R.error("删除失败,超级管理员不可以删除");
        } else {
            boolean end = service.removeById(m);
            if (end) {
                return R.success("删除成功");
            } else {
                return R.error("删除失败");
            }
        }

    }


    /**
     * 根据id修改数据
     */
    @PostMapping("/editById")
    public R<String> editById(@RequestBody Manager m) {
        //密码加密
        String md5Password = DigestUtils.md5DigestAsHex(m.getManagerPassword().getBytes());
        m.setManagerPassword(md5Password);
        boolean end = service.updateById(m);
        if (end) {
            log.info("editById 方法上传的 Manager 对象数据   {}", m.toString());
            return R.success("编辑成功");
        } else {
            return R.error("编辑失败");
        }
    }


    /**
     * 获得所有普通管理员的信息
     */
    @PostMapping("/getAllNormalManagers")
    public R<List<Manager>> getAllNormalManagers() {
        LambdaQueryWrapper<Manager> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Manager::getManagerLevel, 1);
        ;
        return R.success(service.list(lqw));
    }









}
