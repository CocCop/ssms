package abc.red1.service.impl;

import abc.red1.dao.ManagerDao;
import abc.red1.entity.Manager;
import abc.red1.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ManagerImpl extends ServiceImpl<ManagerDao, Manager> implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Override
    public Manager getLastOne() {


        return managerDao.getLastOne();
    }
}
