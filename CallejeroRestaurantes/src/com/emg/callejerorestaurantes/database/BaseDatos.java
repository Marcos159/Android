package com.emg.callejerorestaurantes.database;

import java.util.ArrayList;
import java.util.List;



import com.emg.callejerorestaurantes.base.Restaurante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;


public class BaseDatos extends SQLiteOpenHelper {
	
	//Nombre de la base de datos
	private static final String BASEDATOS_NOMBRE = "restaurantes.db";

	//Version de la base de datos
	private static final int BASEDATOS_VERSION = 1;
	
	//Clausula para consultar los datos
	private static String[] FROM_CURSOR = {_ID, "Nombre", "Direccion", 
			"Telefono", "Plato", "Imagen", "Imagen_Plato" };
	
	private static String ORDER_BY = "Nombre" + " DESC";
	
	public BaseDatos(Context contexto){
		super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
	}
	
	/**
	 * 
	 * Estructuras de las tablas de la base de datos.
	 */
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE " + "restaurantes" + "(" + 
				 _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				"Nombre"     +   " TEXT NOT NULL, " +
				"Direccion"     +   " TEXT NOT NULL, " +
				"Telefono"     +   " TEXT NOT NULL, " +
				"Plato"     +   " TEXT NOT NULL);");
		//TODO COMO METER IMAGENES Y EL BOOLEAN
	}
	
	/** 
	 * Realizar aplicacion para actualizar la base de datos
	 */
	
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva){
		
		db.execSQL("DROP TABLE IF EXISTS " + "restaurantes");
		onCreate(db);
		
	}
	
	/**
	 * Registrar un nuevo juego en la tabla
	 */
	
	public void nuevoRestaurante(Restaurante restaurante){

    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues valores = new ContentValues();
    	valores.put("Nombre", restaurante.getNombre());
    	valores.put("Direccion", restaurante.getDireccion());
    	valores.put("Telefono", restaurante.getTelefono());
    	
    	valores.put("Plato", restaurante.getPlato());
    	
    	
    	db.insertOrThrow("Restaurantes", null, valores);
    
		
	}
	
	
	public ArrayList<Restaurante> getAllRestauranes() {
	    ArrayList<Restaurante> listaRestaurante = new ArrayList<Restaurante>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + "Restaurantes";
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	Restaurante restaurante = new Restaurante();
	        	restaurante.setNombre(cursor.getString(1));
	        	restaurante.setDireccion(cursor.getString(2));
	        	restaurante.setTelefono(cursor.getString(3));
	        	
	        	restaurante.setPlato(cursor.getString(5));
	        	
	        	listaRestaurante.add(restaurante);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return listaRestaurante;
	}
	
	/**
	 * Obtiene un cursor con todos alumnos de la tabla
	 */
	
	
	public Cursor getRestaurantes()  {
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("Restaurantes", FROM_CURSOR, null, null, null, null, ORDER_BY);
		
		return cursor;
		
	}
}
