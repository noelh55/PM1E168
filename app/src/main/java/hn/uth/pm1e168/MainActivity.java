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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private String[] data = {"Elemento 1", "Elemento 2", "Elemento 3"};
    private Button compartirButton;
    ArrayList<String> ArregloPersonas;
    //String seleccion = getIntent().getStringExtra("seleccion");

    private ImageView imageView;
    private Button mostrarImagenButton;
    private Uri uriDeLaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        mostrarImagenButton = findViewById(R.id.btnVer);

        //ListView listView = findViewById(R.id.listpersonas); // Tu ListView
       // ArrayList<String> listaItems = new ArrayList<>();
        //listaItems.add(seleccion); // Agregar la selección al ListView
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaItems);
        //listView.setAdapter(adapter);
        listView = findViewById(R.id.listpersonas);
        Button deleteButton = findViewById(R.id.btnDelete);
        compartirButton = findViewById(R.id.btncompartir);
        Button actualizarButton = findViewById(R.id.btnactualizar);

        dataList = new ArrayList<>();
        dataList.add("Elemento 1");
        dataList.add("Elemento 2");
        dataList.add("Elemento 3");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

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

        mostrarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriDeLaImagen != null) {
                    imageView.setImageURI(uriDeLaImagen);
                }
            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Supongamos que deseas actualizar el segundo elemento
                if (dataList.size() > 1) {
                    actualizarElemento(1, "Elemento Actualizado"); // Indice 1 para el segundo elemento
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supongamos que deseas eliminar el primer elemento
                if (!dataList.isEmpty()) {
                    dataList.remove(0);
                    adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elementoSeleccionado = data[position];
            }
        });

        compartirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.length > 0) {
                    compartirElemento(data[0]);
                }
            }
        });

        ListView listView = findViewById(R.id.listpersonas);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Crear un AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Marcar");
                builder.setMessage("¿Deseas marcar a esta persona?");

                // Agregar botón "Sí"
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent = new Intent(MainActivity.this, ActivityCall.class);
                        Intent intent = new Intent(getApplicationContext(), ActivityCall.class);
                        startActivity(intent);
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

    private void actualizarElemento(int indice, String nuevoValor) {
        if (indice >= 0 && indice < dataList.size()) {
            dataList.set(indice, nuevoValor);
            adapter.notifyDataSetChanged();
        }
    }

    private void compartirElemento(String texto) {
        Intent compartirIntent = new Intent(Intent.ACTION_SEND);
        compartirIntent.setType("text/plain");
        compartirIntent.putExtra(Intent.EXTRA_TEXT, texto);

        startActivity(Intent.createChooser(compartirIntent, "Compartir vía"));
    }
}