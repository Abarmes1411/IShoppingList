package com.example.ishoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishoppinglist.models.Product;

public class DetailedActivity extends AppCompatActivity {

    private TextView tvNameDet, tvNoteDet, tvPendDet;
    private Button btnCancel, btnEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Recojo los componentes de la vista
        tvNameDet = findViewById(R.id.tvNameDet);
        tvNoteDet = findViewById(R.id.tvNoteDet);
        tvPendDet = findViewById(R.id.tvPendDet);
        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);


        // Recupero el producto a editar desde el Intent
        Intent intent = getIntent();
        Product p = (Product) intent.getSerializableExtra("product");

        // Relleno los textviews con la info recogida
        tvNameDet.setText(p.getName());
        tvNoteDet.setText(p.getNote());
        if(!p.isStatus()){
            tvPendDet.setText("Pendiente");
        }else{
            tvPendDet.setText("No pendiente");
        }


        // Hago el listener para cuando el user haga click en cancelar
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(DetailedActivity.this, MainActivity.class);

                startActivity(intentMain);
            }
        });

        // Hago el listener para cuando el user haga click en editar producto
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditPend = new Intent(DetailedActivity.this, EditDetailActivity.class);
                intentEditPend.putExtra("product", p); // Enviar el producto para editarlo
                startActivity(intentEditPend);
            }
        });
    }
}