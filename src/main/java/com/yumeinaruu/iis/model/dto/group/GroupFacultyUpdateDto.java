package com.yumeinaruu.iis.model.dto.group;

import com.yumeinaruu.iis.model.dto.faculty.FacultyForGroupDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupFacultyUpdateDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private FacultyForGroupDto faculty;
}
