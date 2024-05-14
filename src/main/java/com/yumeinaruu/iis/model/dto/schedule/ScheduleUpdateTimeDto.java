package com.yumeinaruu.iis.model.dto.schedule;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdateTimeDto {
    @NotNull
    @Min(0)
    private Long id;

    @NotNull
    @FutureOrPresent
    private Timestamp beginning;

    @NotNull
    @FutureOrPresent
    private Timestamp ending;
}
