package world.tylone.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ClientDto implements Serializable {

    private Integer id;

    private String username;

    private String role;

    private String email;

    private String telNo;

    private Integer vipFee;

    private Integer credit;

    private Integer isBlack;

    private LocalDateTime created;
}
