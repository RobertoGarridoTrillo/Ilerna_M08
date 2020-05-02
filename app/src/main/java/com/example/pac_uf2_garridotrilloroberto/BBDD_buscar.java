package com.example.pac_uf2_garridotrilloroberto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;


class BBDD_buscar {
    // Campos de clase
    private String nombre, apellidos, usuario, password, email;

    final static String ORIGEN_INICIO = "Actividad_inicio.java";
    final static String ORIGEN_REGISTRAR = "Actividad_Registrar.java";

    /**
     * Este metodo comprueba si el campo pasado existe en la BBDD,
     *
     * @param db objeto de tipo SQLiteDatabase. Es la base de datos que trabajamos
     * @param origen la actividad que hace la llamada
     *               Constantes de clase:
     *               BBDD_buscar.ORIGEN_INICIO = "Actividad_inicio.java"
     *               BBDD_buscar.ORIGEN_REGISTRAR = "Actividad_Registrar.java"
     * @return Un objeto de tipo Usuario o un null
     */
    Usuario buscarCampo(SQLiteDatabase db, EditText usuarioEnviado, EditText passwordEnviado,
                        String origen) {
        // Definimos que columnas vamos a leer
        String[] projection = {
                BBDD_estructura.COLUMNA_NOMBRE,
                BBDD_estructura.COLUMNA_APELLIDOS,
                BBDD_estructura.COLUMNA_USUARIO,
                BBDD_estructura.COLUMNA_PASSWORD,
                BBDD_estructura.COLUMNA_EMAIL
        };
        // Filtramos los resultados -> WHERE "xxx" = 'xxx'
        String selection;
        String[] selectionArgs;
        if (origen.equals(ORIGEN_REGISTRAR)) {
            selection = BBDD_estructura.COLUMNA_USUARIO + " = ? ";
            selectionArgs = new String[]{usuarioEnviado.getText().toString()};
        } else if (origen.equals(ORIGEN_INICIO)) {
            selection = BBDD_estructura.COLUMNA_USUARIO + " = ? AND " +
                    BBDD_estructura.COLUMNA_PASSWORD + " = ? ";
            selectionArgs = new String[]{usuarioEnviado.getText().toString(), passwordEnviado.getText().toString()};
        } else return null;

        System.out.println(usuarioEnviado.getText().toString()+" "+passwordEnviado.getText().toString()+" "+origen);
        Cursor cursor;
        try {
            cursor = db.query(
                    BBDD_estructura.TABLA_CLIENTES,  // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE (criterio) clause
                    selectionArgs,          // The values for WHERE clause
                    null,          // don't group the rows
                    null,           // don't filter by row groups
                    null           // The sort order
            );
            // mueve el cursor a la primera posicio. Si es mayor a cero extrae los datos del usuario
            // y devuelve un objeto de tipo Usuario
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                this.nombre = cursor.getString(cursor.getColumnIndex(BBDD_estructura.COLUMNA_NOMBRE));
                this.apellidos = cursor.getString(cursor.getColumnIndex(BBDD_estructura.COLUMNA_APELLIDOS));
                this.usuario = cursor.getString(cursor.getColumnIndex(BBDD_estructura.COLUMNA_USUARIO));
                this.password = cursor.getString(cursor.getColumnIndex(BBDD_estructura.COLUMNA_PASSWORD));
                this.email = cursor.getString(cursor.getColumnIndex(BBDD_estructura.COLUMNA_EMAIL));
                cursor.close();
                return new Usuario(nombre, apellidos, usuario, password, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
