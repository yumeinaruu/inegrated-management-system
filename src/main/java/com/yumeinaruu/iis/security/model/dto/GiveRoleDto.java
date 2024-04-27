package com.yumeinaruu.iis.security.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GiveRoleDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private String role;
}
