package com.yumeinaruu.iis.model.dto.schedule;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreateDto {
    @NotNull
    @Size(min = 1, max = 50)
    private String subject;

    @NotNull
    @Size(min = 1, max = 50)
    private String group;

    @NotNull
    @FutureOrPresent
    private Timestamp time;
}

