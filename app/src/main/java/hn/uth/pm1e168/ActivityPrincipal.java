package hn.uth.pm1e168;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hn.uth.pm1e168.configuracion.SQLiteConexion;
import hn.uth.pm1e168.configuracion.Transacciones;

public class ActivityPrincipal extends AppCompatActivity {

    EditText nombre, telefono, nota, pais;
    private EditText num1EditText, num2EditText, num3EditText;
    Button btnSave;
    Button btnAd;

    static final int peticion_acceso_camara = 101;
    static final int peticion_toma_fotografica = 102;
    String currentPhotoPath;
    ImageView imageView;
    Button btntakefoto;
    String pathfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnAd = (Button) findViewById(R.id.btnAd);

        num1EditText = findViewById(R.id.txtnombre);
        num2EditText = findViewById(R.id.txtTelefono);
        num3EditText = findViewById(R.id.txtnota);

        nombre = (EditText) findViewById(R.id.txtnombre);
        telefono = (EditText)findViewById(R.id.txtTelefono);
        nota = (EditText)findViewById(R.id.txtnota);
        //pais = (EditText)findViewById(R.id.spinner);

        Button btnContactos = findViewById(R.id.btnContactos);
        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Spinner spinner = findViewById(R.id.spinner);

        // Datos para llenar el Spinner
        String[] datos = {"Pais","Honduras", "Costa Rica", "Guatemala", "El Salvador"};

        // Crear un adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        spinner.setAdapter(adapter);

        // Establecer el valor por defecto (por ejemplo, "Opción 2")
        int defaultValueIndex = 0; // El índice de la opción que deseas establecer como predeterminada
        spinner.setSelection(defaultValueIndex);


        imageView = (ImageView)findViewById(R.id.imageView);
        btntakefoto = (Button) findViewById(R.id.btntakefoto);

        btntakefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
            }
        });

        //pais = (Spinner) findViewById(R.id.spinner);
        nombre = (EditText) findViewById(R.id.txtnombre);
        telefono = (EditText)findViewById(R.id.txtTelefono);
        nota = (EditText)findViewById(R.id.txtnota);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num1EditText.getText().toString().isEmpty()) {
                    mostrarAlertDialog("Campo Nombre está vacío");
                } else if (num2EditText.getText().toString().isEmpty()) {
                    mostrarAlertDialog("Campo Teléfono está vacío");
                } else if ( num3EditText.getText().toString().isEmpty()) {
                    mostrarAlertDialog("Campo Nota está vacío");
                } else {
                AddPerson();}
            }
        });

        btnAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityPais.class);
                startActivity(intent);
            }
        });
    }

    private void AddPerson() {
            try {
                SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.namedb, null, 1);
                SQLiteDatabase db = conexion.getWritableDatabase();

                ContentValues valores = new ContentValues();
                //valores.put(Transacciones.pais, pais.getText().toString());
                valores.put(Transacciones.nombre, nombre.getText().toString());
                valores.put(Transacciones.telefono, telefono.getText().toString());
                valores.put(Transacciones.nota, nota.getText().toString());

                Long Result = db.insert(Transacciones.Tabla, Transacciones.id, valores);

                String resultado = String.valueOf(telefono);

                // Enviar el resultado a la segunda actividad
                Intent intent = new Intent(ActivityPrincipal.this, ActivityCall.class);
                intent.putExtra("resultado", resultado);
                startActivity(intent);

                Toast.makeText(this, getString(R.string.Respuesta), Toast.LENGTH_SHORT).show();
                db.close();
            } catch (Exception exception) {
                Toast.makeText(this, getString(R.string.ErrorResp), Toast.LENGTH_SHORT).show();
            }
    }

    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},peticion_acceso_camara);
        }
        else
        {
            //TomarFoto();
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //TomarFoto();
                dispatchTakePictureIntent();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void TomarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intent, peticion_toma_fotografica);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pm013p2023.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, peticion_toma_fotografica);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == peticion_toma_fotografica && resultCode == RESULT_OK)
        {
            /*
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            imageView.setImageBitmap(image);
             */
            try
            {
                File foto = new File(currentPhotoPath);
                imageView.setImageURI(Uri.fromFile(foto));
            }
            catch (Exception ex)
            {
                ex.toString();
            }
        }
    }

    private void mostrarAlertDialog(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta");
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}