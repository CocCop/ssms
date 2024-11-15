package abc.red1.service.impl;

import abc.red1.dao.SupplierDao;
import abc.red1.entity.Supplier;
import abc.red1.entity.excel.ExcelSupplier;
import abc.red1.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserImpl
 * @Author YiXia
 * @Date 2024/1/12 10:51
 * @Version 1.0
 * @Description TODO
 **/

@Service
public class SupplierImpl extends ServiceImpl<SupplierDao, Supplier> implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    public List<ExcelSupplier> supplierExcelForManager() {
        return supplierDao.supplierExcelForManager();
    }

    public List<ExcelSupplier> supplierExcelForNormal(Long id) {
        return supplierDao.supplierExcelForNormal(id);
    }
}
