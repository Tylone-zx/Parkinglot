package world.tylone.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ResponseDto implements Serializable {

    @NotBlank(message = "停车场名不能为空")
    private String parklotName;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "属性不能为空")
    private String role;

    @NotNull(message = "评分不能为空")
    private Integer stars;
}
