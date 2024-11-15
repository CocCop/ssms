package abc.red1.service.impl;

import abc.red1.dao.SellDao;
import abc.red1.entity.Sell;
import abc.red1.entity.excel.ExcelSell;
import abc.red1.service.SellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SellImpl
 * @Author YiXia
 * @Date 2024/1/24 16:54
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class SellImpl extends ServiceImpl<SellDao, Sell> implements SellService {
    @Autowired
    private SellDao sellDao;
    @Override
    public List<ExcelSell> sellExcelForLevel0() {
        return sellDao.sellExcelForLevel0();
    }

    @Override
    public List<ExcelSell> sellExcelForLevel1(Long id) {
        return sellDao.sellExcelForLevel1(id);
    }
}
