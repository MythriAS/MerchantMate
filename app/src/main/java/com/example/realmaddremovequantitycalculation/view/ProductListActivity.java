package com.example.realmaddremovequantitycalculation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmaddremovequantitycalculation.Interfaces.ProductListenerInterface;
import com.example.realmaddremovequantitycalculation.R;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;
import com.example.realmaddremovequantitycalculation.Totals;
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

        RecyclerView rvProductList = findViewById(R.id.rv_product_list);
        RecyclerView rvAddedProductList = findViewById(R.id.rv_added_product_list);

        txtTotalQty = findViewById(R.id.txt_total_qty);
        txtSubtotal = findViewById(R.id.txt_subtotal);
        txtTotalTax = findViewById(R.id.txt_total_tax);
        txtTotalAmount = findViewById(R.id.txt_total_amount);

        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvAddedProductList.setLayoutManager(new LinearLayoutManager(this));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductList().observe(this, new Observer<List<ProductsRealm>>() {
            @Override
            public void onChanged(List<ProductsRealm> productsRealms) {
                if (productListAdapter == null) {
                    productListAdapter = new ProductListAdapter(productsRealms, ProductListActivity.this);
                    rvProductList.setAdapter(productListAdapter);
                } else {
                    productListAdapter.updateList(productsRealms);
                }
            }
        });

        productViewModel.getAddedProductList().observe(this, new Observer<List<ProductsRealm>>() {
            @Override
            public void onChanged(List<ProductsRealm> productsRealms) {
                if (addedProductListAdapter == null) {
                    addedProductListAdapter = new AddedProductListAdapter(ProductListActivity.this, productsRealms, ProductListActivity.this);
                    rvAddedProductList.setAdapter(addedProductListAdapter);
                } else {
                    addedProductListAdapter.updateList(productsRealms);
                }
            }
        });

        productViewModel.getTotalsLiveData().observe(this, new Observer<Totals>() {
            @Override
            public void onChanged(Totals totals) {
                txtTotalQty.setText(String.valueOf(totals.totalQty));
                txtSubtotal.setText("Rs " + totals.subtotal);
                txtTotalTax.setText("Rs " + totals.tax);
                txtTotalAmount.setText("Rs " + totals.getTotalAmount());
            }
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
}
