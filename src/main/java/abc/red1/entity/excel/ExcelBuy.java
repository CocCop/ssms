package abc.red1.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author YiXia
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelBuy {

    @ExcelProperty("入库序号")
    private Long buyId;
    @ExcelProperty("入库管理员序号")
    private Long managerId;
    @ExcelProperty("入库管理员名称")
    private String managerName;
    @ExcelProperty("入库商品序号")
    private Long buyGoodsId;
    @ExcelProperty("入库商品名称")
    private String goodsName;
    @ExcelProperty("入库仓库编号")
    private Long buyWarehouseId;
    @ExcelProperty("入库仓库名称")
    private String warehouseName;
    @ExcelProperty("入库商品数量")
    private Long buyNumber;






    @ExcelProperty("入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
