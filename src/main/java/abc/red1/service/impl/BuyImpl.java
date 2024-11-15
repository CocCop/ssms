package abc.red1.service.impl;

import abc.red1.dao.BuyDao;
import abc.red1.entity.Buy;
import abc.red1.entity.excel.ExcelBuy;
import abc.red1.service.BuyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PurchaseImpl
 * @Author YiXia
 * @Date 2024/1/19 11:59
 * @Version 1.0
 * @Description TODO
 **/

@Service
public class BuyImpl extends ServiceImpl<BuyDao, Buy> implements BuyService {


    @Autowired
    private BuyDao buyDao;


    @Override
    public List<ExcelBuy> buyExcelForLevel0() {

        return buyDao.buyExcelForLevel0();
    }

    @Override
    public List<ExcelBuy> buyExcelForLevel1(Long id) {
        return buyDao.buyExcelForLevel1(id);
    }
}
