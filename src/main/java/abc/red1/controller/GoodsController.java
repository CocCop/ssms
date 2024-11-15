package abc.red1.controller;


import abc.red1.common.BaseContext;
import abc.red1.entity.*;
import abc.red1.service.GoodsService;
import abc.red1.service.ManagerService;
import abc.red1.service.SupplierService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author YiXia
 */
@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private SupplierService supplierService;
    private int pageSize = 10;

    /**
     * 获取权限内商品的信息
     */
    @PostMapping("/getPage")
    public R<IPage> getAllGoodsByPages(@RequestBody D d) {
        //超级管理员可以查询所有

        long id = BaseContext.getCurrentId();
        Manager m = managerService.getById(id);
        IPage<Goods> page = new Page<>(d.getNum1(), pageSize);
        //查找所有供应商信息。构建id-name的map
        HashMap<Long,String> supplierMap=new HashMap<>();
        List<Supplier> supplierList1=supplierService.list();
        for(Supplier s:supplierList1){
            supplierMap.put(s.getSupplierId(),s.getSupplierName());
        }
        if (m.getManagerLevel() == 0) {
            //超级管理员查询所有数据
            List<Goods> goodsList=goodsService.page(page).getRecords();
            for(Goods g:goodsList){
                g.setGoodsSupplierName(supplierMap.get(g.getGoodsSupplierId()));
            }
            page.setRecords(goodsList);
            return R.success(page);
        } else {
            //普通管理员只可以查看名下供应商的商品
            //获取名下供应商信息
            LambdaQueryWrapper<Supplier> lqwSupplier = new LambdaQueryWrapper<>();
            lqwSupplier.eq(Supplier::getBelongManagerId, id);
            List<Supplier> supplierList = supplierService.list(lqwSupplier);


            //根据供应商信息查询对应的商品信息
            LambdaQueryWrapper<Goods> lqwGoods = new LambdaQueryWrapper<>();
            //处理上面查询到的 supplierList 数据
            List<Long> idList = new ArrayList<>();
            for (Supplier s : supplierList) {
                idList.add(s.getSupplierId());
            }

            lqwGoods.in(Goods::getGoodsSupplierId, idList);
            List<Goods> goodsList = goodsService.list(page, lqwGoods);

            for(Goods g:goodsList){
                g.setGoodsSupplierName(supplierMap.get(g.getGoodsSupplierId()));
            }
            page.setRecords(goodsList);

            return R.success(page);
        }
    }



    /*
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
    */

    @PostMapping("/add")
    public R<String> add(@RequestBody Goods g) {
        boolean end = goodsService.save(g);
        if (end) {
            log.info("打印 add 方法上传的 Goods 对象数据   {}", g.toString());
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }


    @PostMapping("/deleteById")
    public R<String> deleteById(@RequestBody D d) {

        boolean end = goodsService.removeById(d.getId());
        if (end) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }


    @PostMapping("/editById")
    public R<String> editById(@RequestBody Goods g) {
        boolean end = goodsService.updateById(g);
        if (end) {
            log.info("打印 updateById 方法上传的 Goods 对象数据   {}", g.toString());
            return R.success("编辑成功");
        } else {
            return R.error("编辑失败");
        }
    }


    @PostMapping("/getGoodsById")
    public R<Goods> getGoodsById(@RequestBody D d) {

        Goods sqlS = goodsService.getById(d.getId());

        //数据肯定存在，直接返回信息
        return R.success(sqlS);
    }


}
