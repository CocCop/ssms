package abc.red1.controller;


import abc.red1.common.BaseContext;
import abc.red1.entity.D;
import abc.red1.entity.Manager;
import abc.red1.entity.R;
import abc.red1.entity.Warehouse;
import abc.red1.service.ManagerService;
import abc.red1.service.WarehouseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author YiXia
 */
@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {


    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ManagerService managerService;
    private int pageSize = 10;


    /**
     * 获取权限内的仓库的信息
     */
    @PostMapping("/getPage")
    public R<IPage> getAllWarehouseByPages(@RequestBody D d) {
        //超级管理员可以查询所有

        long id = BaseContext.getCurrentId();
        Manager m = managerService.getById(id);
        IPage<Warehouse> page = new Page<>(d.getNum1(), pageSize);
        if (m.getManagerLevel() == 0) {
            //超级管理员可以查看所有
            warehouseService.page(page);
        } else {
            //普通管理员只可以查看名下供应商的商品
            //获取名下供应商信息
            LambdaQueryWrapper<Warehouse> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Warehouse::getWarehouseManagerId, id);
            List<Warehouse> warehousesList=warehouseService.list(page, lqw);
            page.setRecords(warehousesList);
        }
        return R.success(page);
    }


    @PostMapping("/add")
    public R<String> add(@RequestBody Warehouse w) {
        boolean end = warehouseService.save(w);
        if (end) {
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }


    /**
     * 根据仓库id，修改状态
     */
    @PostMapping("/changeState")
    public R<String> changeState(@RequestBody D d) {
        Warehouse sqlS = warehouseService.getById(d.getId());
        sqlS.setWarehouseState((sqlS.getWarehouseState() + 1) % 2);
        boolean end = warehouseService.updateById(sqlS);

        log.info("打印 changeState 方法 Warehouse {}", sqlS.toString());
        if (end) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }


    @PostMapping("/deleteById")
    public R<String> deleteById(@RequestBody D d) {

        boolean end = warehouseService.removeById(d.getId());
        if (end) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }


    @PostMapping("/edit")
    public R<String> editById(@RequestBody Warehouse w) {
        boolean end = warehouseService.updateById(w);
        if (end) {
            log.info("打印 edit方法 Warehouse 对象数据   {}", w.toString());
            return R.success("编辑成功");
        } else {
            return R.error("编辑失败");
        }
    }


    @PostMapping("/getWarehouseById")
    public R<Warehouse> editById(@RequestBody D d) {
        return R.success(warehouseService.getById(d.getId()));
    }


    @PostMapping("/getAllWarehouseByLevel")
    public List<Warehouse> getAllWarehouseByLevel() {
        long id = BaseContext.getCurrentId();
        Manager m = managerService.getById(id);
        LambdaQueryWrapper<Warehouse> lqw = new LambdaQueryWrapper<>();
        if (m.getManagerLevel() == 0) {
            lqw.eq(Warehouse::getWarehouseState, 1);
        } else {
            lqw.eq(Warehouse::getWarehouseState, 1).eq(Warehouse::getWarehouseManagerId, id);
        }
        return warehouseService.list(lqw);
    }


}
