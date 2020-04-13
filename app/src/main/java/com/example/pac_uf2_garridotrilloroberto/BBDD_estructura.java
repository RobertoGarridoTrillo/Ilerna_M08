package com.example.pac_uf2_garridotrilloroberto;

import android.provider.BaseColumns;

class BBDD_estructura implements BaseColumns {

    static final String TABLA_CLIENTES = "Clientes";
    // Usare, por recomendacion de la API, BaseColumns._ID en lugar de crear una constante
    //public static final String _ID = "_id"; asi está en la interface
    static final String COLUMNA_NOMBRE = "Nombre";
    static final String COLUMNA_APELLIDOS = "Apellido";
    static final String COLUMNA_USUARIO = "Usuario";
    static final String COLUMNA_PASSWORD = "Password";
    static final String COLUMNA_EMAIL = "Email";
    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLA_CLIENTES;
    // métodos que creen y manntienen la base de datos y las tablas
    // en la api viene de una forma mas compacta
    private static final String NN_TEXT_TYPE = " TEXT NOT NULL";
    private static final String TEXT_TYPE = " TEXT NOT NULL";
    private static final String COMMA_SET = ",";
    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLA_CLIENTES + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNA_NOMBRE + " " + NN_TEXT_TYPE + COMMA_SET +
                    COLUMNA_APELLIDOS + " " + TEXT_TYPE + COMMA_SET +
                    COLUMNA_USUARIO + " " + NN_TEXT_TYPE + COMMA_SET +
                    COLUMNA_PASSWORD + " " + NN_TEXT_TYPE + COMMA_SET +
                    COLUMNA_EMAIL + " " + TEXT_TYPE + " )";


    // Para evitar que alguien instancia accidentalmente la clase hare el constructor privado.
    private BBDD_estructura() {
    }

}

