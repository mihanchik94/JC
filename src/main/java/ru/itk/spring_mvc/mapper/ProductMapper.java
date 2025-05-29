package ru.itk.spring_mvc.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_mvc.dto.ProductDto;
import ru.itk.spring_mvc.model.Product;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDto fromProductToProductDto(Product product);

    List<ProductDto> fromProductListToProductDtoList(List<Product> product);

    Product fromProductDtoToProduct(ProductDto productDto);
}
