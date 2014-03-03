package com.emg.callejerorestaurantes.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.emg.callejerorestaurantes.base.Restaurante;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static android.provider.BaseColumns._ID;


public class BaseDatos extends SQLiteOpenHelper {
	
	//Nombre de la base de datos
	private static final String BASEDATOS_NOMBRE = "restaurantes.db";

	//Version de la base de datos
	private static final int BASEDATOS_VERSION = 1;
	
	//Clausula para consultar los datos
	private static String[] FROM_CURSOR = {_ID, Constantes.NOMBRE_RESTAURANTE, Constantes.DIRECCION_RESTAURANTE, 
			Constantes.TELEFONO_RESTAURANTE, Constantes.FAVORITO_RESTAURANTE, Constantes.PLATO_RESTAURANTE, Constantes.FOTO_RESTAURANTE };
	
	private static String ORDER_BY =  Constantes.NOMBRE_RESTAURANTE + " DESC";
	
	public BaseDatos(Context contexto){
		super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
	}
	
	/**
	 * 
	 * Estructuras de las tablas de la base de datos.
	 */
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE " + Constantes.TABLA_RESTAURANTES + "(" + 
				 _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				 Constantes.NOMBRE_RESTAURANTE     +   " TEXT NOT NULL, " +
				 Constantes.DIRECCION_RESTAURANTE     +   " TEXT NOT NULL, " +
				 Constantes.TELEFONO_RESTAURANTE     +   " TEXT NOT NULL, " +
				 Constantes.PLATO_RESTAURANTE     +   " TEXT NOT NULL, " +
				
				 Constantes.FAVORITO_RESTAURANTE     +   " INTEGER NOT NULL, " +
				Constantes.FOTO_RESTAURANTE +   " BLOB );");
	}
	
	/** 
	 * Realizar aplicacion para actualizar la base de datos
	 */
	
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva){
		
		db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLA_RESTAURANTES);
		onCreate(db);
		
	}
	
	/**
	 * Registrar un nuevo juego en la tabla
	 */
	
	public void nuevoRestaurante(Restaurante restaurante){

    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues valores = new ContentValues();
    	valores.put(Constantes.NOMBRE_RESTAURANTE, restaurante.getNombre());
    	valores.put(Constantes.DIRECCION_RESTAURANTE, restaurante.getDireccion());
    	valores.put(Constantes.TELEFONO_RESTAURANTE, restaurante.getTelefono());
    	valores.put(Constantes.PLATO_RESTAURANTE, restaurante.getPlato());
    	valores.put(Constantes.FAVORITO_RESTAURANTE, restaurante.esFavorito());
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	restaurante.getFotoLogo().compress(Bitmap.CompressFormat.JPEG, 100, stream);
    	byte imageInByte[] = stream.toByteArray();
    	
    	
    	valores.put(Constantes.FOTO_RESTAURANTE, imageInByte);
    
    	
    	
    	db.insertOrThrow(Constantes.TABLA_RESTAURANTES, null, valores);
    
		
	}
	
	
	public ArrayList<Restaurante> getAllRestaurantes() {
	    ArrayList<Restaurante> listaRestaurante = new ArrayList<Restaurante>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + Constantes.TABLA_RESTAURANTES;
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	Restaurante restaurante = new Restaurante();
	        	restaurante.setNombre(cursor.getString(1));
	        	restaurante.setDireccion(cursor.getString(2));
	        	restaurante.setTelefono(cursor.getString(3));
	        	restaurante.setPlato(cursor.getString(4));
	        	
	        	
	        	int intValue =(cursor.getInt(5));
	        	restaurante.setFavorito((intValue == 1)? true : false);
	        
	        	System.out.println("LLEGA ANTES DEL BLOB");
	        	ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(6));
	        	Bitmap theImage = BitmapFactory.decodeStream(imageStream);
	        	System.out.println("ESTA ES LA IMAGEN AL IMPORTAR"+theImage);
	        	restaurante.setFotoLogo(theImage);
	        	
	        	
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
		Cursor cursor = db.query(Constantes.TABLA_RESTAURANTES, FROM_CURSOR, null, null, null, null, ORDER_BY);
		
		return cursor;
		
	}
}
