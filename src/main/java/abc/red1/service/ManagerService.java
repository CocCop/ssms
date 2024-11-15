package abc.red1.service;

import abc.red1.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;



public interface ManagerService extends IService<Manager> {

    Manager getLastOne();
}
