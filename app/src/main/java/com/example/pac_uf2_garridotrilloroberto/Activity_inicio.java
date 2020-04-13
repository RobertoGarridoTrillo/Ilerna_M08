package com.example.pac_uf2_garridotrilloroberto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_inicio extends AppCompatActivity {

    // Campos de clase
    private EditText usuario, password;
    private SQLiteDatabase db;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // primero creamos una instancio de la clase helper
        BBDD_helper helper = new BBDD_helper(this);

        // creo / abro la base de datos
        db = helper.getWritableDatabase();

        // identificamos los views de la actividad
        usuario = findViewById(R.id.textoUsuario);
        password = findViewById(R.id.textoPassword);
        Button botonEnviar = findViewById(R.id.botonEnviar);


        // ponemos los not null a la escucho
        usuario.setOnTouchListener(new PonerTransparente(usuario));
        password.setOnTouchListener(new PonerTransparente(password));

        // Ponemos el boton de Enviar a la escucha
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llamo al metodo validacion, si va bien llamo al metodo insertar
                if (validacion()) iniciarBBDD();
            }
        });
    }


    /**
     * Esta clase interna es de apoyo a los listener "not null"
     */
    private static class PonerTransparente implements View.OnTouchListener {
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
        miEditor.putString("usuario", usuario.getText().toString());
        miEditor.putString("password", password.getText().toString());
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
        usuario.setText(datos.getString("usuario", ""));
        password.setText(datos.getString("password", ""));
    }

    /**
     * compruebo que los campos nombre, usuario y password no esten en blanco
     *
     * @return False si hay un error, True si es correcto
     */
    private boolean validacion() {
        boolean salir = true;
        if (usuario.getText().toString().equals("")) {
            toast("El valor del nombre no puede ser nulo");
            usuario.setBackgroundColor(Color.argb(125, 255, 0, 0));
            salir = false;
        }
        if (password.getText().toString().equals("")) {
            toast("El valor del usuario no puede ser nulo");
            password.setBackgroundColor(Color.argb(125, 255, 0, 0));
            salir = false;
        }
        return salir;
    }


    /**
     * Si el metodo Validacion() devuelve True,
     */
    private void iniciarBBDD() {
        // hago una consulta para comprobar que no está ya registrado
        // si no está lo inserto en la base de datos
        BBDD_buscar buscar = new BBDD_buscar();
        Usuario user = buscar.buscarCampo(db, usuario, password, BBDD_buscar.ORIGEN_INICIO);
        if (user == null) {
            Toast.makeText(Activity_inicio.this,
                    "No existe un usuario con ese password", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(Activity_inicio.this, Activity_datos_musica.class);
            i.putExtra("usuario", user);
            startActivity(i);
        }
    }


    /**
     * Crea un Mensaje emergente tipo Toast
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

