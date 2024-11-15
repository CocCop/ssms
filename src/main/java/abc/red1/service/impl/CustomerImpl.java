package abc.red1.service.impl;

import abc.red1.dao.CustomerDao;
import abc.red1.entity.Customer;
import abc.red1.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName CustomerImpl
 * @Author YiXia
 * @Date 2024/1/19 15:56
 * @Version 1.0
 * @Description TODO
 **/

@Service
public class CustomerImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {

}
