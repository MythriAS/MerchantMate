package com.example.realmaddremovequantitycalculation.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realmaddremovequantitycalculation.Interfaces.ProductListenerInterface;
import com.example.realmaddremovequantitycalculation.R;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;
import com.example.realmaddremovequantitycalculation.viewmodel.ProductViewModel;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListenerInterface {
    private ProductViewModel productViewModel;
    private ProductListAdapter productListAdapter;
    private AddedProductListAdapter addedProductListAdapter;

    private AppCompatTextView txtTotalQty, txtSubtotal, txtTotalTax, txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        RecyclerView rvProductList = findViewById(R.id.rv_product_list);
        RecyclerView rvAddedProductList = findViewById(R.id.rv_added_product_list);

        txtTotalQty = findViewById(R.id.txt_total_qty);
        txtSubtotal = findViewById(R.id.txt_subtotal);
        txtTotalTax = findViewById(R.id.txt_total_tax);
        txtTotalAmount = findViewById(R.id.txt_total_amount);

        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvAddedProductList.setLayoutManager(new LinearLayoutManager(this));

        productViewModel.getProductList().observe(this, products -> {
            if (productListAdapter == null) {
                productListAdapter = new ProductListAdapter( products, this);
                rvProductList.setAdapter(productListAdapter);
            } else {
                productListAdapter.updateList(products);
            }
        });

        productViewModel.getAddedProductList().observe(this, addedProducts -> {
            if (addedProductListAdapter == null) {
                addedProductListAdapter = new AddedProductListAdapter(this, addedProducts, this);
                rvAddedProductList.setAdapter(addedProductListAdapter);
            } else {
                addedProductListAdapter.updateList(addedProducts);
            }
            updateTotals(addedProducts);
        });
    }

    @Override
    public void onProductAdd(ProductsRealm product) {
        productViewModel.addProduct(product);
    }

    @Override
    public void onProductDelete(String productUUID, int position) {
        productViewModel.deleteProduct(productUUID);
    }

    private void updateTotals(List<ProductsRealm> addedProducts) {
        double subtotal = 0, totalTax = 0;
        int totalQty = 0;

        for (ProductsRealm product : addedProducts) {
            int qty = product.getQuantity();
            totalQty += qty;
            subtotal += product.getProductPrice() * qty;
            totalTax += (product.getProductTaxRate() / 100) * product.getProductPrice() * qty;
        }

        txtTotalQty.setText(String.valueOf(totalQty));
        txtSubtotal.setText("Rs " + subtotal);
        txtTotalTax.setText("Rs " + totalTax);
        txtTotalAmount.setText("Rs " + (subtotal + totalTax));
    }
}
