package com.example.realmaddremovequantitycalculation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

public class AddedProductListAdapter extends RecyclerView.Adapter<AddedProductListAdapter.ViewHolder> {
    private Context context;
    private List<ProductsRealm> products;
    private ProductListenerInterface productListenerInterface;

    public AddedProductListAdapter(Context context, List<ProductsRealm> products, ProductListenerInterface productListenerInterface) {
        this.context = context;
        this.products = products;
        this.productListenerInterface = productListenerInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recycler_product_added_list, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ProductsRealm productRealm = products.get(holder.getAdapterPosition());

        holder.imgProduct.setImageResource(productRealm.getProductImage());
        holder.txtProductName.setText(productRealm.getProductName());
        holder.txtQuantity.setText("Qty: " + productRealm.getQuantity()); // Display quantity

        holder.imgProductDelete.setOnClickListener(view -> {
            productListenerInterface.onProductDelete(productRealm.getProductUUID(), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void deleteUser(int position) {
        if (position >= 0 && position < products.size()) {
            products.remove(position);
            notifyItemRemoved(position);
        } else {
            Log.e("AddedProductListAdapter", "Invalid position: " + position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView txtProductName, txtQuantity;
        public AppCompatImageView imgProductDelete, imgProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtProductName = itemView.findViewById(R.id.txt_added_product_name);
            this.txtQuantity = itemView.findViewById(R.id.tetx_quanity); // Add the quantity text view in XML
            this.imgProduct = itemView.findViewById(R.id.added_image);
            this.imgProductDelete = itemView.findViewById(R.id.img_product_delete);
        }
    }
}
