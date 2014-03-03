package com.example.callejerorestaurantes;

import java.util.ArrayList;



import com.emg.callejerorestaurantes.base.Restaurante;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestauranteAdapter extends BaseAdapter {
	//Lista de contactos de la aplicacion
	private ArrayList <Restaurante> listaRestaurantes;
	//Lista de contactos a mostrar en cafa momento
	private ArrayList <Restaurante> listaActual;
	private LayoutInflater inflater;

	static class ViewHolder {
		
		ImageView imagen;
		TextView nombreRestaurante;
		TextView telefono;
		TextView plato;
	}
	
	public RestauranteAdapter (Activity contexto, ArrayList<Restaurante> listaRestaurantes){
		
		
		this.listaRestaurantes = listaRestaurantes;
		listaActual = new ArrayList <Restaurante>();
		inflater = LayoutInflater.from(contexto);
	}
	
	/*
	 * Solo mantiene en la lista a los contactos favoritos
	 */
	public void verFavoritos(){
		listaActual.clear();
		for(Restaurante restaurante : listaRestaurantes){
			if(restaurante.esFavorito())
				listaActual.add(restaurante);
		}
	}
	
	public void verNombre(String nombre){
		listaActual.clear();
		for(Restaurante restaurante : listaRestaurantes){
			if(restaurante.getNombre().equals(nombre))
				listaActual.add(restaurante);
		}
	}
	
	public void verPlato(String plato){
		listaActual.clear();
		for(Restaurante restaurante : listaRestaurantes){
			if(restaurante.getPlato().equals(plato))
				listaActual.add(restaurante);
		}
	}
	
	
	/*
	 * Muestra todos los restaurantes de la aplicacion
	 */
	public void verTodos(){
		
		listaActual.clear();
		
		listaActual.addAll(listaRestaurantes);
		
	}
	public void eliminar(int posicion) {
		listaActual.remove(posicion);
		notifyDataSetChanged();
	}
	/*
	 * Se ha empleado el patrón Holder para mejorar el rendimiento de la ListView
	 * http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	 */
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup padre) {
		ViewHolder holder = null;
		
		//Si la View es nill se crea de nuevo
		if(convertView == null){
			convertView = inflater.inflate(R.layout.fila, null);
			
			holder = new ViewHolder();
			holder.imagen = (ImageView) convertView.findViewById(R.id.ivImagen);
			holder.nombreRestaurante = (TextView) convertView.findViewById(R.id.tvNombreRestaurante);
			holder.telefono = (TextView) convertView.findViewById(R.id.tvTelefonoRestaurante);
			holder.plato = (TextView) convertView.findViewById(R.id.tvPlatoPrincipal);
			
			convertView.setTag(holder);
		}
		/*
		 *  En caso de que la View no sea null se reutilizará con los
		 *  nuevos valores
		 */
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Restaurante restaurante = listaActual.get(position);
		/*
		 *  Comprueba, según las prerencias de la aplicación, cómo
		 *  se deben mostrar los datos del contacto en pantalla
		 */
		//String nombreRestaurante = null;
		//SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(contexto);
		//String opcionDatos = preferencias.getString("opcion_datos", "Nombre");
		holder.imagen.setImageBitmap(restaurante.getFotoLogo());
		holder.nombreRestaurante.setText(restaurante.getNombre());
		holder.telefono.setText(restaurante.getTelefono());
		holder.plato.setText(restaurante.getPlato());
		return convertView;
	}
	@Override
	public int getCount() {
		
		return listaActual.size();
	}

	@Override
	public Object getItem(int posicion) {
		
		return listaActual.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		
		return posicion;
	}
}
