package com.example.realmaddremovequantitycalculation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;

    public ProductViewModel() {
        repository = new ProductRepository();
    }

    public LiveData<List<ProductsRealm>> getProductList() {
        return repository.getProductList();
    }

    public LiveData<List<ProductsRealm>> getAddedProductList() {
        return repository.getAddedProductList();
    }

    public void addProduct(ProductsRealm product) {
        repository.addProduct(product);
    }

    public void deleteProduct(String productUUID) {
        repository.deleteProduct(productUUID);
    }
}

