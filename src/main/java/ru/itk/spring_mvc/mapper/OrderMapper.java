package ru.itk.spring_mvc.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.model.Order;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductMapper.class})
public interface OrderMapper {

    @Mapping(source = "customer.customerId", target = "customerId")
    OrderDto fromOrderToOrderDto(Order order);

    @Mapping(source = "customerId", target = "customer.customerId")
    Order fromOrderDtoToOrder(OrderDto orderDto);
}
