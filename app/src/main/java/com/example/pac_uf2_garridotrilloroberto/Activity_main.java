package com.example.pac_uf2_garridotrilloroberto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Activity_main extends AppCompatActivity implements IComunicacion {

    private int fragmentEnUso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (fragmentEnUso == 0) {
            reemplazarFragmento(R.id.contenedorInicio, new fragment_home());
            fragmentEnUso = 0;
        }
    }

    /**
     * llamado cuando la actividad se resume (al girar la pantalla o empezar de nuevo)
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle estado) {
        super.onRestoreInstanceState(estado);
        // leemos el boolean de estado
        fragmentEnUso = estado.getInt("fragmentEnUso", 0);

        // colocamos el fragento segun el boolea fragmentoEnUso
        // false se esta mostrando fragment_home
        if (fragmentEnUso==0) {
            reemplazarFragmento(R.id.contenedorInicio, new fragment_home());
            fragmentEnUso = 0;
        } else {
            reemplazarFragmento(R.id.contenedorInicio, new fragment_registrarse_entrar());
            fragmentEnUso = 1;
        }
    }

    /**
     * llamado cuando la actividad se pausa (al girar la pantalla o empezar de nuevo)
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle estado) {
        // Creamos un objeto SharedPreferences
        estado.putInt("fragmentEnUso", fragmentEnUso);
        super.onSaveInstanceState(estado);

    }

    /**
     * metodo implementeado de la interface IComunicacion que usa para comunicar del fragmento
     * a la actividad
     *
     * @param clase la actividad que va a abrir
     */
    @Override
    public void pulsarBoton(Class clase) {
        Intent i = new Intent(Activity_main.this, clase);
        startActivity(i);
    }

    /**
     * Usando una transicion de fragmento, al presionar el boton de iniciar cambio un fragmento por
     * otro.
     *
     * @param contenedor contenedor donde alojamos los fragmentos.
     *                   id del contenedor (en este caso R.id.contenedorInicio)
     * @param fragmento  el fragmento a sustituir
     *                   (en este caso fragment_registrarse_entrar o fragment_home)
     */
    public void reemplazarFragmento(int contenedor, Fragment fragmento) {

        // Crea el nuevo fragmento y la transacción.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // cambio el fragmento
        ft.replace(contenedor, fragmento);
        // añado el fragmento a la a la pila de la aplicación para que retroceda
        // ft.addToBackStack(null);
        // Commit a la transacción
        ft.commit();
        fragmentEnUso = fragmentEnUso==0 ? 1 : 0;

    }
}
