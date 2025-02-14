package com.example.realmaddremovequantitycalculation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmaddremovequantitycalculation.Interfaces.ProductListenerInterface;
import com.example.realmaddremovequantitycalculation.R;
import com.example.realmaddremovequantitycalculation.RealmClasses.ProductsRealm;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private List<ProductsRealm> productsRealm;
    private ProductListenerInterface productListenerInterface;


    public ProductListAdapter(Context context, List<ProductsRealm> productsRealm, ProductListenerInterface productListenerInterface) {
        this.context = context;
        this.productsRealm = productsRealm;
        this.productListenerInterface = productListenerInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_product_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ProductsRealm productRealm = productsRealm.get(holder.getAdapterPosition());
        holder.imgProduct.setImageResource(productRealm.getProductImage());
        holder.imgProductAdd.setImageResource(productRealm.getProductAdd());
        holder.txtProductName.setText(productRealm.getProductName());
        holder.txtProductTax.setText(productRealm.getProductTaxRate() + "%");
        holder.txtProductPrice.setText("Rs " + productRealm.getProductPrice());

        holder.imgProductAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListenerInterface.onProductAdd(productRealm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsRealm.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView txtProductName, txtProductTax, txtProductPrice;
        public AppCompatImageView imgProductAdd, imgProduct;


        public ViewHolder(View itemView) {
            super(itemView);
            this.txtProductName = (AppCompatTextView) itemView.findViewById(R.id.txt_product_name);
            this.imgProductAdd = (AppCompatImageView) itemView.findViewById(R.id.img_product_add);
            this.txtProductTax = (AppCompatTextView) itemView.findViewById(R.id.txt_product_tax);
            this.txtProductPrice = (AppCompatTextView) itemView.findViewById(R.id.txt_product_price);
            this.imgProduct = (AppCompatImageView) itemView.findViewById(R.id.image);
        }
    }
}
