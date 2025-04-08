package org.wp2.medsys.services;

import org.wp2.medsys.domain.Product;
import org.wp2.medsys.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product p) {
        productRepository.save(p);
    }

    @Override
    public List<Product> search(String category, Double minPrice, Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(p -> (category == null || category.isEmpty() || p.getCategory().equalsIgnoreCase(category)))
                .filter(p -> (minPrice == null || p.getPrice() >= minPrice))
                .filter(p -> (maxPrice == null || p.getPrice() <= maxPrice))
                .toList();
    }
}
