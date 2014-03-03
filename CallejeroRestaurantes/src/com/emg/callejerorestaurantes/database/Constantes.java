package com.emg.callejerorestaurantes.database;

import android.provider.BaseColumns;

/**
 * Clase con las constantes usadas en la aplicación
 * @author Santiago Faci
 *
 */
public interface Constantes extends BaseColumns {
	
	public static final String TABLA_RESTAURANTES = "restaurantes";
	
	/**
	 * Columnas de la tabla Alumnos
	 */
	public static final String NOMBRE_RESTAURANTE = "nombre";
	public static final String DIRECCION_RESTAURANTE = "direccion";
	public static final String TELEFONO_RESTAURANTE = "telefono";
	public static final String FAVORITO_RESTAURANTE = "favorito";
	public static final String PLATO_RESTAURANTE = "plato";
	public static final String FOTO_RESTAURANTE = "foto";

}
