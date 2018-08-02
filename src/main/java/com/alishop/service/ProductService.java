package com.alishop.service;


import com.alishop.dto.ProductDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

public interface ProductService {

    Logger logger = LogManager.getLogger(ProductService.class);

    List<ProductDTO> searchProductByName(String name, int page, int size);

    List<ProductDTO> searchProductByCategory(int categoryId, int page, int size);

    List<ProductDTO> getMaleProducts(int page, int size);

    List<ProductDTO> getFeMaleProducts(int page, int size);

    List<ProductDTO> getProductPrice(String scope, String sortType, int page, int size);

    List<ProductDTO> getProducts(int page, int size);

    ProductDTO getProduct(int id);

    boolean saveProduct(ProductDTO product);

    boolean updateProduct(ProductDTO product, int productId);

    boolean deleteProduct(int productId);

    int countNumberProducts(String scopePrice);

}
