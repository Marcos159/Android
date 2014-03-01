package com.example.callejerorestaurantes;


import java.util.ArrayList;




import com.emg.callejerorestaurantes.base.Restaurante;
import com.emg.callejerorestaurantes.database.BaseDatos;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity  implements OnCreateContextMenuListener {
	private BaseDatos datos;
	
	private RestauranteAdapter adaptador;
	public static ArrayList<Restaurante> listaRestaurantes;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			listaRestaurantes = new ArrayList<Restaurante>();
		}
		else {
			listaRestaurantes = savedInstanceState.getParcelableArrayList("contactos");
		}
		
		adaptador = new RestauranteAdapter(this, listaRestaurantes);
		
		ListView lvLista = (ListView) findViewById(R.id.lvLista);
		lvLista.setAdapter(adaptador);
		
		lvLista.setEmptyView(findViewById(R.id.tvSinDatos));
		this.registerForContextMenu(lvLista);
		
		//TODO METER LA BASE DE DATOS EN EL LIST
		//cargarLista();
	}
	
	/*private void cargarLista() {
	       
        // Instancia el objeto que permite trabajar con la Base de Datos
        datos = new BaseDatos(this);
        
        verRestaurantes();
    }
	
	
    private void verRestaurantes() {
    	Cursor cursor = datos.getRestaurantes();
    	this.cargarRestaurantes(cursor);
    }
    
    private void cargarRestaurantes(Cursor cursor) {
    	
    	SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.fila, cursor, FROM_SHOW, TO, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    	((ListView) findViewById(R.id.lvLista)).setAdapter(adaptador);
    }*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	/*
	 * Método que se ejecuta cuando el usuario selecciona una opción
	 * del menú de opciones.
	 * Hay que evaluar que opción ha pulsado y hacer lo que convenga
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent = null;
		
		switch (item.getItemId()) {
		
			// Sólo hay una opción, la de lanzar la Activity de dar de alta un contacto
			case R.id.menu_nuevo_restaurante:
				
				intent = new Intent(this, NuevoRestaurante.class);
				startActivity(intent);
				
				return true;
				
			case R.id.menu_inicio:
				
				intent = new Intent(this, Inicio.class);
				startActivity(intent);
				
				return true;	
				
			case R.id.menu_Preferencias:
				
				intent = new Intent(this, Preferencias.class);
				startActivity(intent);
				
				return true;
			case R.id.menu_AcercaDe:
				
				intent = new Intent(this, AcercaDe.class);
				startActivity(intent);
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}	
	
	/*
	 * Este método lo invoca Android antes de que la Activity termine 
	 * para almacenar su estado.
	 * En este caso se aprovecha para almacenar la lista de contactos, de forma
	 * que luego podamos recuperarla en el método onCreate()
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		System.out.println("ENTRA EN ONSAVE");
		outState.putParcelableArrayList("restaurantes", listaRestaurantes);
		super.onSaveInstanceState(outState);
	}
	
	/*
	 * Siempre que volvamos de segundo plano, notificaremos al adaptador
	 * que hay cambios por si los hubiera habido
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		/*
		 * Comprueba las preferencias del usuario para ver si se deben
		 * mostrar sólo los contactos marcados como favoritos
		 */
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
		boolean verFavoritos = preferencias.getBoolean("opcion_ver_favoritos", false);
		
		if (verFavoritos){
			adaptador.verFavoritos();
			System.out.println("LLEGA");
		}
		else
			adaptador.verTodos();
		
		
		SharedPreferences preferenciasNombre = PreferenceManager.getDefaultSharedPreferences(this);
		String opcionDatos = preferenciasNombre.getString("opcion_ver_Nombre", ""); 
		if(!opcionDatos.equals("")){
			adaptador.verNombre(opcionDatos);
			
		}
		else
			adaptador.verTodos();
	
		adaptador.notifyDataSetChanged();
		
	}


}
