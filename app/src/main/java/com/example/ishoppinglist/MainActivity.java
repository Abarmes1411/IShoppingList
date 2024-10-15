package com.example.ishoppinglist;

import static com.example.ishoppinglist.database.Database.initList;
import static com.example.ishoppinglist.database.Database.productList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishoppinglist.adapters.ProductAdapter;
import com.example.ishoppinglist.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvProducts;
    private List<Product> pendingProducts;
    private Button btnAddPends, btnAddProd;
    static List<Product> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recojo el componente del listview
        lvProducts = findViewById(R.id.lvProducts);
        btnAddPends = findViewById(R.id.btnAddPends);
        btnAddProd = findViewById(R.id.btnAddProd);

        // Inicializo el metodo de la Database
        initList();

        // Filtro los productos pendientes con el meotodo que he creado
        pendingProducts = filterPendingProducts(productList);

        // Creo el adaptador con la lista filtrada de productos pendientes
        ProductAdapter adapter = new ProductAdapter(MainActivity.this, 0, pendingProducts);

        // Asigno el adaptador al listview
        lvProducts.setAdapter(adapter);

        // Hago el listener para cuando el user haga click
        lvProducts.setOnItemClickListener((parent, view, position, id) -> {


            Product p = pendingProducts.get(position);

            Log.i("click en el producto", p.toString());

            Intent detailedIntent = new Intent(MainActivity.this, DetailedActivity.class);
            detailedIntent.putExtra("product", p);
            startActivity(detailedIntent);
        });

        // Hago el listener para cuando el user haga click en aniadir pendientes
        btnAddPends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbtnAddPends = new Intent(MainActivity.this, AddPendActivity.class);

                startActivity(intentbtnAddPends);
            }
        });

        // Hago el listener para cuando el user haga click en aniadir un producto
        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbtnAddProd = new Intent(MainActivity.this, AddProdActivity.class);

                startActivity(intentbtnAddProd);
            }
        });

    }

    //El metodo onResume() se encarga de que al volver a la pagina, se actualice el listview con los productos pendientes
    @Override
    protected void onResume() {
        super.onResume();

        // Vuelvo a cargar los datos de los productos pendientes
        pendingProducts = filterPendingProducts(productList);

        // Aviso al adapter que se ha actualizado la lista
        ProductAdapter adapter = new ProductAdapter(MainActivity.this, 0, pendingProducts);
        lvProducts.setAdapter(adapter);
    }

    // Metodo que uso para filtrar los productos pendientes de comprar
    private List<Product> filterPendingProducts(List<Product> originalList) {
        filteredList.clear(); // Uso el .clear() para limpiar la lista y evitar repeticiones de productos
        for (Product product : originalList) {
            if (!product.isStatus()) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }



}