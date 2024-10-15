package com.example.ishoppinglist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ishoppinglist.R;
import com.example.ishoppinglist.models.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> products;

    // Constructor que recibe la lista de productos (no pendientes)
    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.products = products;  // lista sin filtrar
    }

    // Tamanio de los productos
    @Override
    public int getCount() {
        return products.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return products.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtengo el producto en la posicion actual del adaptador
        Product product = getItem(position);

        // Inflo la vista solo si convertView es null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        // Busco el textview donde se mostrara el nombre del producto
        TextView tvNameProd = convertView.findViewById(R.id.tvNameProd);
        tvNameProd.setText(product.getName());

        // Devuelvo la vista para el producto en la lista
        return convertView;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtengo el producto en la posicion actual del adaptador
        Product product = getItem(position);

        // Inflo la vista solo si convertView es null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        // Busco los textviews donde se mostraran el nombre y el estado del producto
        TextView tvNameProd = convertView.findViewById(R.id.tvNameProd);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        // Verifico si el producto no es nulo antes de acceder a sus propiedades
        if (product != null) {

            tvNameProd.setText(product.getName());

            tvStatus.setText(product.isStatus() ? "No Pendiente" : "Pendiente");
        }

        // Devuelvo la vista
        return convertView;
    }
}
