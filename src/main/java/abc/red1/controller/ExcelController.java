package abc.red1.controller;

import abc.red1.common.BaseContext;
import abc.red1.entity.Manager;
import abc.red1.entity.excel.ExcelBuy;
import abc.red1.entity.excel.ExcelSell;
import abc.red1.entity.excel.ExcelSupplier;
import abc.red1.service.BuyService;
import abc.red1.service.ManagerService;
import abc.red1.service.SellService;
import abc.red1.service.SupplierService;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ExcelController
 * @Author YiXia
 * @Date 2024/1/25 23:07
 * @Version 1.0
 * @Description TODO
 **/
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private BuyService buyService;
    @Autowired
    private SellService sellService;

    Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

//    @PostMapping("/downloadSupplierExcel")
//    public void downloadSupplierExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
////        response.setHeader("Content-Disposition","attachment;filename="+java.net.URLEncoder.encode("123","UTF-8"));
//        List<ExcelSupplier> list = supplierService.supplierExcel();
//        log.info("excel数据 :{}",list);
//        EasyExcel.write(response.getOutputStream(),ExcelSupplier.class).sheet("模板").doWrite(list);
//    }

    @PostMapping("/downloadSupplierExcel")
    public void downloadSupplierExcel(HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        date = new Date();
        String fileName = "D:/red1/excel/供应商" + formatter.format(date) + ".xlsx";
        //根据操作者权限判断数据
        long id = 1;
        if (BaseContext.getCurrentId() == null) {
            id = 1;
        } else {
            id = BaseContext.getCurrentId();
        }
        Manager m = managerService.getById(id);
        List<ExcelSupplier> list = new ArrayList<>();
        if (m.getManagerLevel() == 0) {
            list = supplierService.supplierExcelForManager();
        } else {
            list = supplierService.supplierExcelForNormal(id);
        }
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ExcelSupplier.class).sheet("模板").doWrite(list);


//        //读取文件
//        File file = new File(fileName);
//        //输入流
//        InputStream inputStream = new FileInputStream(file);
//        //响应头
//        response.setHeader("Content-Disposition", "attachment; filename=file_name");
//        //文件响应体
//        OutputStream outputStream = response.getOutputStream();
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
//        inputStream.close();
//        outputStream.close();
//        return R.success("success");

    }


    @PostMapping("/downloadBuyExcel")
    public void downloadBuyExcel(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        date = new Date();
        String fileName = "D:/red1/excel/入库记录" + formatter.format(date) + ".xlsx";
        //根据操作者权限判断数据
        List<ExcelBuy> list = buyService.buyExcelForLevel0();

        log.info("打印数据库ExcelBuy数据={}", list);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ExcelBuy.class).sheet("模板").doWrite(list);


    }


//    @PostMapping("/downloadSellExcel")
//    public void downloadSellExcel(HttpServletRequest request,
//                                  HttpServletResponse response) throws IOException {
//        String fileName = "D:/red1/excel/simpleWrite.xlsx";
//        //根据操作者权限判断数据
//        long id = BaseContext.getCurrentId();
//        Manager m = managerService.getById(id);
//        List<ExcelSupplier> list = new ArrayList<>();
//        if (m.getManagerLevel() == 0) {
//            list = buyService.buyExcelForManager();
//        } else {
//            list = buyService.buyExcelForNormal(id);
//        }
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(fileName, ExcelSupplier.class).sheet("模板").doWrite(list);
//
//
//    }


    @PostMapping("/downloadSellExcel")
    public void downloadSellExcel(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        date = new Date();
        String fileName = "D:/red1/excel/出库记录" + formatter.format(date) + ".xlsx";
        //根据操作者权限判断数据
        List<ExcelSell> list = sellService.sellExcelForLevel0();

        log.info("打印数据库ExcelSell数据={}", list);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ExcelSell.class).sheet("模板").doWrite(list);


    }
}


