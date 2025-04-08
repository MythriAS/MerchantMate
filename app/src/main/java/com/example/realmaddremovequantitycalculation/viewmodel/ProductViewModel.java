package com.example.realmaddremovequantitycalculation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;
import com.example.realmaddremovequantitycalculation.RealmClasses.Totals;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;
   private MutableLiveData<Totals> totalsLiveData = new MutableLiveData<>();

    private final Observer<List<ProductsRealm>> addedProductObserver = new Observer<List<ProductsRealm>>() {
        @Override
        public void onChanged(List<ProductsRealm> products) {
            calculateTotals(products);
        }
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

        for (int i = 0; i < products.size(); i++) {
            ProductsRealm productRealmModelClass = products.get(i);
            tax = tax + (productRealmModelClass.getProductTaxRate() / 100) * productRealmModelClass.getProductPrice() * productRealmModelClass.getQuantity();
            subtotal = subtotal + productRealmModelClass.getProductPrice() * productRealmModelClass.getQuantity();
            qty = qty + productRealmModelClass.getQuantity();
        }
        totalsLiveData.setValue(new Totals(qty, subtotal, tax));
    }
}

