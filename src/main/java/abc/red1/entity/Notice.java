package abc.red1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Notice
 * @Author YiXia
 * @Date 2024/1/29 0:31
 * @Version 1.0
 * @Description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice implements Serializable {
    private static final long serialVersionUID=1L;
    private Long noticeId;
    private String noticeTitle;
    private String noticeMessage;
}
