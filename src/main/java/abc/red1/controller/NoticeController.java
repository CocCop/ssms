package abc.red1.controller;

import abc.red1.dao.NoticeDao;
import abc.red1.entity.D;
import abc.red1.entity.Goods;
import abc.red1.entity.Notice;
import abc.red1.entity.R;
import abc.red1.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName NoticeController
 * @Author YiXia
 * @Date 2024/1/29 10:07
 * @Version 1.0
 * @Description TODO
 **/
@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {


    @Autowired
    private NoticeService noticeService;

    @PostMapping("/getLastOne")
    public R<Notice> add() {
        Notice n = noticeService.getLastOne();
        return R.success(n);
    }


    @PostMapping("/getAllDesc")
    public R<List<Notice>> getAllDesc() {
        List<Notice> list = noticeService.getAllDesc();
        return R.success(list);
    }

    @PostMapping("/getAllDescLimit3")
    public R<List<Notice>> getAllDescLimit3() {
        List<Notice> list = noticeService.getAllDescLimit3();
        return R.success(list);
    }


    @PostMapping("/deleteById")
    public R<String> deleteById(@RequestBody D d) {
        System.out.println("==="+d.getId());
        noticeService.deleteById(d.getId());
        return R.success("success");
    }



    @PostMapping("/add")
    public R<String> add(@RequestBody Notice n) {
        boolean end = noticeService.save(n);
        if (end) {
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }


}
