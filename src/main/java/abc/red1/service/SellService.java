package abc.red1.service;

import abc.red1.entity.Sell;
import abc.red1.entity.excel.ExcelBuy;
import abc.red1.entity.excel.ExcelSell;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SellService extends IService<Sell> {
    List<ExcelSell> sellExcelForLevel0();

    List<ExcelSell> sellExcelForLevel1(Long id);

}
