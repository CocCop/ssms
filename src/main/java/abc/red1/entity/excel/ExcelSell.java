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
public class ExcelSell {

    @ExcelProperty("出库序号")
    private Long sellId;
    @ExcelProperty("出库管理员序号")
    private Long managerId;
    @ExcelProperty("出库管理员名称")
    private String managerName;
    @ExcelProperty("出库商品序号")
    private Long sellGoodsId;
    @ExcelProperty("出库商品名称")
    private String goodsName;
    @ExcelProperty("出库仓库编号")
    private Long sellWarehouseId;
    @ExcelProperty("出库商品数量")
    private Long sellNumber;
    @ExcelProperty("出库消费者序号")
    private Long sellCustomerId;
    @ExcelProperty("出库消费者名称")
    private String customerName;

    @ExcelProperty("出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
