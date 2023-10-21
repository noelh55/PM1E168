package hn.uth.pm1e168;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hn.uth.pm1e168.Models.Personas;
import hn.uth.pm1e168.configuracion.SQLiteConexion;
import hn.uth.pm1e168.configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listView;
    ArrayList<Personas> listperson;

    ArrayList<String> ArregloPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            // Establecemos una conxion a base de datos
            conexion = new SQLiteConexion(this, Transacciones.namedb, null, 1);
            listView = (ListView) findViewById(R.id.listpersonas);
            GetPersons();

            ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloPersonas);
            listView.setAdapter(adp);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String ItemPerson = listperson.get(i).getNombre();
                    Toast.makeText(MainActivity.this, "Nombre" + ItemPerson, Toast.LENGTH_LONG).show();
                }
            });

        }
        catch (Exception ex)
        {
            ex.toString();
        }

        Button btnatras = findViewById(R.id.btnatras);
        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityPrincipal.class);
                startActivity(intent);
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
            person.setNombre(cursor.getString(0));
            person.setTelefono(cursor.getInt(1));
            person.setNota(cursor.getString(2));

            listperson.add(person);
        }

        cursor.close();
        FillList();
    }

    private void FillList()
    {
        ArregloPersonas = new ArrayList<String>();

        for(int i = 0; i < listperson.size(); i++)
        {
            ArregloPersonas.add(listperson.get(i).getNombre() + " - " +
                    listperson.get(i).getTelefono() + " - " +
                    listperson.get(i).getNota());
        }
    }
}