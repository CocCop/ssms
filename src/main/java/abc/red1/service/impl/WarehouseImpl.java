package abc.red1.service.impl;


import abc.red1.dao.WarehouseDao;
import abc.red1.entity.Warehouse;
import abc.red1.service.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName WarehouseImpl
 * @Author YiXia
 * @Date 2024/1/18 13:54
 * @Version 1.0
 * @Description TODO
 **/

@Service
public class WarehouseImpl extends ServiceImpl<WarehouseDao, Warehouse> implements WarehouseService {
}
