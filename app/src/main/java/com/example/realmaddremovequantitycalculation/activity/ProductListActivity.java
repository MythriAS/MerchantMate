package com.example.realmaddremovequantitycalculation.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmaddremovequantitycalculation.Interfaces.ProductListenerInterface;
import com.example.realmaddremovequantitycalculation.R;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;
import com.example.realmaddremovequantitycalculation.RealmClasses.RealmOperations;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ProductListActivity extends AppCompatActivity implements ProductListenerInterface {
    private Context context;
    private RecyclerView rvProductList, rvAddedProductList;
    private ProductListAdapter productListAdapter;
    private AddedProductListAdapter addedProductListAdapter;
    private List<ProductsRealm> productsRealmList;
    private AppCompatTextView txtTotalQty, txtSubtotal, txtTotalTax, txtTotalAmount;
    private List<ProductsRealm> addedProductList; // Changed to ProductsRealm directly
    private double subtotal = 0, totalQty = 0, totalTax = 0, totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        context = ProductListActivity.this;

        rvProductList = findViewById(R.id.rv_product_list);
        rvAddedProductList = findViewById(R.id.rv_added_product_list);

        txtTotalQty = findViewById(R.id.txt_total_qty);
        txtSubtotal = findViewById(R.id.txt_subtotal);
        txtTotalTax = findViewById(R.id.txt_total_tax);
        txtTotalAmount = findViewById(R.id.txt_total_amount);

        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvAddedProductList.setLayoutManager(new LinearLayoutManager(this));

        productsRealmList = new RealmOperations().getProductList();

        // FIRST RECYCLER VIEW
        if (productsRealmList == null || productsRealmList.size() == 0) {
            insertSomeProductsToRealm();
            productsRealmList = new RealmOperations().getProductList();
        }
        if (productsRealmList != null) {
            productListAdapter = new ProductListAdapter(context, productsRealmList, this);
        }
        rvProductList.setAdapter(productListAdapter);

        // SECOND RECYCLER VIEW
        addedProductList = new ArrayList<>();
        addedProductListAdapter = new AddedProductListAdapter(context, addedProductList, this);
        rvAddedProductList.setAdapter(addedProductListAdapter);
    }

    private void insertSomeProductsToRealm() {
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.south, R.drawable.add, "South Indian Meal", 10.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.north, R.drawable.add, "North Indian Meal", 20.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.dessert, R.drawable.add, "Desserts", 30.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.dosa, R.drawable.add, "Masala Dosa", 40.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.milkshakes, R.drawable.add, "Milkshake", 50.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.icecream, R.drawable.add, "Ice Cream", 60.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.juice, R.drawable.add, "Juice", 70.0, 10.0));
        new RealmOperations().insertProductDetails(new ProductsRealm(R.drawable.bevarages, R.drawable.add, "Beverages", 80.0, 10.0));
    }

    @Override
    public void onProductAdd(ProductsRealm productsRealm) {
        // Check if the product already exists in the added list
        ProductsRealm existingProduct = findProductByUUID(productsRealm.getProductUUID());
        if (existingProduct != null) {
            // Increment quantity if the product is already in the cart
            existingProduct.incrementQuantity();
        } else {
            // Add new product with quantity = 1
            productsRealm.setQuantity(1);
            addedProductList.add(productsRealm);
        }
        addedProductListAdapter.notifyDataSetChanged();
        calculateTotals();
    }

    @Override
    public void onProductDelete(String productUUID, int position) {
        // Find product by UUID
        if (position == RecyclerView.NO_POSITION) {
            // Exit early if the position is invalid
            return;
        }

        ProductsRealm product = addedProductList.get(position);

        if (product != null) {
            if (product.getQuantity() <= 1) {
                addedProductList.remove(position); // Remove product from list
                addedProductListAdapter.notifyItemRemoved(position);
            } else {
                product.decrementQuantity(); // Decrease quantity
                addedProductListAdapter.notifyItemChanged(position);
            }
            addedProductListAdapter.notifyItemChanged(position);
        }

        calculateTotals(); // Recalculate totals after deletion
    }

    private void calculateTotals() {
        subtotal = 0;
        totalQty = 0;
        totalTax = 0;
        totalAmount = 0;

        // Calculate totals considering the quantity of each product
        for (ProductsRealm product : addedProductList) {
            int quantity = product.getQuantity();

            totalQty += quantity;
            subtotal += product.getProductPrice() * quantity;
            double productTaxAmount = (product.getProductTaxRate() / 100) * product.getProductPrice() * quantity;
            totalTax += productTaxAmount;
        }

        totalAmount = subtotal + totalTax;
        displayTotals();
    }

    private void displayTotals() {
        txtTotalQty.setText(String.valueOf(totalQty));
        txtSubtotal.setText("Rs " + subtotal);
        txtTotalTax.setText("Rs " + totalTax);
        txtTotalAmount.setText("Rs " + totalAmount);
    }

    private ProductsRealm findProductByUUID(String productUUID) {
        for (ProductsRealm product : addedProductList) {
            if (product.getProductUUID().equals(productUUID)) {
                return product;
            }
        }
        return null;
    }
}
