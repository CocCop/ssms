package abc.red1.service.impl;

import abc.red1.dao.GoodsDao;
import abc.red1.entity.Goods;
import abc.red1.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserImpl
 * @Author YiXia
 * @Date 2024/1/12 10:51
 * @Version 1.0
 * @Description TODO
 **/

@Service
public class GoodsImpl extends ServiceImpl<GoodsDao, Goods> implements GoodsService {
}
