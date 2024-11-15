package abc.red1.controller;

import abc.red1.common.BaseContext;
import abc.red1.entity.*;
import abc.red1.service.GoodsService;
import abc.red1.service.ManagerService;
import abc.red1.service.SellService;
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
 * @ClassName SellController
 * @Author YiXia
 * @Date 2024/1/24 16:56
 * @Version 1.0
 * @Description TODO
 **/
@Slf4j
@RestController
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private SellService sellService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WarehouseService warehouseService;
    private int pageSize = 10;


    @PostMapping("/add")
    public R<String> add(@RequestBody Sell s) {
        s.setSellMasterId(BaseContext.getCurrentId());
        boolean end = sellService.save(s);
        if (end) {
            log.info("打印 add 方法上传的 sell 对象数据   {}", s.toString());
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }


    @PostMapping("/getPageByLevel")
    public R<IPage> getAllSellByLevel(@RequestBody D d) {
        long id = BaseContext.getCurrentId();
        Manager m = managerService.getById(id);
        IPage<Sell> page = new Page<>(d.getNum1(), pageSize);
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
            List<Sell> sellList=sellService.page(page).getRecords();
            for(Sell s:sellList){
                s.setSellMasterName(managerMap.get(s.getSellMasterId()));
                s.setSellGoodsName(GoodsMap.get(s.getSellGoodsId()));
                s.setSellWarehouseName(warehouseMap.get(s.getSellWarehouseId()));
            }
            page.setRecords(sellList);
            return R.success(page);
        } else {
            LambdaQueryWrapper<Sell> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Sell::getSellMasterId, id);
            List<Sell> sellList=sellService.page(page, lqw).getRecords();
            for(Sell s:sellList){
                s.setSellMasterName(managerMap.get(s.getSellMasterId()));
                s.setSellGoodsName(GoodsMap.get(s.getSellGoodsId()));
                s.setSellWarehouseName(warehouseMap.get(s.getSellWarehouseId()));
            }
            page.setRecords(sellList);
            return R.success(page);
        }
    }

}
