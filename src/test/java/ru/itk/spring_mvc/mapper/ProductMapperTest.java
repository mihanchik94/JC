package ru.itk.spring_mvc.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.itk.spring_mvc.dto.ProductDto;
import ru.itk.spring_mvc.model.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testFromProductToProductDto() {
        Product product = Product.builder()
                .productId(1L)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(19.99))
                .quantityInStock(100)
                .build();

        ProductDto productDto = productMapper.fromProductToProductDto(product);

        assertNotNull(productDto);
        assertEquals(product.getProductId(), productDto.getProductId());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getQuantityInStock(), productDto.getQuantityInStock());
    }

    @Test
    void testFromProductListToProductDtoList() {
        Product product1 = Product.builder()
                .productId(1L)
                .name("Test Product 1")
                .description("Test Description 1")
                .price(BigDecimal.valueOf(19.99))
                .quantityInStock(100)
                .build();

        Product product2 = Product.builder()
                .productId(2L)
                .name("Test Product 2")
                .description("Test Description 2")
                .price(BigDecimal.valueOf(29.99))
                .quantityInStock(200)
                .build();

        List<Product> productList = Arrays.asList(product1, product2);

        List<ProductDto> productDtoList = productMapper.fromProductListToProductDtoList(productList);

        assertNotNull(productDtoList);
        assertEquals(2, productDtoList.size());

        ProductDto productDto1 = productDtoList.get(0);
        assertEquals(product1.getProductId(), productDto1.getProductId());
        assertEquals(product1.getName(), productDto1.getName());
        assertEquals(product1.getDescription(), productDto1.getDescription());
        assertEquals(product1.getPrice(), productDto1.getPrice());
        assertEquals(product1.getQuantityInStock(), productDto1.getQuantityInStock());

        ProductDto productDto2 = productDtoList.get(1);
        assertEquals(product2.getProductId(), productDto2.getProductId());
        assertEquals(product2.getName(), productDto2.getName());
        assertEquals(product2.getDescription(), productDto2.getDescription());
        assertEquals(product2.getPrice(), productDto2.getPrice());
        assertEquals(product2.getQuantityInStock(), productDto2.getQuantityInStock());
    }

    @Test
    void testFromProductDtoToProduct() {
        ProductDto productDto = ProductDto.builder()
                .productId(1L)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(19.99))
                .quantityInStock(100)
                .build();

        Product product = productMapper.fromProductDtoToProduct(productDto);
        assertNotNull(product);
        assertEquals(productDto.getProductId(), product.getProductId());
        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getDescription(), product.getDescription());
        assertEquals(productDto.getPrice(), product.getPrice());
        assertEquals(productDto.getQuantityInStock(), product.getQuantityInStock());
    }

}