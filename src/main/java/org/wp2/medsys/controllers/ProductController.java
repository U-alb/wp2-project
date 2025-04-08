package org.wp2.medsys.controllers;


import org.wp2.medsys.domain.Product;
import org.wp2.medsys.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {
    //constructor injection of the service
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public String listProducts(Model model ){
        model.addAttribute("products",productService.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model){
        model.addAttribute("product",new Product());
        return "addproducts";
    }

    @PostMapping("/new")
    public String addProduct(Product product){
        productService.save(product);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        //check if search button is pressed, otherwise it shows the result list at all times
        boolean searchPerformed = (category != null && !category.isEmpty()) ||
                minPrice != null || maxPrice != null;

        List<Product> results = searchPerformed
                ? productService.search(category, minPrice, maxPrice)
                : List.of();

        model.addAttribute("results", results);
        model.addAttribute("searchPerformed", searchPerformed);
        return "searchproducts";
    }


}
