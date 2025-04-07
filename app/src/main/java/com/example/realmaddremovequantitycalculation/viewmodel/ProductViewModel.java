package com.example.realmaddremovequantitycalculation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;
import com.example.realmaddremovequantitycalculation.Totals;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;

    private MutableLiveData<List<ProductsRealm>> addedProductList = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Totals> totalsLiveData = new MutableLiveData<>();

    private final Observer<List<ProductsRealm>> addedProductObserver = products -> {
        addedProductList.setValue(products);
        calculateTotals(products);
    };
    public ProductViewModel() {
        repository = new ProductRepository();
        repository.getAddedProductList().observeForever(addedProductObserver);
    }

    public LiveData<List<ProductsRealm>> getProductList() {
        return repository.getProductList();
    }

    public LiveData<List<ProductsRealm>> getAddedProductList() {
        return repository.getAddedProductList();
    }

    public LiveData<Totals> getTotalsLiveData() {
        return totalsLiveData;
    }

    public void addProduct(ProductsRealm product) {
        repository.addProduct(product);
    }

    public void deleteProduct(String productUUID) {
        repository.deleteProduct(productUUID);
    }

    private void calculateTotals(List<ProductsRealm> products) {
        double subtotal = 0, tax = 0;
        int qty = 0;

        for (ProductsRealm p : products) {
            qty += p.getQuantity();
            subtotal += p.getProductPrice() * p.getQuantity();
            tax += (p.getProductTaxRate() / 100.0) * p.getProductPrice() * p.getQuantity();
        }
        totalsLiveData.setValue(new Totals(qty, subtotal, tax));
    }
}

