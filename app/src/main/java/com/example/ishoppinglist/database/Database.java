package com.example.ishoppinglist.database;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ishoppinglist.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Database extends AppCompatActivity {

    public static List<Product> productList;
    public static List<Product> nonPendingProductList;
    public static int lastId = 234;


    //Metodo que inicia una lista de productos predeterminados, principalmente para que no aparezca vacia
    public static void initList(){


        if(productList==null){
            productList = new ArrayList<>();


            //true significa NO PENDIENTE
            //false significa PENDIENTE
            Product p = new Product();
            p.setId("230");
            p.setName("Leche Semi-Desnatada");
            p.setNote("La mama ha dicho que hay que comprarlo. URGE.");
            p.setStatus(false);

            Product p2 = new Product();
            p2.setId("231");
            p2.setName("Pan Integral");
            p2.setNote("Comprar el de cereales.");
            p2.setStatus(true);

            Product p3 = new Product();
            p3.setId("232");
            p3.setName("Huevos");
            p3.setNote("Preferir los de gallinas camperas.");
            p3.setStatus(false);

            Product p4 = new Product();
            p4.setId("233");
            p4.setName("Jabón Líquido");
            p4.setNote("Quedan pocos, comprar una botella de 1L.");
            p4.setStatus(true);

            Product p5 = new Product();
            p5.setId("234");
            p5.setName("Papel Higiénico");
            p5.setNote("Solo quedan dos rollos.");
            p5.setStatus(false);

            productList.add(p);
            productList.add(p2);
            productList.add(p3);
            productList.add(p4);
            productList.add(p5);
        }
    }

    //Metodo con los productos que no estan pendientes, es decir cuyo status es true
    public static void initnonPendingList() {
        // Verifico si la lista principal está inicializada
        if (productList == null) {
            initList();
        }

        // Inicializo o limpio la lista de productos no pendientes
        if (nonPendingProductList == null) {
            nonPendingProductList = new ArrayList<>();
        } else {
            nonPendingProductList.clear();
        }

        // Filtro los productos NO pendientes (status == true)
        for (Product product : productList) {
            if (product.isStatus()) {  // Productos no pendientes
                nonPendingProductList.add(product);
            }
        }
    }

    //Metodo que uso para incrementar el id en referencia a los productos
    public static int incrementID(){
        lastId++;
        return lastId;
    }

}
