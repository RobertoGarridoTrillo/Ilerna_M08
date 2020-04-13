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
public class fragment_home extends Fragment {

    public fragment_home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button botonInicio = view.findViewById(R.id.botonInicio);

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IComunicacion) Objects.requireNonNull(getActivity()))
                        .reemplazarFragmento(R.id.contenedorInicio,
                        new fragment_registrarse_entrar());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
