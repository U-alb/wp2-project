package org.wp2.medsys.services;

import org.wp2.medsys.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void save(Product p);
    List<Product> search(String category, Double minPrice, Double maxPrice);

}
