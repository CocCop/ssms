package abc.red1.controller;


import abc.red1.common.BaseContext;
import abc.red1.entity.D;
import abc.red1.entity.Manager;
import abc.red1.entity.R;
import abc.red1.entity.Supplier;
import abc.red1.service.ManagerService;
import abc.red1.service.SupplierService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.shadow.org.terracotta.offheapstore.HashingMap;
import org.ehcache.shadow.org.terracotta.offheapstore.OffHeapHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * @author YiXia
 */
@Slf4j
@RestController
@RequestMapping("/supplier")
public class SupplierController {


    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ManagerService managerService;

    private int pageSize = 10;

    /**
     * 分页展示数据
     * 默认展示权限以内的数据
     */
    @PostMapping("/getPage")
    public R<IPage> getPage(@RequestBody D d) {
        long id = BaseContext.getCurrentId();
        //根据id查找对应管理员信息
        Manager m = managerService.getById(id);
        //构建分页page对象
        IPage<Supplier> page = new Page<>(d.getNum1(), pageSize);
        //查找所有管理员信息。构建id-name的map
        HashMap<Long,String> managerMap=new HashMap<>();
        List<Manager> managerList=managerService.list();
        for(Manager mm:managerList){
            managerMap.put(mm.getManagerId(),mm.getManagerName());
        }

        //判断管理员的身份
        if (m.getManagerLevel() == 0) {
            //超级管理员查询所有数据
            List<Supplier> supplierList=supplierService.page(page).getRecords();
            for(Supplier s:supplierList){
                s.setBelongManagerName(managerMap.get(s.getBelongManagerId()));
            }
            page.setRecords(supplierList);
            return R.success(page);
        } else {
            //普通管理员查询名下数据
            LambdaQueryWrapper<Supplier> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Supplier::getBelongManagerId, id);
//            return R.success(supplierService.page(page, lqw));
            List<Supplier> supplierList=supplierService.page(page,lqw).getRecords();
            for(Supplier s:supplierList){
                s.setBelongManagerName(managerMap.get(s.getBelongManagerId()));
            }
            page.setRecords(supplierList);
            return R.success(page);
        }

    }


    /**
     * 根据Id获取指定管理员的信息
     */
    @PostMapping("/getSupplierById")
    public R<Supplier> getSupplierById(@RequestBody D d) {
        Supplier s = supplierService.getById(d.getId());
        log.info("打印 getManagerById 方法上传的 Supplier 对象数据   {}", s.toString());
        return R.success(s);

    }


    /**
     * 新增，
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Supplier s) {
        long belongManagerId = BaseContext.getCurrentId();


        //判断上传者身份，超级管理员可能会分配供应商给普通管理员。
        Manager m = managerService.getById(belongManagerId);
        boolean end = false;
        if (m.getManagerLevel() == 0 && s.getBelongManagerId() != null) {
            //是超级管理员并且分配了普通管理员
            //直接新增
            end = supplierService.save(s);
        } else {
            //没有分配管理员，默认是账号自己的
            s.setBelongManagerId(belongManagerId);
            end = supplierService.save(s);
        }

        if (end) {
            log.info("打印 add 方法上传的 Supplier 对象数据   {}", s.toString());
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }


    @PostMapping("/changeState")
    public R<String> changeStatus(@RequestBody D d) {
        Supplier s = supplierService.getById(d.getId());
        s.setSupplierState((s.getSupplierState() + 1) % 2);
        boolean end = supplierService.updateById(s);
        log.info("打印 changeState 方法上传的 Manager 对象数据   {}", s.toString());
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
        //前端做了权限控制了，后端直接删除就好
        boolean b = supplierService.removeById(d.getId());
        if (b) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }


    /**
     * 根据id修改数据
     */
    @PostMapping("/editById")
    public R<String> editById(@RequestBody Supplier s) {
        boolean end = supplierService.updateById(s);
        if (end) {
            log.info("editById 方法上传的 Supplier 对象数据   {}", s.toString());
            return R.success("编辑成功");
        } else {
            return R.error("编辑失败");
        }
    }

    /**
     * 获取管理员名下所有供应商的信息
     */
    @PostMapping("/getAllSuppliersByLevel")
    public R<List<Supplier>> getAllSuppliersByLevel() {
        //获取管理员id
        long mId = BaseContext.getCurrentId();
        //判断上传者身份，超级管理员可能会分配供应商给普通管理员。
        Manager m = managerService.getById(mId);
        //判断管理员的身份
        if (m.getManagerLevel() == 0) {
            //超级管理员查询所有数据
            return R.success(supplierService.list());
        } else {
            //普通管理员查询名下数据
            LambdaQueryWrapper<Supplier> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Supplier::getBelongManagerId, mId);
            return R.success(supplierService.list(lqw));
        }
    }

    @PostMapping("/getAllSuppliersByLevelAndState")
    public R<List<Supplier>> getAllSuppliersByLevelAndState() {
        //获取管理员id
        long mId = BaseContext.getCurrentId();
        //判断上传者身份，超级管理员可能会分配供应商给普通管理员。
        Manager m = managerService.getById(mId);
        LambdaQueryWrapper<Supplier> lqw = new LambdaQueryWrapper<>();
        //判断管理员的身份
        if (m.getManagerLevel() == 0) {
            //超级管理员查询所有数据
            lqw.eq(Supplier::getSupplierState,1);
            return R.success(supplierService.list());
        } else {
            //普通管理员查询名下数据
            lqw.eq(Supplier::getBelongManagerId, mId).eq(Supplier::getSupplierState,1);
            return R.success(supplierService.list(lqw));
        }
    }


//    @PostMapping("/getSupplierExcel")
//    public String getSupplierExcel(HttpServletResponse response) throws IOException {
//        //1、创建一个文件对象
//        File excelFile = new File("D:red1/Excel/1.xlsx");
//        //2、判断文件是否存在，不存在则创建一个Excel文件
//        if (!excelFile.exists()) {
//            try {
//                excelFile.createNewFile();//创建一个新的文件
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //3、指定需要那个class去写。然后写到第一个sheet，名字为模版，然后文件流会自动关闭
//        //处理数据
//        List<ExcelSupplier> list = supplierService.supplierExcel();
//        log.info("打印excel数据  ={}",list);
////        EasyExcel.write(excelFile, ExcelSupplier.class).sheet("供应商信息").doWrite(list);
//        EasyExcel.write(response.getOutputStream(),ExcelSupplier.class).sheet("模板").doWrite(list);
//        return "success";
//    }

//    @GetMapping("/userExcel")
//    public void downloadUserExcel(HttpServletResponse response) throws IOException {
//        List<User> users = userService.list();
//        log.info("开始写excel");
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
//        EasyExcel.write(response.getOutputStream(),User.class).sheet("模板").doWrite(users);
//    }
}
