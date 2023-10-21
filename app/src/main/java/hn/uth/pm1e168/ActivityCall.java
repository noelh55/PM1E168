package hn.uth.pm1e168;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hn.uth.pm1e168.Models.Personas;
import hn.uth.pm1e168.configuracion.SQLiteConexion;
import hn.uth.pm1e168.configuracion.Transacciones;

public class ActivityCall extends AppCompatActivity {

    SQLiteConexion conexion;
    Spinner combopersonas;
    EditText telefono;
    private Button botonLlamar;

    ArrayList<Personas> listperson;

    ArrayList<String> ArregloPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        conexion =  new SQLiteConexion(this, Transacciones.namedb, null, 1);
        telefono = (EditText) findViewById(R.id.cbNumero);
        botonLlamar = findViewById(R.id.btnShowDialog);

        GetPersons();

        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Número de teléfono que deseas llamar
                String numeroTelefono = "97010355";

                // Crear un intent para realizar una llamada telefónica
                Intent intentLlamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numeroTelefono));
                startActivity(intentLlamar);
            }
        });
    }
    private void GetPersons()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        listperson = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery(Transacciones.SelectTablePersonas,null);
        while(cursor.moveToNext())
        {
            person = new Personas();
            person.setTelefono(cursor.getInt(0));

            listperson.add(person);
        }

        cursor.close();
        FillCombo();
    }

    private void FillCombo()
    {
        ArregloPersonas = new ArrayList<String>();

        for(int i = 0; i < listperson.size(); i++)
        {
            //ArregloPersonas.add(listperson.get(i).getTelefono() );
        }
    }
}