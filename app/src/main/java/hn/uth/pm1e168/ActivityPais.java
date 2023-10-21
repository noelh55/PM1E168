package hn.uth.pm1e168;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hn.uth.pm1e168.configuracion.SQLiteConexion;
import hn.uth.pm1e168.configuracion.Transacciones;

public class ActivityPais extends AppCompatActivity {

    private EditText num1EditText;
    private EditText campoTexto;
    private Button botonGuardar;
    private EditText editText;
    private Button guardarButton;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        campoTexto = findViewById(R.id.txtPa);
        botonGuardar = findViewById(R.id.btnAdd);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {AddPerson();}
        });
    }

    private void AddPerson() {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.namedb, null,1);
            SQLiteDatabase db =  conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(Transacciones.campoTexto, campoTexto.getText().toString());

            Long Result = db.insert(Transacciones.Tabla, Transacciones.id, valores);

            Toast.makeText(this, getString(R.string.Respuesta), Toast.LENGTH_SHORT).show();
            db.close();
        }
        catch (Exception exception)
        {
            Toast.makeText(this, getString(R.string.ErrorResp), Toast.LENGTH_SHORT).show();
        }
    }
}