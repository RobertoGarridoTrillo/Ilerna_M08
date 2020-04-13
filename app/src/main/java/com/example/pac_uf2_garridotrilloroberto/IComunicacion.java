package com.example.pac_uf2_garridotrilloroberto;

import android.widget.Button;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface IComunicacion {
    /**
     * Este metodo sirve para comunicar un fragmento con la actividad.
     * Recibe un fragmento para sustituirlo y el id del Layout contenedor.
     * Para llamar a este metodo desde el fragmento podemos hacerlo asi:
     * <p>
     * ((IComunicacion)getActivity()).reemplazarFragmento(R.id.contenedorInicio, new fragment_registrarse_entrar());
     * </p>
     *
     * @param fragment        fragento que queremos sustituir
     * @param containerViewId id del layout contenedor
     */
    void reemplazarFragmento(@IdRes int containerViewId, @NonNull Fragment fragment);

    /**
     * Al pulsar el boton de registrar o entrar envia el boton desde el fragmento donde está
     * a la actividad. Lo hago porque desde el fragmento no puedo llamar a otra actividad
     *
     * @param clase  la actividad que va a abrir
     */
    void pulsarBoton(Class clase);
}
