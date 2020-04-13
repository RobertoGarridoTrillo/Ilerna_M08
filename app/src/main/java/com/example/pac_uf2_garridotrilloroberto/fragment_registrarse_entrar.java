package com.example.pac_uf2_garridotrilloroberto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_registrarse_entrar extends Fragment {

    // campos de clase
    private Button botonRegistrar;
    private Button botonEntrar;

    public fragment_registrarse_entrar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // creo la variable de tipo vista del fragmento donde estoy
        View registrarseEntrar = inflater.inflate(R.layout.fragment_registrarse_entrar,
                container, false);
        // inicio las variables donde guardo los botones de entrar y registrarse
        botonRegistrar = registrarseEntrar.findViewById(R.id.Registrar);
        botonEntrar = registrarseEntrar.findViewById(R.id.botonEntrar);

        // pongo los botones entrar y registrarse a la escucha
        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// envio el boton a la actividad
                ((IComunicacion) Objects.requireNonNull(getActivity()))
                        .pulsarBoton(Activity_inicio.class);
            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// envio el boton a la actividad
                ((IComunicacion) Objects.requireNonNull(getActivity()))
                        .pulsarBoton(Activity_registrar.class);
            }
        });
        // Inflate the layout for this fragment
        return registrarseEntrar;
    }
}
