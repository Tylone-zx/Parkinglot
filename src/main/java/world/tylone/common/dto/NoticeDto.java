package world.tylone.common.dto;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Tylone
 * @since 2021-05-31
 */
@Data
public class NoticeDto implements Serializable {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "目标不能为空")
    private String target;

}
