package abc.red1.service.impl;

import abc.red1.dao.NoticeDao;
import abc.red1.entity.Notice;
import abc.red1.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName NoticeImpl
 * @Author YiXia
 * @Date 2024/1/29 10:09
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class NoticeImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Notice getLastOne() {
        return noticeDao.getLastone();
    }
    public List<Notice> getAllDescLimit3() {
        return noticeDao.getAllDescLimit3();
    }


    @Override
    public List<Notice> getAllDesc() {
        return noticeDao.getAllDesc();
    }

    @Override
    public void  deleteById(Long id) {
          noticeDao.deleteById(id);
    }
}
