package world.tylone.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LeaveDto implements Serializable {

    @NotBlank(message = "Token不能为空")
    private String reserveToken;
}
