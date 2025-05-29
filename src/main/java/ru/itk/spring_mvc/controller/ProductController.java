package ru.itk.spring_mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itk.spring_mvc.dto.ProductDto;
import ru.itk.spring_mvc.service.ProductService;
import ru.itk.spring_mvc.service.JsonService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JsonService jsonService;

    @GetMapping("/all")
    public ResponseEntity<String> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        String jsonResult = jsonService.serializeToJson(products);
        return products.isEmpty()
                ? new ResponseEntity<>(jsonResult, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(jsonResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable(name = "id") Long id) {
        ProductDto product = productService.findById(id);
        return ResponseEntity.ok(jsonService.serializeToJson(product));
    }

    @PostMapping("/")
    public ResponseEntity<String> createProduct(@RequestBody String jsonProductDto) {
        ProductDto productDto = productService.createProduct(
                jsonService.deserializeFromJson(jsonProductDto, ProductDto.class));
        return new ResponseEntity<>(jsonService.serializeToJson(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(name = "id") Long id, @RequestBody String jsonProductDto) {
        ProductDto productDto = productService.updateProduct(id,
                jsonService.deserializeFromJson(jsonProductDto, ProductDto.class));
        return ResponseEntity.ok(jsonService.serializeToJson(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
