package com.gally.eretroway.controllers;


import com.gally.eretroway.models.Product;
import com.gally.eretroway.repositories.ProductRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;



@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class ProductControllerTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void repositoryIsEmpty() {
        Iterable<Product> products = productRepository.findAll();

        assertThat(products).isEmpty();
    }

    @Test
    public void creatingProduct() {
        Product product = productRepository.save(new Product("bmw", "lorem epsum",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg",
                50, 500000, true));
        assertThat(product).hasFieldOrPropertyWithValue("model", "bmw");
        assertThat(product).hasFieldOrPropertyWithValue("description", "lorem epsum");
        assertThat(product).hasFieldOrPropertyWithValue("imageUrl",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg");
        assertThat(product).hasFieldOrPropertyWithValue("stockQuantity", 50);
        assertThat(product).hasFieldOrPropertyWithValue("price", 500000);
        assertThat(product).hasFieldOrPropertyWithValue("featured", true);


    }


    @Test
    public void FindingAllProducts() {
       Product product1 = productRepository.save(new Product("Product#1", "Desc#1",
               "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
       testEntityManager.persist(product1);

        Product product2 = productRepository.save(new Product("Product#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));
        testEntityManager.persist(product2);

        Product product3 = productRepository.save(new Product("Product#3", "Desc#3",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 20, 250000, true));
        testEntityManager.persist(product3);

        Iterable<Product> products = productRepository.findAll();

        assertThat(products).hasSize(3).contains(product1, product2, product3);
    }

    @Test
    public void FindProductById() {
        Product product1 = productRepository.save(new Product("Product#1", "Desc#1",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
        testEntityManager.persist(product1);

        Product product2 = productRepository.save(new Product("Product#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));
        testEntityManager.persist(product2);

        Product foundProduct = productRepository.findById(product2.getId()).get();

        assertThat(foundProduct).isEqualTo(product2);
    }

    @Test
    public void FindFeaturedProducts() {
        Product product1 = productRepository.save(new Product("Product#1", "Desc#1",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
        testEntityManager.persist(product1);

        Product product2 = productRepository.save(new Product("Product#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));
        testEntityManager.persist(product2);

        Product product3 = productRepository.save(new Product("Product#3", "Desc#3",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 20, 250000, true));
        testEntityManager.persist(product3);

        Iterable<Product> products = productRepository.findByFeatured(true);

        assertThat(products).hasSize(2).contains(product1, product3);

    }

    @Test
    public void findProductsByModel() {
        Product product1 = productRepository.save(new Product("Spring Boot#1", "Desc#1",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
        testEntityManager.persist(product1);

        Product product2 = productRepository.save(new Product("Java#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));
        testEntityManager.persist(product2);

        Product product3 = productRepository.save(new Product("Spring Data Jpa#3", "Desc#3",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 20, 250000, true));
        testEntityManager.persist(product3);

        Iterable<Product> products = productRepository.findByModelContaining("ring");

        assertThat(products).hasSize(2).contains(product1, product3);
    }

    @Test
    public void updatingProductById() {
        Product product1 = productRepository.save(new Product("Spring Boot#1", "Desc#1",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
        testEntityManager.persist(product1);
        Product product2 = productRepository.save(new Product("Java#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));
        testEntityManager.persist(product2);
        Product updateProduct = new Product("updated Product 2", "Updated Desc 2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg" , 50, 80000, true);
        Product product = productRepository.findById(product2.getId()).get();
        product.setModel(updateProduct.getModel());
        product.setDescription(updateProduct.getDescription());
        product.setImageUrl(updateProduct.getImageUrl());
        product.setStockQuantity(updateProduct.getStockQuantity());
        product.setPrice(updateProduct.getPrice());
        product.setFeatured(updateProduct.isFeatured());
        Product checkProduct = productRepository.findById(product2.getId()).get();
        assertThat(checkProduct.getId()).isEqualTo(product2.getId());
        assertThat(checkProduct.getModel()).isEqualTo(product2.getModel());
        assertThat(checkProduct.getDescription()).isEqualTo(product2.getDescription());
        assertThat(checkProduct.getImageUrl()).isEqualTo(product2.getImageUrl());
        assertThat(checkProduct.getStockQuantity()).isEqualTo(product2.getStockQuantity());
        assertThat(checkProduct.getPrice()).isEqualTo(product2.getPrice());
        assertThat(checkProduct.isFeatured()).isEqualTo(product2.isFeatured());

    }

    @Test
    public void deletingProductById() {
        Product product1 = productRepository.save(new Product("Spring Boot#1", "Desc#1",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
        testEntityManager.persist(product1);

        Product product2 = productRepository.save(new Product("Java#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));
        testEntityManager.persist(product2);

        Product product3 = productRepository.save(new Product("Spring Data Jpa#3", "Desc#3",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 20, 250000, true));
        testEntityManager.persist(product3);

        productRepository.deleteById(product2.getId());

        Iterable<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2).contains(product1, product3);
    }

    @Test
    public void deletingAllProducts() {
        testEntityManager.persist(new Product("Spring Boot#1", "Desc#1",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 60, 10000, true));
        testEntityManager.persist(new Product("Java#2", "Desc#2",
                "https://images.eplaque.fr/wp-content/uploads/2020/03/11153010/carte-grise-moto.jpg", 80, 50000, false));

        productRepository.deleteAll();

        assertThat(productRepository.findAll().isEmpty());
    }

}
