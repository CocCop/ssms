package abc.red1.controller;

import abc.red1.common.BaseContext;
import abc.red1.entity.*;
import abc.red1.service.BuyService;
import abc.red1.service.GoodsService;
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

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName BuyController
 * @Author YiXia
 * @Date 2024/1/23 22:24
 * @Version 1.0
 * @Description TODO
 **/
@Slf4j
@RestController
@RequestMapping("/buy")
public class BuyController {

    @Autowired
    private BuyService buyService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WarehouseService warehouseService;
    private int pageSize = 10;


    @PostMapping("/add")
    public R<String> add(@RequestBody Buy b) {
        //获取管理员id
        long mId = BaseContext.getCurrentId();
        b.setBuyMasterId(mId);
        boolean judge = buyService.save(b);
        if (judge) {
            return R.success("success");
        } else {
            return R.error("false");
        }
    }


    @PostMapping("/getPageByLevel")
    public R<IPage> getAllWarehouseByLevel(@RequestBody D d) {
        long id = BaseContext.getCurrentId();
        Manager m = managerService.getById(id);
        IPage<Buy> page = new Page<>(d.getNum1(), pageSize);
        //构建id-name的map
        HashMap<Long, String> managerMap = new HashMap<>();
        List<Manager> managerList = managerService.list();
        for (Manager mm : managerList) {
            managerMap.put(mm.getManagerId(), mm.getManagerName());
        }
        HashMap<Long, String> GoodsMap = new HashMap<>();
        List<Goods> goodsList = goodsService.list();
        for (Goods gg : goodsList) {
            GoodsMap.put(gg.getGoodsId(), gg.getGoodsName());
        }
        HashMap<Long, String> warehouseMap = new HashMap<>();
        List<Warehouse> warehouseList = warehouseService.list();
        for (Warehouse ww : warehouseList) {
            warehouseMap.put(ww.getWarehouseId(), ww.getWarehouseName());
        }


        if (m.getManagerLevel() == 0) {
            //超级管理员查询所有数据
            List<Buy> buyList=buyService.page(page).getRecords();
            for(Buy b:buyList){
                b.setBuyMasterName(managerMap.get(b.getBuyMasterId()));
                b.setBuyGoodsName(GoodsMap.get(b.getBuyGoodsId()));
                b.setBuyWarehouseName(warehouseMap.get(b.getBuyWarehouseId()));
            }
            page.setRecords(buyList);
            return R.success(page);
        } else {
            LambdaQueryWrapper<Buy> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Buy::getBuyMasterId, id);
            List<Buy> buyList=buyService.page(page, lqw).getRecords();
            for(Buy b:buyList){
                b.setBuyMasterName(managerMap.get(b.getBuyMasterId()));
                b.setBuyGoodsName(GoodsMap.get(b.getBuyGoodsId()));
                b.setBuyWarehouseName(warehouseMap.get(b.getBuyWarehouseId()));
            }
            page.setRecords(buyList);
            return R.success(page);
        }
    }

}
