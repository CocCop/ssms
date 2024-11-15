package abc.red1.controller;

import abc.red1.entity.*;
import abc.red1.service.BuyService;
import abc.red1.service.GoodsService;
import abc.red1.service.SellService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * @ClassName WarehouseDetail
 * @Author YiXia
 * @Date 2024/1/24 11:31
 * @Version 1.0
 * @Description TODO
 **/

@Slf4j
@RestController
@RequestMapping("/warehouseDetail")
public class WarehouseDetailController {


    @Autowired
    private BuyService buyService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellService sellService;

    /**
     * 根据id查询仓库内所有商品
     */
    @PostMapping("/getAll")
    public R<List<WarehouseDetail>> getAllWarehouseByPages(@RequestBody D d) {
        log.info("打印请求参数D {}",d);

        //构建仓库详情的hashMap对象  <商品id，数量>
        HashMap<Long, Long> buyMap = new HashMap<>();
        //保存所有购买的商品信息   <商品id，商品信息>
        HashMap<Long, Goods> goodsMap = new HashMap<>();


        //查询所有仓库购买记录有
        LambdaQueryWrapper<Buy> buyLQW = new LambdaQueryWrapper<>();
        buyLQW.eq(Buy::getBuyWarehouseId, d.getId());
        List<Buy> buyList = buyService.list(buyLQW);
        log.info("展示所有仓库购买记录，{}",buyList);


        //处理购买记录
        Long goodsId = null;
        for (Buy b : buyList) {
            goodsId = b.getBuyGoodsId();
            if (buyMap.containsKey(goodsId)) {
                buyMap.put(goodsId, buyMap.get(goodsId) + b.getBuyNumber());
            } else {
                buyMap.put(goodsId, b.getBuyNumber());
            }
        }

        //处理上面的所有商品购买记录,用于根据商品id查找对应的商品信息
        buyMap.forEach((k, v) -> {
            //根据k值（id）寻找商品具体信息
            goodsMap.put(k, goodsService.getById(k));
        });


        //查询所有仓库出库记录
        LambdaQueryWrapper<Sell> sellLQW =new LambdaQueryWrapper<>();
        sellLQW.eq(Sell::getSellWarehouseId,d.getId());
        List<Sell> sellList=sellService.list(sellLQW);


        //获得了所有的出售记录，开始处理
        for(Sell s:sellList){
            Long num=buyMap.get(s.getSellGoodsId());
            if(num>s.getSellNumber()){
                buyMap.put(s.getSellGoodsId(),num-s.getSellNumber());
            }else if(num.equals(s.getSellNumber())){
                buyMap.remove(s.getSellGoodsId());
            }else{
                return R.error("出库大于入库");
            }
        }


        //输出
        List<WarehouseDetail> endList = new ArrayList<>();
        //便利hashmap
        buyMap.forEach((k, v) -> {
            WarehouseDetail warehouseDetail = new WarehouseDetail();
            warehouseDetail.setGoodsId(k);
            Goods g = goodsMap.get(k);
            warehouseDetail.setGoodsName(g.getGoodsName());
            warehouseDetail.setGoodsPrice(g.getGoodsPrice());
            warehouseDetail.setGoodsNumber(v);
            endList.add(warehouseDetail);
        });
        log.info("最终展现数据：{}",endList);


        return R.success(endList);
    }


}


