package abc.red1.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName WarehouseDetail
 * @Author YiXia
 * @Date 2024/1/24 11:17
 * @Version 1.0
 * @Description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDetail implements Serializable {
    private static final long serialVersionUID=1L;


    private Long goodsId;
    private String goodsName;
    private double goodsPrice;
    private Long goodsNumber;


}
