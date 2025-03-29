package com.example.realmaddremovequantitycalculation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmaddremovequantitycalculation.Interfaces.ProductListenerInterface;
import com.example.realmaddremovequantitycalculation.R;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<ProductsRealm> productsRealm;
    private ProductListenerInterface productListenerInterface;

    public ProductListAdapter(List<ProductsRealm> productsRealm, ProductListenerInterface productListenerInterface) {
        this.productsRealm = productsRealm;
        this.productListenerInterface = productListenerInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_product_details, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductsRealm product = productsRealm.get(position);

        holder.imgProduct.setImageResource(product.getProductImage());
        holder.imgProductAdd.setImageResource(product.getProductAdd());
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductTax.setText(product.getProductTaxRate() + "%");
        holder.txtProductPrice.setText("Rs " + product.getProductPrice());

        holder.imgProductAdd.setOnClickListener(view -> {
            ProductsRealm clickedProduct = productsRealm.get(position);
            productListenerInterface.onProductAdd(clickedProduct);
        });
    }
    @Override
    public int getItemCount() {
        return productsRealm.size();
    }

    public void updateList(List<ProductsRealm> newList) {
        this.productsRealm = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductTax, txtProductPrice;
        public AppCompatImageView imgProductAdd, imgProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtProductName = itemView.findViewById(R.id.txt_product_name);
            this.imgProductAdd = itemView.findViewById(R.id.img_product_add);
            this.txtProductTax = itemView.findViewById(R.id.txt_product_tax);
            this.txtProductPrice = itemView.findViewById(R.id.txt_product_price);
            this.imgProduct = itemView.findViewById(R.id.image);
        }
    }
}