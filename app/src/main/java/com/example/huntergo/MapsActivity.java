package com.example.huntergo;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.huntergo.Classes.Monstro;
import com.example.huntergo.DAO.MonstroDAO;

import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Random;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int AccessLocation = 123;
    private MonstroDAO monstroDAO = new MonstroDAO();
    private boolean primeiraVez = true;
    Location loc = new Location("Start");
    ArrayList<Monstro> monstros;
    Location inicial = loc;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        configurarBottomNav();
        monstros = monstroDAO.getMonstros();

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(MapsActivity.this);
        checkPermission();
    }

    public void checkPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

                String[]  permissao = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissao, AccessLocation);
            } else{
                getUserLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == AccessLocation){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getUserLocation();
            }
        }
    }

    public void getUserLocation() {
        Toast.makeText(this, "Permissão do GPS garantida", Toast.LENGTH_LONG).show();


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

        }

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                loc = location;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, locationListener);

        Thread th = new Thread(){
            Location oldLocation = new Location("Start");

            @Override
            public void run() {
                while (true){

                    try {

                        if(oldLocation.distanceTo(loc) == 0f)
                        {
                            continue;
                        }

                        oldLocation = loc;

                        runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  mMap.clear();
                                  LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                                  mMap.addMarker(new MarkerOptions().position(latLng).title("Jogador")
                                  .icon(BitmapDescriptorFactory.fromResource(R.drawable.esqueleto)));
                                  mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
                                  mMap.getUiSettings().setZoomControlsEnabled(true);
                                  Log.d("teste", "Localizacao");

                                  if(primeiraVez == true || loc.distanceTo(inicial) > 50){
                                      primeiraVez = false;
                                      gerarMonstros(latLng);
                                      inicial = loc;
                                  }

                              }
                        });

                        Thread.sleep(1000);

                    } catch (Exception e){

                    }

                }

            }
        };
        th.start();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) { mMap = googleMap; }

    public void gerarMonstros(LatLng latLng){
        ArrayList<LatLng> perto = getRandomLocations(latLng, 50);
        if (monstros.isEmpty()){
            Log.d("teste", "vazio");
        } else{
            Log.d("teste", "cheio");
        }
        Monstro m;
        Random rand = new Random();
        BitmapDescriptor imagem = BitmapDescriptorFactory.fromResource(R.drawable.esqueleto);

        for (LatLng l : perto){
            m = monstros.get(rand.nextInt(monstros.size()));

            switch (m.getNome()){
                case "Goblin": imagem = BitmapDescriptorFactory.fromResource(R.drawable.goblin);
                break;

                case "Ogro": imagem = BitmapDescriptorFactory.fromResource(R.drawable.ogro);
                break;

                case "Slime": imagem = BitmapDescriptorFactory.fromResource(R.drawable.slime);
                break;

                case "Esqueleto": imagem = BitmapDescriptorFactory.fromResource(R.drawable.esqueleto);
                break;

            }

            mMap.addMarker(new MarkerOptions()
                .position(l)
                .title(m.getNome())
                .icon(imagem)
            );
        }
    }

    public ArrayList<LatLng> getRandomLocations(LatLng point, int radius) {

        ArrayList<LatLng> randomPoints = new ArrayList<>();
        Location myLocation = new Location("");
        myLocation.setLatitude(point.latitude);
        myLocation.setLongitude(point.longitude);

        //This is to generate 10 random points
        for(int i = 0; i<20; i++) {
            double x0 = point.latitude;
            double y0 = point.longitude;

            Random random = new Random();

            // Convert radius from meters to degrees
            double radiusInDegrees = radius / 111000f;

            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(y0);

            double foundLatitude = new_x + x0;
            double foundLongitude = y + y0;
            LatLng randomLatLng = new LatLng(foundLatitude, foundLongitude);
            randomPoints.add(randomLatLng);

        }

        return randomPoints;

    }

    public void configurarBottomNav(){
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.mapaSelect);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inventarioSelect:
                        startActivity(new Intent(getApplicationContext(), InventarioActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.mapaSelect:
                        return true;

                    case R.id.sairSelect:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                }

                return false;
            }
        });
    }
}
