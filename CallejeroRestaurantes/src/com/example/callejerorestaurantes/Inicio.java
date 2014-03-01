package com.example.callejerorestaurantes;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Inicio extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		
		Button btEntrar = (Button) findViewById(R.id.btEntrar);
        btEntrar.setOnClickListener(this);
        
        Button btAjustes = (Button) findViewById(R.id.btAjustes);
        btAjustes.setOnClickListener(this);
        
        Button btMapa = (Button) findViewById(R.id.btMapa);
        btMapa.setOnClickListener(this);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.btEntrar){
			Intent intent = new Intent(Inicio.this,MainActivity.class);
			startActivity(intent);
		}
		if (v.getId()==R.id.btAjustes){
			Intent intent = new Intent (Inicio.this,Preferencias.class);
			startActivity(intent);
		}
		if (v.getId()==R.id.btMapa){
			Intent intent = new Intent (Inicio.this,Mapa.class);
			startActivity(intent);
		}
	}

}
