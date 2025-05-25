package ru.itk.spring_mvc.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.model.Order;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface OrderMapper {


    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.name", target = "customerName")
    @Mapping(source = "user.surname", target = "customerSurname")
    OrderDto fromOrderToOrderDto(Order order);

    List<OrderDto> fromOrderListToOrderDtoList(List<Order> orders);
}
