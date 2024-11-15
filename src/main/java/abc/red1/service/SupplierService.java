package abc.red1.service;

import abc.red1.entity.Supplier;
import abc.red1.entity.excel.ExcelSupplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SupplierService extends IService<Supplier> {


    List<ExcelSupplier> supplierExcelForManager();

    List<ExcelSupplier> supplierExcelForNormal(Long id);






}
