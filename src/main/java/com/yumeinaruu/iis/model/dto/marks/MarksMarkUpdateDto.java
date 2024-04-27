package com.yumeinaruu.iis.model.dto.marks;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksMarkUpdateDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer mark;
}
