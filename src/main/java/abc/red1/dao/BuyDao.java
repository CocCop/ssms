package abc.red1.dao;


import abc.red1.entity.Buy;
import abc.red1.entity.excel.ExcelBuy;
import abc.red1.entity.excel.ExcelSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName Purchase
 * @Author YiXia
 * @Date 2024/1/19 11:56
 * @Version 1.0
 * @Description TODO
 **/

@Mapper
public interface BuyDao extends BaseMapper<Buy> {


    @Select("select b.buy_id,m.manager_id,m.manager_name,b.buy_goods_id,g.goods_name,b.buy_warehouse_id,w.warehouse_name,b.buy_number ,b.create_time " +
            "from buy b ,goods g,manager m ,warehouse w    " +
            "where b.buy_master_id = m.manager_id and b.buy_goods_id = g.goods_id and b.buy_warehouse_id= w.warehouse_id")
    List<ExcelBuy> buyExcelForLevel0();

    @Select("select b.buy_id,m.manager_id,m.manager_name,b.buy_goods_id,g.goods_name,b.buy_warehouse_id,w.warehouse_name,b.buy_number ,b.create_time " +
            "from buy b ,goods g,manager m ,warehouse w    " +
            "where b.buy_master_id= #{id} and b.buy_master_id = m.manager_id and b.buy_goods_id = g.goods_id and b.buy_warehouse_id= w.warehouse_id ")
    List<ExcelBuy> buyExcelForLevel1(Long id);

}
