package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_mvc.dto.ProductDto;
import ru.itk.spring_mvc.mapper.ProductMapper;
import ru.itk.spring_mvc.model.Product;
import ru.itk.spring_mvc.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAll_shouldReturnListOfProductDto() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(BigDecimal.valueOf(10.00));
        product1.setQuantityInStock(100);

        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(BigDecimal.valueOf(20.00));
        product2.setQuantityInStock(200);

        productList.add(product1);
        productList.add(product2);

        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto1 = new ProductDto();
        productDto1.setProductId(1L);
        productDto1.setName("Product 1");
        productDto1.setDescription("Description 1");
        productDto1.setPrice(BigDecimal.valueOf(10.00));
        productDto1.setQuantityInStock(100);

        ProductDto productDto2 = new ProductDto();
        productDto2.setProductId(2L);
        productDto2.setName("Product 2");
        productDto2.setDescription("Description 2");
        productDto2.setPrice(BigDecimal.valueOf(20.00));
        productDto2.setQuantityInStock(200);

        productDtoList.add(productDto1);
        productDtoList.add(productDto2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.fromProductListToProductDtoList(productList)).thenReturn(productDtoList);

        List<ProductDto> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(productDto1, result.get(0));
        assertEquals(productDto2, result.get(1));

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).fromProductListToProductDtoList(productList);
    }

    @Test
    void findById_shouldReturnProductDtoSuccessfully() {
        Long productId = 1L;
        Product product = new Product();
        product.setProductId(productId);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setQuantityInStock(100);

        ProductDto productDto = new ProductDto();
        productDto.setProductId(productId);
        productDto.setName("Product 1");
        productDto.setDescription("Description 1");
        productDto.setPrice(BigDecimal.valueOf(10.00));
        productDto.setQuantityInStock(100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.fromProductToProductDto(product)).thenReturn(productDto);

        ProductDto result = productService.findById(productId);

        assertNotNull(result);
        assertEquals(productDto.getProductId(), result.getProductId());
        assertEquals(productDto.getName(), result.getName());
        assertEquals(productDto.getDescription(), result.getDescription());
        assertEquals(productDto.getPrice(), result.getPrice());
        assertEquals(productDto.getQuantityInStock(), result.getQuantityInStock());

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).fromProductToProductDto(product);
    }

    @Test
    void findById_shouldThrowEntityNotFoundExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.findById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void createProduct_shouldCreateProductSuccessfully() {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");
        productDto.setDescription("New Description");
        productDto.setPrice(BigDecimal.valueOf(30.00));
        productDto.setQuantityInStock(300);

        Product product = new Product();
        product.setName("New Product");
        product.setDescription("New Description");
        product.setPrice(BigDecimal.valueOf(30.00));
        product.setQuantityInStock(300);

        Product savedProduct = new Product();
        savedProduct.setProductId(1L);
        savedProduct.setName("New Product");
        savedProduct.setDescription("New Description");
        savedProduct.setPrice(BigDecimal.valueOf(30.00));
        savedProduct.setQuantityInStock(300);

        ProductDto expectedProductDto = new ProductDto();
        expectedProductDto.setProductId(1L);
        expectedProductDto.setName("New Product");
        expectedProductDto.setDescription("New Description");
        expectedProductDto.setPrice(BigDecimal.valueOf(30.00));
        expectedProductDto.setQuantityInStock(300);

        when(productMapper.fromProductDtoToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.fromProductToProductDto(savedProduct)).thenReturn(expectedProductDto);


        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(expectedProductDto.getProductId(), result.getProductId());
        assertEquals(expectedProductDto.getName(), result.getName());
        assertEquals(expectedProductDto.getDescription(), result.getDescription());
        assertEquals(expectedProductDto.getPrice(), result.getPrice());
        assertEquals(expectedProductDto.getQuantityInStock(), result.getQuantityInStock());

        verify(productMapper, times(1)).fromProductDtoToProduct(productDto);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).fromProductToProductDto(savedProduct);
    }

    @Test
    void updateProduct_shouldUpdateProductSuccessfully() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setDescription("Updated Description");
        productDto.setPrice(BigDecimal.valueOf(40.00));
        productDto.setQuantityInStock(400);

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setName("Product 1");
        existingProduct.setDescription("Description 1");
        existingProduct.setPrice(BigDecimal.valueOf(10.00));
        existingProduct.setQuantityInStock(100);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(BigDecimal.valueOf(40.00));
        updatedProduct.setQuantityInStock(400);

        ProductDto expectedProductDto = new ProductDto();
        expectedProductDto.setProductId(productId);
        expectedProductDto.setName("Updated Product");
        expectedProductDto.setDescription("Updated Description");
        expectedProductDto.setPrice(BigDecimal.valueOf(40.00));
        expectedProductDto.setQuantityInStock(400);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.fromProductToProductDto(updatedProduct)).thenReturn(expectedProductDto);

        ProductDto result = productService.updateProduct(productId, productDto);

        assertNotNull(result);
        assertEquals(expectedProductDto.getProductId(), result.getProductId());
        assertEquals(expectedProductDto.getName(), result.getName());
        assertEquals(expectedProductDto.getDescription(), result.getDescription());
        assertEquals(expectedProductDto.getPrice(), result.getPrice());
        assertEquals(expectedProductDto.getQuantityInStock(), result.getQuantityInStock());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        verify(productMapper, times(1)).fromProductToProductDto(updatedProduct);
    }

    @Test
    void delete_shouldDeleteProductSuccessfully() {
        Long productId = 1L;

        productService.delete(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

}