package com.example.callejerorestaurantes;


import java.util.ArrayList;


import com.emg.callejerorestaurantes.base.Restaurante;
import com.emg.callejerorestaurantes.database.BaseDatos;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		
		datos = new BaseDatos(this);
		
		adaptador = new RestauranteAdapter(this, datos.getAllRestaurantes());
		
		ListView lvLista = (ListView) findViewById(R.id.lvLista);
		lvLista.setAdapter(adaptador);
		
		lvLista.setEmptyView(findViewById(R.id.tvSinDatos));
		this.registerForContextMenu(lvLista);	
		
		
		
	}
	

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
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("CallejeroRestautnate\nAplicacion para consultar Restaurantes\n(c) 2014 Marcos Escribano")
						.setIcon(R.drawable.ic_contacts)
						.setTitle("Acerca de")
						.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();						
							}
						});
				AlertDialog dialogo = builder.create();
				dialogo.show();
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
		outState.putParcelableArrayList("restaurantes", datos.getAllRestaurantes());
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
		
		//TODO TESTEAR ESTO
		if (verFavoritos){
			adaptador.verFavoritos();
			
		}
		else{
			adaptador.verTodos();
			SharedPreferences preferenciasNombre = PreferenceManager.getDefaultSharedPreferences(this);
			String opcionDatos = preferenciasNombre.getString("opcion_ver_Nombre", ""); 
			if(!opcionDatos.equals("")){
				adaptador.verNombre(opcionDatos);
				
			}
			else{
				adaptador.verTodos();
				SharedPreferences preferenciasPlato = PreferenceManager.getDefaultSharedPreferences(this);
				String opcionPlatos = preferenciasPlato.getString("opcion_ver_Plato", ""); 
				if(!opcionDatos.equals("")){
					adaptador.verNombre(opcionPlatos);
					
				}
				else
					adaptador.verTodos();
			}
		}
		adaptador.notifyDataSetChanged();
		
	}


}
