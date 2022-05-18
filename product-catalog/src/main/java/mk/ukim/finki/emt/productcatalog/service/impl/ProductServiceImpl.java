package mk.ukim.finki.emt.productcatalog.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import mk.ukim.finki.emt.productcatalog.domain.models.ProductId;
import mk.ukim.finki.emt.productcatalog.domain.models.exceptions.ProductNotFoundException;
import mk.ukim.finki.emt.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productcatalog.service.ProductService;
import mk.ukim.finki.emt.productcatalog.service.forms.ProductForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(ProductId id) {
        return this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product createProduct(ProductForm form) {
        Product product = Product.build(form.getProductName(), form.getPrice(), form.getSales());
        this.productRepository.save(product);
        return product;
    }

    @Override
    public Product orderItemCreated(ProductId productId, int quantity) {
        Product product = this.findById(productId);
        product.addSales(quantity);
        this.productRepository.saveAndFlush(product);
        return product;
    }

    @Override
    public Product orderItemRemoved(ProductId productId, int quantity) {
        Product product = this.findById(productId);
        product.removeSales(quantity);
        this.productRepository.saveAndFlush(product);
        return product;
    }

    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }
}
