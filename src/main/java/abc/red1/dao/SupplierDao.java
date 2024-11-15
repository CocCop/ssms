package abc.red1.dao;


import abc.red1.entity.Supplier;
import abc.red1.entity.excel.ExcelSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SupplierDao extends BaseMapper<Supplier> {

    @Select("select s.* , m.manager_name from manager m,supplier s  where m.manager_id = s.belong_manager_id")
    List<ExcelSupplier> supplierExcelForManager();


    @Select("select s.* , m.manager_name from manager m,supplier s  where m.manager_id = #{l}  and #{l} = s.belong_manager_id")
    List<ExcelSupplier> supplierExcelForNormal(Long l);

}
