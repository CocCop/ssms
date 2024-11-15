package abc.red1.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSupplier {

    @ExcelProperty("供应商ID")
    private Long supplierId;
    @ExcelProperty("供应商所属管理者ID")
    private Long belongManagerId;
    @ExcelProperty("供应商所属管理者账号名")
    private String managerName;
    @ExcelProperty("供应商账号名")
    private String supplierName;
    @ExcelProperty("供应商电话")
    private String supplierPhone;
    @ExcelProperty("供应商地址")
    private String supplierAddress;
    @ExcelProperty("供应商信息")
    private String supplierMessage;
    @ExcelProperty("供应商邮件")
    private String supplierEmail;
    @ExcelProperty("供应商状态")
    private int supplierState;


    @ExcelProperty("创建账号时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ExcelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
