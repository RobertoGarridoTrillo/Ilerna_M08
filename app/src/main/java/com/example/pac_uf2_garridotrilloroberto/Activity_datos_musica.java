package com.example.pac_uf2_garridotrilloroberto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Activity_datos_musica extends AppCompatActivity {

    // campos de clase
    private TextView nombre, apellidos, usuario, password, email;
    private Button botonMusica;
    private static boolean onOffMusica = false; // estado de la musica (true = sonando)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_musica);
        BotonesEscucha();
        MostrarDatos();
    }

    /**
     * Pone los botones a la escucha y enciende y apaga la música
     * la cual la usare como un servicio
     */
    private void BotonesEscucha() {
        // identifico los botones
        nombre = findViewById(R.id.textNombreRecibido);
        apellidos = findViewById(R.id.textoApellidoRecibido);
        usuario = findViewById(R.id.textoUsuarioRecibido);
        password = findViewById(R.id.textoPasswordRecibido);
        email = findViewById(R.id.textoEmailRecibido);
        botonMusica = findViewById(R.id.botonEnviar);

        // pongo el botonMusica a la escucha. LLama o los metodos enciendeMusica() y apagaMusica()
        botonMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOffMusica) apagaMusica();
                else enciendeMusica();
            }
        });
    }


    /**
     *  Cambia el texto del boton, crea un Intent, comienza el servicio asincrono de musica
     *  cambia la varible onOffMusica de estado
     */
    public void enciendeMusica() {
        botonMusica.setText(R.string.musicaOff);
        Intent servicioMusica = new Intent( this, Servicio_musica.class);
        startService(servicioMusica); // comenzamos el servicio
        onOffMusica = !onOffMusica; // convertimos la variable en su opuesto
    }


    /**
     *  Cambia el texto del boton, crea un Intent, apaga el servicio asincrono de musica
     *  cambia la varible onOffMusica de estado
     */
    public void apagaMusica(){
        botonMusica.setText(R.string.musicaOn);
        Intent servicioMusica = new Intent(this, Servicio_musica.class);
        stopService(servicioMusica); // comenzamos el servicio
        onOffMusica = !onOffMusica; // convertimos la variable en su opuesto
    }


    /**
     * Este método recoge en una variable de tipo Usuario los datos mandados en un bundle.
     * Luego identifica los TextView y mete los datos.
     */
    private void MostrarDatos() {
        // Recogemos los
        Usuario user = (Usuario) Objects.requireNonNull(
                getIntent().getExtras()).getSerializable("usuario");

        assert user != null;
        nombre.setText(user.getNombre());
        apellidos.setText(user.getApellidos());
        usuario.setText(user.getUsuario());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
    }
}