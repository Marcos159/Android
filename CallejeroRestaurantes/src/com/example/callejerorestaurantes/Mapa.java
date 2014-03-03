package com.example.callejerorestaurantes;




import java.util.ArrayList;

import com.emg.callejerorestaurantes.base.Restaurante;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;

	public class Mapa extends FragmentActivity implements  OnClickListener, OnMarkerClickListener  {
		
				
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.mapa);
			
			 
	}
		
		

		@Override
		public void onClick(View v) {
			
		}



		@Override
		public boolean onMarkerClick(Marker arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		

}
