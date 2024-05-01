package com.yumeinaruu.iis.model.dto.speciality;

import com.yumeinaruu.iis.model.dto.faculty.FacultyForSpecialityDto;
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
public class SpecialityUpdateFacultyDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private FacultyForSpecialityDto faculty;
}
