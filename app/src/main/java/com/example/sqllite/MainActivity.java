package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqllite.dp.dpHelper;

public class MainActivity extends AppCompatActivity {

    Button btnCrear;
    private EditText etCodigo,etDescripcion,etPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = (EditText) findViewById(R.id.codeProduct);
        etDescripcion = (EditText) findViewById(R.id.descriptionProduct);
        etPrecio = (EditText) findViewById(R.id.precioProducto);

    }

    public void Registrar (View view) {
        dpHelper dp = new dpHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = dp.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            baseDatos.insert("articulos", null, registro);

            baseDatos.close();

            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

        }

    }

    public void Buscar (View view) {
        dpHelper dp = new dpHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = dp.getWritableDatabase();

        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = baseDatos.rawQuery
                    ("SELECT descripcion, precio from articulos WHERE codigo =" + codigo, null);

            if (fila.moveToFirst()) {
                etDescripcion.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));
                baseDatos.close();
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
                baseDatos.close();
            }
        } else {
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_LONG).show();
        }
    }

    public void Modificar (View view) {
        dpHelper dp = new dpHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = dp.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = baseDatos.update("articulos", registro, "codigo=" + codigo, null );
            baseDatos.close();

            if (cantidad == 1) {
                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar (View view) {
        dpHelper dp = new dpHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = dp.getWritableDatabase();

        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()) {
            int cantidad = baseDatos.delete("articulos", "codigo=" + codigo, null);
            baseDatos.close();
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            if (cantidad == 1){
                Toast.makeText(this, "Articulo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Debes de introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

}