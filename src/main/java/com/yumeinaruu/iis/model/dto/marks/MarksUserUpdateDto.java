package com.yumeinaruu.iis.model.dto.marks;

import com.yumeinaruu.iis.model.dto.users.UsersForMarkDto;
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
public class MarksUserUpdateDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private UsersForMarkDto user;
}
