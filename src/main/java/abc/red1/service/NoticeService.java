package abc.red1.service;

import abc.red1.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName NoticeService
 * @Author YiXia
 * @Date 2024/1/29 10:08
 * @Version 1.0
 * @Description TODO
 **/

public interface NoticeService extends IService<Notice> {

    Notice getLastOne();

    List<Notice> getAllDesc();

    List<Notice> getAllDescLimit3();
    void deleteById(Long id);
}
