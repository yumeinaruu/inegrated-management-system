package com.yumeinaruu.iis.model.dto.department;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DepartmentDtoCreate {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
}
