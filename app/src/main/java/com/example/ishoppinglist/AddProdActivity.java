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

public class AddProdActivity extends AppCompatActivity {

    private EditText etProdNameAdd, etProdNoteAdd;
    private Switch swPendStatus;
    private Button btnSaveAdd, btnCancelAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_prod);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Recojo los componentes de la vista
        etProdNameAdd = findViewById(R.id.etProdNameAdd);
        etProdNoteAdd = findViewById(R.id.etProdNoteAdd);
        swPendStatus = findViewById(R.id.swPendStatus);
        btnSaveAdd = findViewById(R.id.btnSaveAdd);
        btnCancelAdd = findViewById(R.id.btnCancelAdd);


        Intent intent = getIntent();

        // Hago el listener para cuando el user haga click en cancelar
        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancelAddProd = new Intent(AddProdActivity.this, MainActivity.class);

                startActivity(intentCancelAddProd);
            }
        });

        // Hago el listener para cuando el user haga click en guardar el aniadido
        btnSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String prodName = etProdNameAdd.getText().toString();
                String prodNote = etProdNoteAdd.getText().toString();
                boolean prodStatus = swPendStatus.isChecked();

                // Hago que salte error si no se introduce ningun nombre
                if(prodName.isEmpty()){
                    etProdNameAdd.setError("Se debe introducir un nombre");
                    return;
                }

                // Relleno el nuevo producto con lo recogido de lo elementos
                Product newProduct = new Product();
                newProduct.setId(String.valueOf(Database.incrementID()));
                newProduct.setName(prodName);
                newProduct.setNote(prodNote);
                newProduct.setStatus(prodStatus);

                // Lo aniado a la database
                Database.productList.add(newProduct);

                //Regreso a la mainactivity
                Intent intentMain = new Intent(AddProdActivity.this, MainActivity.class);
                startActivity(intentMain);
            }
        });
    }
}