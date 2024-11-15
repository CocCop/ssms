package abc.red1.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName purchase
 * @Author YiXia
 * @Date 2024/1/19 11:50
 * @Version 1.0
 * @Description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Buy implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long buyId;
    private Long buyMasterId;
    @TableField(exist = false)
    private String buyMasterName;
    private Long buyGoodsId;
    @TableField(exist = false)
    private String buyGoodsName;
    private Long buyWarehouseId;
    @TableField(exist = false)
    private String buyWarehouseName;
    private Long buyNumber;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


}
