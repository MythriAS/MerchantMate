package com.example.realmaddremovequantitycalculation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.realmaddremovequantitycalculation.R;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProductRepository {
    private final MutableLiveData<List<ProductsRealm>> productList = new MutableLiveData<>();
    private final MutableLiveData<List<ProductsRealm>> addedProductList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<ProductsRealm>> getProductList() {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<ProductsRealm> results = realm.where(ProductsRealm.class).findAll();
            if (results.isEmpty()) {
                insertDefaultProducts();
            }
            productList.postValue(realm.copyFromRealm(results));
        }
        return productList;
    }

    public LiveData<List<ProductsRealm>> getAddedProductList() {
        return addedProductList;
    }

    private void insertDefaultProducts() {

        try (Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm _realm) {
                    _realm.insert(new ProductsRealm(R.drawable.south, R.drawable.add, "South Indian Meal", 10.0, 10.0));
                    _realm.insert(new ProductsRealm(R.drawable.north, R.drawable.add, "North Indian Meal", 20.0, 10.0));
                    _realm.insert(new ProductsRealm(R.drawable.dessert, R.drawable.add, "Desserts", 30.0, 10.0));
                    _realm.insert(new ProductsRealm(R.drawable.dosa, R.drawable.add, "Masala Dosa", 40.0, 10.0));
                    _realm.insert(new ProductsRealm(R.drawable.milkshakes, R.drawable.add, "Milkshake", 50.0, 10.0));
                    _realm.insert(new ProductsRealm(R.drawable.icecream, R.drawable.add, "Ice Cream", 60.0, 10.0));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(ProductsRealm product) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm _realm) {
                    ProductsRealm existingProduct = _realm.where(ProductsRealm.class)
                            .equalTo("productUUID", product.getProductUUID())
                            .findFirst();
                    if (existingProduct != null) {
                        existingProduct.incrementQuantity();
                    } else {
                        product.setQuantity(1);
                        _realm.insertOrUpdate(product);
                    }
                    RealmResults<ProductsRealm> allProducts = realm.where(ProductsRealm.class).findAll();
                    List<ProductsRealm> addedList = new ArrayList<>();

                    for (int i = 0; i < allProducts.size(); i++) {
                        ProductsRealm products = allProducts.get(i);
                        if (products.getQuantity() > 0) {
                            addedList.add(realm.copyFromRealm(products));
                        }
                    }
                    addedProductList.postValue(addedList);
                }
            });
        }
    }


    public void deleteProduct(String productUUID) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm _realm) {
                    ProductsRealm product = _realm.where(ProductsRealm.class)
                            .equalTo("productUUID", productUUID)
                            .findFirst();

                    if (product != null) {
                        if (product.getQuantity() > 1) {
                            product.decrementQuantity();
                        } else {
                            product.deleteFromRealm();
                        }
                    }
                }
            });
        } finally {
            realm.close();
        }

        List<ProductsRealm> currentList = addedProductList.getValue();
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                ProductsRealm product = currentList.get(i);
                if (product.getProductUUID().equals(productUUID)) {
                    if (product.getQuantity() > 1) {
                        product.decrementQuantity();
                    } else {
                        currentList.remove(i);
                    }
                    break;
                }
            }
            addedProductList.postValue(currentList);
        }
    }
}

