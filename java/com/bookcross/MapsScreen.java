package com.bookcross;


import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.bookcross.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsScreen extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedClient;
    private static final int REQUEEST_CODE = 101;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Marker marker;
    SearchView searchView;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        fusedClient = LocationServices.getFusedLocationProviderClient(this); //поиск локации пользователя
        getLocation();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String  locat = searchView.getQuery().toString();
                if (locat == null){
                    Toast.makeText(MapsScreen.this, "Место не найдено", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder = new Geocoder(MapsScreen.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(locat, 1);
                        if (addressList.size() > 0){
                            LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                            if (marker != null){
                                marker.remove();
                            }
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(locat);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                            mMap.animateCamera(cameraUpdate);
                            marker = mMap.addMarker(markerOptions);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEEST_CODE);
            return;
        }

        Task<Location> task = fusedClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    assert mapFragment != null;
                    mapFragment.getMapAsync(MapsScreen.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng loc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(loc).title("Вы находитесь здесь"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));


        LatLng helo_cruise = new LatLng(53.24991507744624, 45.01487152712071);
        mMap.addMarker(new MarkerOptions().position(helo_cruise).title("HELIOPARK Cruise").snippet("2 этаж в зоне ресепшн"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(helo_cruise));

        LatLng helo_res = new LatLng(53.19162178578689, 45.01971653274292);
        mMap.addMarker(new MarkerOptions().position(helo_res).title("HELIOPARK Residence").snippet("1 этаж в зоне ресепшн"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(helo_res));

        LatLng anticafe1 = new LatLng(53.20100890011625, 45.01899579779215);
        mMap.addMarker(new MarkerOptions().position(anticafe1).title("Антикафе Крылья").snippet("Второй этаж, " +
                "полка на стеллаже прямо от входа"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(anticafe1));

        LatLng anticafe2 = new LatLng(53.18621098037059, 45.0139297615496);
        mMap.addMarker(new MarkerOptions().position(anticafe2).title("Антикафе Чердак").snippet("Слева от входа"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(anticafe2));

        /*LatLng bezPol = new LatLng(53.18225024037387, 45.01447081128518);
        mMap.addMarker(new MarkerOptions().position(bezPol).title("Безопасная полка"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bezPol));*/

        LatLng cityLid = new LatLng(53.22219550309377, 44.93980098720748);
        mMap.addMarker(new MarkerOptions().position(cityLid).title("Городская библиотека №17").snippet("Прямо от входа"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cityLid));

        LatLng home = new LatLng(53.19230724657142, 45.01996545623978);
        mMap.addMarker(new MarkerOptions().position(home).title("Дом молодёжи").snippet("Центральный вход"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));

        LatLng labirint = new LatLng(53.208130530439796, 44.9956903110006);
        mMap.addMarker(new MarkerOptions().position(labirint).title("Лабиринт").snippet("Бизнес-центр Сокол"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(labirint));

        LatLng labirint2 = new LatLng(53.18899857762263, 45.01555498712542);
        mMap.addMarker(new MarkerOptions().position(labirint2).title("Лабиринт").snippet("Бизнес-центр Гермес"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(labirint2));


        LatLng tipgraf = new LatLng(53.226782453430125, 45.01611692179001);
        mMap.addMarker(new MarkerOptions().position(tipgraf).title("Типография №1").snippet("Книжная " +
                "полка в офисе Типографии"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tipgraf));

        LatLng tc_summer = new LatLng(53.22227485041697, 44.91564274012236);
        mMap.addMarker(new MarkerOptions().position(tc_summer).title("ТЦ Лето"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tc_summer));

        LatLng tc_mart = new LatLng(53.215527219132376, 44.970162655463426);
        mMap.addMarker(new MarkerOptions().position(tc_mart).title("ТЦ Март").snippet("Безопасная полка напротив входа"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tc_mart));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }
}