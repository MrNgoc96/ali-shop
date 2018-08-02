package com.alishop.service.impl;

import com.alishop.bases.BaseUtils;
import com.alishop.bases.TransformUtils;
import com.alishop.dto.ProductDTO;
import com.alishop.entity.Product;
import com.alishop.repository.ProductRepository;
import com.alishop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;
    @Autowired
    TransformUtils<Product, ProductDTO> transformUtils;
    private final double DEFAULT_BEGIN_PRICE = 0.0;
    private final double DEFAULT_END_PRICE = 5000000.0;


    @Override
    public int countNumberProducts(String scopePrice) {
        double beginPrice = getBeginPrice(scopePrice);
        double endPrice = getEndPrice(scopePrice);
        return (int) (long) repository.countNumberProducts(beginPrice, endPrice);
    }

    @Override
    public List<ProductDTO> searchProductByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Product> productList = repository.searchProductByName(name, pageRequest);
        return transformUtils.transformList(productList, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> searchProductByCategory(int categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Product> productList = repository.searchProductByCategory(categoryId, pageRequest);
        return transformUtils.transformList(productList, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getMaleProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Product> productList = repository.getMaleProducts(pageRequest);
        return transformUtils.transformList(productList, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getFeMaleProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Product> productList = repository.getFeMaleProducts(pageRequest);
        return transformUtils.transformList(productList, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getProductPrice(String scope, String sortType, int page, int size) {
        double beginPrice = getBeginPrice(scope);
        double endPrice = getEndPrice(scope);
        Sort sort = BaseUtils.getSort(sortType, "price");
        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);
        List<Product> productList = repository.getProductPrice(beginPrice, endPrice, pageRequest);
        return transformUtils.transformList(productList, ProductDTO.class);
    }

    public List<ProductDTO> getProducts(int page, int size) {
        page--;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Product> productList = repository.findAll(pageRequest).getContent();
        return transformUtils.transformList(productList, ProductDTO.class);
    }

    public ProductDTO getProduct(int id) {
        Product product = repository.getOne(id);
        return transformUtils.transform(product, ProductDTO.class);
    }


    public boolean saveProduct(ProductDTO productDTO) {
        Product product = transformUtils.transformReverse(productDTO, Product.class);
        return repository.save(product) != null;
    }

    public boolean updateProduct(ProductDTO productDTO, int productId) {
        if (getProduct(productId) == null) return false;
        Product product = transformUtils.transformReverse(productDTO, Product.class);
        return repository.save(product) != null;
    }

    public boolean deleteProduct(int productId) {
        try {
            repository.deleteById(productId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String uploadImage(ServletContext context, String imageLink, int productId, List<MultipartFile> files) {
        if (files.size() > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String path = context.getRealPath("resources/images") + "/SP" + productId + "_" + file.getOriginalFilename();
                    try {
                        file.transferTo(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageLink += "resources/images/SP" + productId + "_" + file.getOriginalFilename() + ";";
                }
            }
        }
        return imageLink;
    }

    public String removeImageLink(ServletContext context, String removeImgIndex, String[] productImageLink) {
        String[] indexs = removeImgIndex.split(";");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(productImageLink));
        if (!indexs[0].equals("")) {
            for (String index : indexs) {
                int i = Integer.parseInt(index);
                removeImage(context, list.get(i));
                list.remove(i);
            }
        }
        StringBuilder imagelink = new StringBuilder();
        for (String productImg : list) {

            if (!productImg.equals("") && !productImg.equals("resources/images/default-img.svg")) {
                imagelink.append(productImg + ";");
            }

        }
        return imagelink.toString();
    }

    public void removeImage(ServletContext context, String imgLink) {
        if (!imgLink.equals("") && !imgLink.equals("resources/images/default-img.svg") && imgLink.startsWith("resources/images/")) {
            String path = context.getRealPath(imgLink);
            File file = new File(path);
            file.delete();
        }
    }


    private double getBeginPrice(String scope) {
        double beginPrice = DEFAULT_BEGIN_PRICE;
        if (!scope.equals("")) {
            String[] arr = scope.split("/");
            beginPrice = arr[0] != null && BaseUtils.isNumber(arr[0]) ? Double.parseDouble(arr[0]) : beginPrice;
        }
        return beginPrice;
    }

    private double getEndPrice(String scope) {
        double endPrice = DEFAULT_END_PRICE;
        if (!scope.equals("")) {
            String[] arr = scope.split("/");
            endPrice = arr[1] != null && BaseUtils.isNumber(arr[1]) ? Double.parseDouble(arr[1]) : endPrice;
        }
        return endPrice;
    }


}
