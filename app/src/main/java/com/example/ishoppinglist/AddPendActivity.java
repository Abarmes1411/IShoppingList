package com.example.ishoppinglist;

import static com.example.ishoppinglist.database.Database.productList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishoppinglist.adapters.ProductAdapter;
import com.example.ishoppinglist.database.Database;
import com.example.ishoppinglist.models.Product;

import java.util.List;

public class AddPendActivity extends AppCompatActivity {

    private TextView tvNamenotPend, tvNotenotPend;
    private Spinner spNoPend;
    private Button btnChangeToPend, btnCancelAddPend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Recojo los componentes de la vista
        spNoPend = findViewById(R.id.spNoPend);
        btnChangeToPend = findViewById(R.id.btnChangeToPend);
        btnCancelAddPend = findViewById(R.id.btnCancelAddPend);


        // Inicializo la lista de productos no pendientes
        Database.initnonPendingList();

        // Creo y asigno el adaptador para los productos no pendientes
        ProductAdapter productAdapter = new ProductAdapter(AddPendActivity.this, 0, Database.nonPendingProductList);
        spNoPend.setAdapter(productAdapter);

        // Hago el listener para cuando el user haga click en cancelar
        btnCancelAddPend.setOnClickListener(v -> {
            Intent intentCancelAddPend = new Intent(AddPendActivity.this, MainActivity.class);
            startActivity(intentCancelAddPend);
        });

        // Hago el listener para cuando el user haga click en cambiar estado
        btnChangeToPend.setOnClickListener(v -> {
            Product product = (Product) spNoPend.getSelectedItem();

            if (product != null) {
                // Cambio el estado del producto a pendiente
                product.setStatus(true);

                // Actualizo la lista de productos no pendientes en la base de datos
                Database.initnonPendingList();

                // Regreso a la mainactivity
                Intent intent = new Intent(AddPendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}