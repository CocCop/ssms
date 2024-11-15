package abc.red1.controller;

import abc.red1.entity.Buy;
import abc.red1.entity.R;
import abc.red1.entity.Sell;
import abc.red1.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName WelcomeController
 * @Author YiXia
 * @Date 2024/1/28 21:47
 * @Version 1.0
 * @Description TODO
 **/
@Slf4j
@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SellService sellService;
    @Autowired
    private BuyService buyService;


    @PostMapping("/initDate1")
    public R<HashMap> initDate1() {
        HashMap<String, Long> map = new HashMap<>();
        //数据统计数据
        map.put("managerNumber", managerService.count());
        map.put("supplierNumber", supplierService.count());
        map.put("goodsNumber", goodsService.count());
        map.put("customerNumber", customerService.count());
        map.put("warehouseNumber", warehouseService.count());
        map.put("buyNumber", buyService.count());
        map.put("sellNumber", sellService.count());
        return R.success(map);
    }


    @PostMapping("/initDate2")
    public R<HashMap> initDate2() {
        HashMap<String, int[]> map = new HashMap<>();
        //折线图数据,获取一周内数据
        List<Buy> buyList = buyService.list();
        List<Sell> sellList = sellService.list();
        //处理数据
        int[] buyArray = new int[7];
        int[] sellArray = new int[7];
        int[] buyArray1 = new int[7];
        int[] sellArray1 = new int[7];
        long sqlSecond = 0;
        long nowSecond = new Date().getTime();
        long day = 0;
        for (Buy b : buyList) {
            //获取毫秒值
            sqlSecond = b.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            day = (nowSecond - sqlSecond) / 86400000;
            if (day < 7) {
                buyArray[(int) day]++;
            }
        }
        for (Sell s : sellList) {
            //获取毫秒值
            sqlSecond = s.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            day = (nowSecond - sqlSecond) / 86400000;
            if (day < 7) {
                sellArray[(int) day]++;
            }
        }
        //堆叠面积图
        buyArray1[0]=buyArray[0];
        sellArray1[0]=sellArray[0];
        for(int i=1;i<7;++i){
            buyArray1[i]=buyArray[i]+buyArray1[i-1];
            sellArray1[i]=sellArray[i]+sellArray1[i-1];
        }

        map.put("buyArray",buyArray);
        map.put("sellArray",sellArray);
        map.put("buyArray1",buyArray1);
        map.put("sellArray1",sellArray1);

        return R.success(map);


    }



}
