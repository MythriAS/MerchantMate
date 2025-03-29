package com.example.realmaddremovequantitycalculation.Interfaces;


import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;

public interface ProductListenerInterface {
    void onProductAdd(ProductsRealm productsRealm);
    void onProductDelete(String productUUID,int position);

}
