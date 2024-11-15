package abc.red1.dao;

import abc.red1.entity.Sell;
import abc.red1.entity.excel.ExcelSell;
import abc.red1.entity.excel.ExcelSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName SellDao
 * @Author YiXia
 * @Date 2024/1/24 16:53
 * @Version 1.0
 * @Description TODO
 **/
@Mapper
public interface SellDao extends BaseMapper<Sell> {

    @Select("select s.sell_id,m.manager_id,m.manager_name,s.sell_goods_id, g.goods_name,s.sell_warehouse_id,s.sell_number,s.sell_customer_id,c.customer_name,s.create_time " +
            "from goods g,manager m ,warehouse w ,sell s ,customer c  " +
            "where s.sell_master_id=m.manager_id and s.sell_goods_id = g.goods_id and s.sell_warehouse_id = w.warehouse_id and c.customer_id=s.sell_customer_id")
    List<ExcelSell> sellExcelForLevel0();

    @Select("select s.sell_id,m.manager_id,m.manager_name,s.sell_goods_id, g.goods_name,s.sell_warehouse_id,s.sell_number,s.sell_customer_id,c.customer_name,s.create_time " +
            "from goods g,manager m ,warehouse w ,sell s ,customer c  " +
            "where s.sell_master_id=#{id} and  s.sell_master_id=m.manager_id and s.sell_goods_id = g.goods_id and s.sell_warehouse_id = w.warehouse_id and c.customer_id=s.sell_customer_id ")
    List<ExcelSell> sellExcelForLevel1(Long id);
}
