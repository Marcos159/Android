package com.emg.callejerorestaurantes.base;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Restaurante implements Parcelable {
	private String nombre;
	private String direccion;
	private String telefono;
	private String plato;
	private boolean favorito;
	private Bitmap fotoLogo;
	
	
	public Restaurante(){}
	
	public Restaurante(String nombre, String direccion, String telefono, String plato, boolean favorito, Bitmap fotoLogo, Bitmap fotoPlato){
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.plato = plato;
		this.favorito = favorito;
		this.fotoLogo = fotoLogo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPlato() {
		return plato;
	}

	public void setPlato(String plato) {
		this.plato = plato;
	}

	public boolean esFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	public Bitmap getFotoLogo() {
		return fotoLogo;
	}

	public void setFotoLogo(Bitmap fotoLogo) {
		this.fotoLogo = fotoLogo;
	}

	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Metodo que sera llamado cuando haya que almacenar los datos por la interfaz Parcelable;
	 */
	@Override
	public void writeToParcel(Parcel destino, int flags) {
		destino.writeString(nombre);
		destino.writeString(direccion);
		destino.writeString(telefono);
		destino.writeString(plato);
		destino.writeString(String.valueOf(favorito));
		destino.writeParcelable(fotoLogo, PARCELABLE_WRITE_RETURN_VALUE);
	
	}
	
	/**
	 * Constructor para recuperar los datos utilizando el interfaz Parcelable
	 * Hay que tener en cuenta que se deben leer en el mismo orden en que se escribieron
	 * @param entrada 
	 */
	private Restaurante (Parcel entrada){
		nombre = entrada.readString();
		direccion = entrada.readString();
		telefono = entrada.readString();
		plato = entrada.readString();
		favorito = Boolean.parseBoolean(entrada.readString());
		fotoLogo = (Bitmap) entrada.readParcelable(Bitmap.class.getClassLoader());
		
	}
}
