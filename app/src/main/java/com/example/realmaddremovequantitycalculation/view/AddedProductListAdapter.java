package com.example.realmaddremovequantitycalculation.view;

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

import java.util.ArrayList;
import java.util.List;

public class AddedProductListAdapter extends RecyclerView.Adapter<AddedProductListAdapter.ViewHolder> {
    private Context context;
    private List<ProductsRealm> addedProducts;
    private ProductListenerInterface listener;

    public AddedProductListAdapter(Context context, List<ProductsRealm> addedProducts, ProductListenerInterface listener) {
        this.context = context;
        this.addedProducts = addedProducts;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recycler_product_added_list, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductsRealm product = addedProducts.get(position);

        holder.imgProduct.setImageResource(product.getProductImage());
        holder.txtProductName.setText(product.getProductName());
        holder.txtQuantity.setText("Qty: " + product.getQuantity());

        holder.imgProductDelete.setOnClickListener(view -> {
            listener.onProductDelete(product.getProductUUID(), holder.getAdapterPosition());
        });
    }



    @Override
    public int getItemCount() {
        return addedProducts.size();
    }

    public void updateList(List<ProductsRealm> newList) {
        this.addedProducts = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView txtProductName, txtQuantity;
        public AppCompatImageView imgProductDelete, imgProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtProductName = itemView.findViewById(R.id.txt_added_product_name);
            this.txtQuantity = itemView.findViewById(R.id.tetx_quanity);
            this.imgProduct = itemView.findViewById(R.id.added_image);
            this.imgProductDelete = itemView.findViewById(R.id.img_product_delete);
        }
    }
}