package abc.red1.service;


import abc.red1.entity.Buy;
import abc.red1.entity.excel.ExcelBuy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BuyService extends IService<Buy> {

    List<ExcelBuy> buyExcelForLevel0();

    List<ExcelBuy> buyExcelForLevel1(Long id);

}
