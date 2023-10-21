package hn.uth.pm1e168;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hn.uth.pm1e168.configuracion.SQLiteConexion;
import hn.uth.pm1e168.configuracion.Transacciones;

public class ActivityPais extends AppCompatActivity {

    private EditText num1EditText;
    private EditText campoTexto;
    private Button botonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        campoTexto = findViewById(R.id.txtPa);
        botonGuardar = findViewById(R.id.btnAdd);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el texto del campo de entrada
                String texto = campoTexto.getText().toString();

                if (texto.isEmpty()) {
                    // Mostrar un Toast indicando que el campo está vacío
                    Toast.makeText(ActivityPais.this, "El campo está vacío", Toast.LENGTH_SHORT).show();
                } else {
                }
            }
        });
    }
}