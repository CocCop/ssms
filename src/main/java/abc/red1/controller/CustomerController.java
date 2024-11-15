package abc.red1.controller;


import abc.red1.entity.Customer;
import abc.red1.entity.D;
import abc.red1.entity.R;
import abc.red1.service.CustomerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YiXia
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;
    //默认每页数据量
    private int pageSize = 10;


    @PostMapping("/getPage")
    public R<IPage> getAll(@RequestBody D d) {

        IPage<Customer> page = new Page<>(d.getNum1(), pageSize);
        customerService.page(page);

        return R.success(page);
    }


    @PostMapping("/add")
    public R<String> add(@RequestBody Customer c) {
        boolean end = customerService.save(c);
        if (end) {
            log.info("打印 add 方法上传的 Customer 对象数据   {}", c.toString());
            return R.success("新增成功");
        } else {
            return R.error("新增失败");
        }
    }


    @PostMapping("/deleteById")
    public R<String> deleteById(@RequestBody D d) {

        boolean end = customerService.removeById(d.getId());
        if (end) {
            log.info("打印 CustomerController -deleteById方法上传的 Customer 对象数据   {}", d.toString());
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    //
//
    @PostMapping("/edit")
    public R<String> editById(@RequestBody Customer c) {
        boolean end = customerService.updateById(c);
        if (end) {
            log.info("打印 CustomerController -edit方法上传的 Customer 对象数据   {}", c.toString());
            return R.success("编辑成功");
        } else {
            return R.error("编辑失败");
        }
    }

    //
//
    @PostMapping("/getCustomerById")
    public R<Customer> getCustomerById(@RequestBody D d) {
        return R.success(customerService.getById(d.getNum1()));
    }


    @PostMapping("/getAllCustomer")
    public R<List<Customer>> getAllCustomer() {
        return R.success(customerService.list());
    }


}
