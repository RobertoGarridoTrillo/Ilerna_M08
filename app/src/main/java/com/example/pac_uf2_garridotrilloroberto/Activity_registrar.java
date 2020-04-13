package com.example.pac_uf2_garridotrilloroberto;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_registrar extends AppCompatActivity implements BaseColumns {

    // Campos de clase
    private EditText nombre, apellidos, usuario, password, email;
    private SQLiteDatabase db;

    /**
     * metodo onCreate. Aqui instancio la clase helper de la base de datos
     * Tanbien identifico los EditText y los pongo a la escuche
     * ol igual que el boton de enviar
     *
     * @param savedInstanceState
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // primero creamos una instancio de la clase helper
        BBDD_helper helper = new BBDD_helper(this);

        // creo o abro la base de datos
        db = helper.getWritableDatabase();

        // identificamos los views de la actividad
        nombre = findViewById(R.id.textNombre);
        apellidos = findViewById(R.id.textoApellido);
        usuario = findViewById(R.id.textoUsuario);
        password = findViewById(R.id.textoPassword);
        email = findViewById(R.id.textoEmail);
        Button botonEnviar = findViewById(R.id.botonEnviar);


        // ponemos los "not null" a la escucha
        nombre.setOnTouchListener(new PonerTransparente(nombre));
        usuario.setOnTouchListener(new PonerTransparente(usuario));
        password.setOnTouchListener(new PonerTransparente(password));

        // Ponemos el boton de Enviar a la escucha
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llamo al metodo validacion, si va bien llamo al metodo insertar
                if (validacion()) insertarBBDD();
            }
        });
    }

    /**
     * Esta clase interna es de apoyo a los listener
     */
    private static class PonerTransparente implements View.OnTouchListener{
        // campos de clase
        private EditText campo;
        // constructor
        PonerTransparente(EditText campo) {
            this.campo = campo;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            campo.setBackgroundColor(Color.TRANSPARENT);
            v.performClick();
            return false;
        }
    }

    /**
     * llamado cuando la actividad se pausa (al girar la pantalla o empezar de nuevo)
     */
    protected void onPause() {
        super.onPause();
        // Creamos un objeto SharedPreferences
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        // Lo hacemos editable
        SharedPreferences.Editor miEditor = datos.edit();
        // Establecemos la información a editar
        miEditor.putString("nombre", nombre.getText().toString());
        miEditor.putString("apellidos", apellidos.getText().toString());
        miEditor.putString("usuario", usuario.getText().toString());
        miEditor.putString("password", password.getText().toString());
        miEditor.putString("email", email.getText().toString());
        // Transferir la información al objeto SharedPreferences
        miEditor.apply();
    }

    /**
     * llamado cuando la actividad se resume (al girar la pantalla o empezar de nuevo)
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Creamos un objeto SharedPreferences
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        // Establecemos la información a recoger
        nombre.setText(datos.getString("nombre", ""));
        apellidos.setText(datos.getString("apellidos", ""));
        usuario.setText(datos.getString("usuario", ""));
        password.setText(datos.getString("password", ""));
        email.setText(datos.getString("email", ""));
    }

    /**
     * compruebo que los campos nombre, usuario y password no esten en blanco
     *
     * @return False si hay un error, True si es correcto
     */
    private boolean validacion() {
        boolean salir = true;
        if (nombre.getText().toString().equals("")) {
            toast("El valor del nombre no puede ser nulo");
            nombre.setBackgroundColor(Color.argb(125, 255, 0, 0));
            salir = false;
        }
        if (usuario.getText().toString().equals("")) {
            toast("El valor del usuario no puede ser nulo");
            usuario.setBackgroundColor(Color.argb(125, 255, 0, 0));
            salir = false;
        }
        if (password.getText().toString().equals("")) {
            toast("El valor del password no puede ser nulo");
            password.setBackgroundColor(Color.argb(125, 255, 0, 0));
            salir = false;
        }
        return salir;
    }


    /**
     * Si el metodo Validacion() devuelve True, inserto en la base de datos el usuario
     */
    private void insertarBBDD() {
        // hago una consulta para comprobar que no está ya registrado
        // si no está lo inserto en la base de datos
        BBDD_buscar buscar = new BBDD_buscar();

        if (buscar.buscarCampo(db, usuario, password, BBDD_buscar.ORIGEN_REGISTRAR) == null) {
            // Creo un nuevo mapa de valores, donde el nombre de las columnas son las claves
            ContentValues values = new ContentValues();
            // values.put(BaseColumns._ID, "");
            values.put(BBDD_estructura.COLUMNA_NOMBRE, nombre.getText().toString());
            values.put(BBDD_estructura.COLUMNA_APELLIDOS, apellidos.getText().toString());
            values.put(BBDD_estructura.COLUMNA_USUARIO, usuario.getText().toString());
            values.put(BBDD_estructura.COLUMNA_PASSWORD, password.getText().toString());
            values.put(BBDD_estructura.COLUMNA_EMAIL, email.getText().toString());

            // inserto el registro en la base de datos
            long resultado;
            resultado = db.insert(BBDD_estructura.TABLA_CLIENTES, null, values);
            if (resultado == -1) {
                Toast.makeText(this, "Fallo al  guardar el registro: " +
                        resultado, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro guardado con exito en: " +
                        resultado, Toast.LENGTH_SHORT).show();
                nombre.setText("");
                apellidos.setText("");
                usuario.setText("");
                password.setText("");
                email.setText("");

                Intent i = new Intent(Activity_registrar.this, Activity_inicio.class);
                startActivity(i);
            }
        } else {
            Toast.makeText(this, "Ya existe un cliente con ese usuario",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Crea un mensaje emergente tipo Toast
     *
     * @param s El String a mostrar
     */
    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /**
     * Cierra la base de datos al destruir la actividad
     */
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}

