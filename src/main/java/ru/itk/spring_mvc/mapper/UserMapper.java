package ru.itk.spring_mvc.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_mvc.dto.UserDto;
import ru.itk.spring_mvc.model.User;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true),
        uses = {OrderMapper.class}
)
public interface UserMapper {


    UserDto fromUserToUserDto(User user);

    List<UserDto> fromUserListToUserDtoList(List<User> users);

    User fromUserDtoToUser(UserDto userDto);
}
