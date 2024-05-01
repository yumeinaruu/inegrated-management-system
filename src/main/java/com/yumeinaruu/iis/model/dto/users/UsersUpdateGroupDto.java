package com.yumeinaruu.iis.model.dto.users;

import com.yumeinaruu.iis.model.Group;
import com.yumeinaruu.iis.model.dto.group.GroupForUsersDto;
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
public class UsersUpdateGroupDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private GroupForUsersDto group;
}
