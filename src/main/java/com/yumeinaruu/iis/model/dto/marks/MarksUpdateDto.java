package com.yumeinaruu.iis.model.dto.marks;

import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.users.UsersForMarkDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksUpdateDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer mark;

    @NotNull
    @Size(min = 1, max = 50)
    private String subject;

    @NotNull
    private UsersForMarkDto user;
}
