package com.app.phant0m.agenda;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    //Aqui se declararon todos los edittext que tengo en el XML

    EditText et_ident,et_nombre,et_apellidos,et_direccion,et_telefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Aqui se iguala la parte grafica y el codigo
        et_ident = (EditText) findViewById(R.id.et_ident);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellidos = (EditText) findViewById(R.id.et_apellidos);
        et_direccion = (EditText) findViewById(R.id.et_direccion);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
    }

    //Este metodo es donde se guarda la informacion y esta ligada con el boton de guardar
    public void Guardar (View v){
// Esta condicion es para que no se guarde hasta que se llenen todos los edittext
        if(et_ident.getText().length()==0||et_nombre.getText().length()==0||et_apellidos.getText().length()==0||et_direccion.getText().length()==0||et_telefono.getText().length()==0){
            Toast.makeText(this, "Hay campos vacios =(", Toast.LENGTH_SHORT).show();
        } //
        else{ // Y si todos los edittext estan llenos entonces hara lo siguiente :

            //las siguientes dos lineas es donde se inicie la conexion a la BD agenda
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agendaa", null, 1);

            SQLiteDatabase bd = admin.getWritableDatabase();

//squi se declaran variables para guardar todo lo que se encuentre en los edittext ya convertidos en string
            String identificador = et_ident.getText().toString();
            String nombre = et_nombre.getText().toString();
            String apellidos = et_apellidos.getText().toString();
            String direccion = et_direccion.getText().toString();
            String telefono = et_telefono.getText().toString();


//aqui es donde las variables vacian su contenido a los campos de la base de datos
            ContentValues registro = new ContentValues(); // se creo un arreglo para cuardar los siguientes en registro
            registro.put("identificador", identificador );
            registro.put("nombre", nombre);
            registro.put("apellidos", apellidos);
            registro.put("direccion", direccion);
            registro.put("numero", telefono);

//aqui se hace la insercion de los datos a la base de datos
            bd.insert("agendaa", null, registro);
            //se cierra la conexion
            bd.close();

// se limpian los edittext para una nueva insercion
            et_ident.setText("");
            et_nombre.setText("");
            et_apellidos.setText("");
            et_direccion.setText("");
            et_telefono.setText("");

            //se manda un mensaje diciendo que se agrego un nuevo conacto
            Toast.makeText(this, "Se agrego un nuevo Contacto", Toast.LENGTH_SHORT).show();

        }

    }
//es el metodo onclick del boton consulta
    public void Consulta(View v) {
        //esto es para que intente hacer lo siguiente y no se cierre la app en caso de equivocacion
        try {
//se abre la conexion a la base de datos en las siguientes 2 lineas
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agendaa", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            //la siguiente linea dice que dni va a ser igual a lo que alla en el campo5
            String identificador = et_ident.getText().toString();  //el edittext et_ident va a ser igual
// aqui se ejecuta una consulta de mysqlite
            Cursor fila = bd.rawQuery("select identificador,nombre,apellidos,direccion,numero from agendaa where identificador= " + identificador, null);
//Aqui se vacia el contenido a los edittext
            if (fila.moveToFirst()) {
                et_ident.setText(fila.getString(0));
                et_nombre.setText(fila.getString(1));
                et_apellidos.setText(fila.getString(2));
                et_direccion.setText(fila.getString(3));
                et_telefono.setText(fila.getString(4));

            }
            // y si lo que se inserto en el campo5 no existe en la base de daos mandara el sig mensaje
            else {
                Toast.makeText(this, "No contacto no existe =/", Toast.LENGTH_SHORT).show();
            }
            //la base de datos se cierra
            bd.close();
        }
//si hay algun error lo atrapa y lo muestra
        catch(Exception e){
            Toast.makeText(this, "Tienes que poner el Identificador de un contacto =)" + e, Toast.LENGTH_SHORT).show();

        }
    }
    //este es el metodo para dar de baja algun contacto
    public void Baja(View v) {
        //el try intentara hacer las lineas del codigo que esten dentro de este
        try {
            //en estas 2 lineas se inicia la conexion con la base de datos
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agendaa", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            //el identificador sera igual a lo que hay en el edittext y lo convierte a string
            String identificador = et_ident.getText().toString();
// qui dice que se borrara lo que se alla en la tabla agenda y con el identificador del edittext
            int cant = bd.delete("agendaa", "identificador = " + identificador, null);
            //se cierra la conexion
            bd.close();
// se borra todo el contenido de los edittext
            et_ident.setText("");
            et_nombre.setText("");
            et_apellidos.setText("");
            et_direccion.setText("");
            et_telefono.setText("");


            if (cant == 1) {
                Toast.makeText(this, "Se borró el contacto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe el contacto", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(this, "Tienes que poner un Identificador de contacto =)" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar (View v) {
        try {
            //en las sig 2 lineas se inicia la base de datos
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agendaa", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
//aqui se crean variables que seran igual lo que alla en los edittext y se convertiran a string
            String identificador = et_ident.getText().toString();
            String nombre = et_nombre.getText().toString();
            String apellidos = et_apellidos.getText().toString();
            String direccion = et_direccion.getText().toString();
            String telefono = et_telefono.getText().toString();

//se crea un arreglo que va a contener todos los datos a modificar
            ContentValues registro = new ContentValues();

            registro.put("identificador",identificador);
            registro.put("nombre", nombre);
            registro.put("apellidos", apellidos);
            registro.put("direccion", direccion);
            registro.put("numero", telefono);


            int cant = bd.update("agendaa", registro, "identificador = " + identificador, null);
            bd.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos del contacto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe el contacto", Toast.LENGTH_SHORT).show();
            }
        }
        //si hay algun error lo mostrara
        catch(Exception e){
            Toast.makeText(this, "Primero tienes que consultar un identificador de un contacto" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void Limpiar (View v){

        //se limpian todos los edittext
        et_ident.setText("");
        et_nombre.setText("");
        et_apellidos.setText("");
        et_direccion.setText("");
        et_telefono.setText("");

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
