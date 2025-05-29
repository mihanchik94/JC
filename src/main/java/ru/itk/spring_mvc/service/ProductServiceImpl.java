package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.spring_mvc.dto.ProductDto;
import ru.itk.spring_mvc.mapper.ProductMapper;
import ru.itk.spring_mvc.model.Product;
import ru.itk.spring_mvc.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        return productMapper.fromProductListToProductDtoList(productRepository.findAll());
    }

    @Override
    public ProductDto findById(Long id) {
        return productMapper.fromProductToProductDto(findProductById(id));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.fromProductDtoToProduct(productDto);
        return productMapper.fromProductToProductDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = findProductById(id);
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantityInStock(productDto.getQuantityInStock());
        return productMapper.fromProductToProductDto(productRepository.save(existingProduct));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id = %d not found", id)));
    }
}
