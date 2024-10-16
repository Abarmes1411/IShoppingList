package com.example.ishoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishoppinglist.database.Database;
import com.example.ishoppinglist.models.Product;

public class EditDetailActivity extends AppCompatActivity {

    private EditText etProdName, etProdNote;
    private Switch swProdPend, swLactosa, swGluten;
    private Button btnCancelEdit, btnSaveEdit;
    private Product productToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Recojo los componentes de la vista
        etProdName = findViewById(R.id.etProdName);
        etProdNote = findViewById(R.id.etProdNote);
        swProdPend = findViewById(R.id.swProdPend);
        swLactosa = findViewById(R.id.swLactosa);
        swGluten = findViewById(R.id.swGluten);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);

        // Recupero el producto a editar desde el Intent
        Intent intent = getIntent();
        productToEdit = (Product) intent.getSerializableExtra("product");

        // Muestro los detalles actuales del producto en los campos de edicion
        if (productToEdit != null) {
            etProdName.setText(productToEdit.getName());
            etProdNote.setText(productToEdit.getNote());
            swProdPend.setChecked(productToEdit.isStatus());
            swLactosa.setChecked(productToEdit.isLactosa());
            swGluten.setChecked(productToEdit.isGluten());
        }

        // Hago el listener para cuando el user haga click en cancelar edicion
        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancelEdit = new Intent(EditDetailActivity.this, DetailedActivity.class);
                intentCancelEdit.putExtra("product", productToEdit); // Devolver el producto sin cambios
                startActivity(intentCancelEdit);
            }
        });

        // Hago el listener para cuando el user haga click en guardar cambios
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prodName = etProdName.getText().toString();
                String prodNote = etProdNote.getText().toString();
                boolean prodStatus = swProdPend.isChecked();
                boolean prodLactosa = swLactosa.isChecked();
                boolean prodGluten = swGluten.isChecked();

                // En caso de que el nombre este vacio, salta un error
                if (prodName.isEmpty()) {
                    etProdName.setError("Se debe introducir un nombre");
                    return;
                }

                // Actualizo los atributos del producto
                productToEdit.setName(prodName);
                productToEdit.setNote(prodNote);
                productToEdit.setStatus(prodStatus);
                productToEdit.setLactosa(prodLactosa);
                productToEdit.setGluten(prodGluten);

                // Actualizo la lista de productos principal
                for (Product product : Database.productList) {
                    if (product.getId().equals(productToEdit.getId())) {
                        product.setName(prodName);
                        product.setNote(prodNote);
                        product.setStatus(prodStatus);
                        product.setLactosa(prodLactosa);
                        product.setGluten(prodGluten);
                    }
                }

                // Actualizo la lista de pendientes si es necesario
                if (!prodStatus) {
                    MainActivity.filteredList.remove(productToEdit);
                } else {
                    if (!MainActivity.filteredList.contains(productToEdit)) {
                        MainActivity.filteredList.add(productToEdit);
                    }
                }

                // Por ultimo regreso a la vista detallada del producto y envio el producto ya actualizado devuelta
                Intent intentBack = new Intent(EditDetailActivity.this, DetailedActivity.class);
                intentBack.putExtra("product", productToEdit);
                startActivity(intentBack);
            }
        });
    }
}