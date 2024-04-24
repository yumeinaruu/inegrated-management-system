package com.yumeinaruu.iis.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DepartmentDtoUpdate {
    @NotNull
    private Long id;

    @NotNull
    private String name;
}
