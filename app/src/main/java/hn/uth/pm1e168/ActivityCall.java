package hn.uth.pm1e168;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnShowDialog = findViewById(R.id.btnShowDialog);
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCall.this);
                builder.setTitle("Llamada");
                builder.setMessage("¿Seguro deseas llamar?");

                // Agregar botón "Sí"
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Número de teléfono al que se realizará la llamada
                        String numero = "97010355"; // Reemplaza con el número deseado

                        // Crear una intención para la acción de llamada
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numero));

                        // Iniciar la intención para realizar la llamada
                        startActivity(intent);
                        dialog.dismiss(); // Cerrar el diálogo
                    }
                });

                // Agregar botón "No"
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar cuando se hace clic en "No"
                        dialog.dismiss(); // Cerrar el diálogo
                    }
                });

                // Mostrar el diálogo
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}