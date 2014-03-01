package com.example.callejerorestaurantes;


import com.emg.callejerorestaurantes.base.Restaurante;
import com.emg.callejerorestaurantes.database.BaseDatos;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NuevoRestaurante extends Activity implements OnClickListener  {
	private BaseDatos datos;
	private int RESULTADO_CARGA_IMAGEN = 1;
	private String boton="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_restaurante);
		
		Button btAceptar = (Button) findViewById(R.id.btAceptar);
        btAceptar.setOnClickListener(this);
        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(this);
        Button btImagen = (Button) findViewById(R.id.btImagen);
        btImagen.setOnClickListener(this);

        
	}
	
	/**
     * Método que se ejecutará cuando se invoque StartActivityForResult sobre esta
     * Activity.
     * Carga la imagen seleccionada de la galería de imágenes y la muestra en el ImageView
     */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if ((requestCode == RESULTADO_CARGA_IMAGEN) && (resultCode == RESULT_OK) && (data != null)) {
			// Obtiene el Uri de la imagen seleccionada por el usuario
			Uri imagenSeleccionada = data.getData();
			String[] ruta = {MediaStore.Images.Media.DATA };
			
			// Realiza una consulta a la galería de imágenes solicitando la imagen seleccionada
			Cursor cursor = getContentResolver().query(imagenSeleccionada, ruta, null, null, null);
            cursor.moveToFirst();
 
            // Obtiene la ruta a la imagen
            int indice = cursor.getColumnIndex(ruta[0]);
            String picturePath = cursor.getString(indice);
            cursor.close();
            if(boton.equals("Imagen")) {
	            // Carga la imagen en la vista ImageView que hay encima del botón
	            ImageView imageView = (ImageView) findViewById(R.id.ivRestaurante);
	            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_restaurante, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*
		 * Si el usuario pulsa en Aceptar, se recogen los datos, se crea un contacto
		 * y se añade a la lista de contactos.
		 * También se muestra un mensaje al usuario y se limpian las vistas
		 */
		case R.id.btAceptar:
			
			EditText etNombre = (EditText) findViewById(R.id.etNombre);
			EditText etDireccion = (EditText) findViewById(R.id.etDireccion);
			ImageView ivImagen = (ImageView) findViewById(R.id.ivRestaurante);
			EditText etTelefono = (EditText) findViewById(R.id.etTelefono);
			EditText etPlato = (EditText) findViewById(R.id.etPlato);
			CheckBox cbFavorito = (CheckBox) findViewById(R.id.cbFavorito);
			
			Restaurante restaurante = new Restaurante();
			restaurante.setNombre(etNombre.getText().toString());
			restaurante.setDireccion(etDireccion.getText().toString());
			restaurante.setFotoLogo(((BitmapDrawable) ivImagen.getDrawable()).getBitmap());
			restaurante.setTelefono(etTelefono.getText().toString());
			restaurante.setPlato(etPlato.getText().toString());
			restaurante.setFavorito(cbFavorito.isChecked());
			System.out.println("ESTE ES FAVORITO"+restaurante.esFavorito());
			//datos.nuevoRestaurante(restaurante);
			
			MainActivity.listaRestaurantes.add(restaurante);
			
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.nuevo_restaurante_message), Toast.LENGTH_SHORT).show();
			break;
		/*
		 * Si el usuario pulsa en el botón Cerrar, finalizamos la Activity
		 * o bien podemos 'volver atrás' (está comentado)
		 */
		case R.id.btCancelar:
			
			finish();
			//onBackPressed();
			break;
		/*
		 * Si el usuario pulsa en el botón Imagen mostramos la galería de imágenes del móvil
		 * para que escoja una imagen que luego visualizaremos y asignaremos al contacto
		 */
		case R.id.btImagen:
			boton = "Imagen";
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULTADO_CARGA_IMAGEN);
			break;	
		
		default:
		
	}

	}
}
