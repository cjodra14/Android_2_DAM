package com.jodra.taranis;

import static android.view.View.GONE;
import static java.lang.Thread.sleep;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  implements Runnable{


    private LinearLayout layoutElegirCiudad;



    private TextView nombreCiudad,txtTemperatura,txtTempMaxMin,txtSensacionTermica, txtEstado,txtFecha;
    private EditText elegirCiudadEdit;
    private ImageView imgEstado;

    private RecyclerView recyclerTiempo;
    private ArrayList<TiempoRecyclerContenedor> arrayListModeloTiempo;
    private AdaptadorRecyclerTiempo adaptadorRecyclerTiempo;

    private LocationManager locationManager;
    private String cityName;
    private String ciudad;
    private double latitude=0,longitude=0;
    Thread hilo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerTiempo=findViewById(R.id.recyclerTiempoHoras);
        nombreCiudad=findViewById(R.id.tvCiudad);
        txtTemperatura=findViewById(R.id.tvTempActual);
        txtTempMaxMin=findViewById(R.id.txtTempMaxMin);
        txtSensacionTermica=findViewById(R.id.txtSensacionTermica);
        txtEstado=findViewById(R.id.txtEstado);
        txtFecha=findViewById(R.id.txtFecha);

        elegirCiudadEdit=findViewById(R.id.editTextElegirCiudad);

        imgEstado=findViewById(R.id.imgEstado);
        layoutElegirCiudad = findViewById(R.id.layoutElegirCiudad);
        arrayListModeloTiempo = new ArrayList<>();
        adaptadorRecyclerTiempo = new AdaptadorRecyclerTiempo(this, arrayListModeloTiempo);
        recyclerTiempo.setAdapter(adaptadorRecyclerTiempo);


            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            hilo= new Thread(this);
            hilo.start();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getWeatherInfo(cityName);







    }



    private String getCityName(double latitude, double longitude){
        String cityName = "Ciudad no encontrada";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> direcciones = geocoder.getFromLocation(latitude,longitude,10);
            for (Address direccion: direcciones){
                if (direccion!=null){
                    String ciudad = direccion.getSubLocality();
                    if (ciudad!=null && !ciudad.equals("")){
                        cityName = ciudad;
                    }else{
                        System.out.println(R.string.city_not_found);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return cityName;
    }

    public void mostrarSeleccionCiudad(View vista){
        if (layoutElegirCiudad.getVisibility()== GONE){
        layoutElegirCiudad.setVisibility(View.VISIBLE);
        nombreCiudad.setVisibility(View.INVISIBLE);}
        else{
            layoutElegirCiudad.setVisibility(GONE);
            nombreCiudad.setVisibility(View.VISIBLE);
        }
    }

    public void buscarCiudad(View vista){
         ciudad = elegirCiudadEdit.getText().toString();
        if (ciudad.isEmpty()){
            Toast.makeText(MainActivity.this,R.string.intro_city,Toast.LENGTH_SHORT).show();
        }else{
         nombreCiudad.setText(cityName);
         getWeatherInfo(ciudad);
            layoutElegirCiudad.setVisibility(GONE);
            nombreCiudad.setVisibility(View.VISIBLE);
        }


    }


    private void getWeatherInfo(String ciudad){
        String url="http://api.weatherapi.com/v1/forecast.json?key=3aadb09321ca4ff0aa515220211511&q="+ciudad+"&days=1&aqi=no&alerts=no&lang="+Locale.getDefault().getLanguage();
        System.out.println(url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                arrayListModeloTiempo.clear();
                try {
                    String city =   response.getJSONObject("location").getString("name");
                    nombreCiudad.setText(city);
                    String fechaJson =   response.getJSONObject("location").getString("localtime");
                    SimpleDateFormat respuestaFecha = new SimpleDateFormat("yyy-MM-dd HH:mm");
                    SimpleDateFormat salidaFecha = new SimpleDateFormat("EEEE, dd MMMM ");
                    try {
                        Date formateo_fecha = respuestaFecha.parse(fechaJson);
                        txtFecha.setText(capitalizeFirstLetter(salidaFecha.format(formateo_fecha)));
                    }catch (ParseException e){
                        System.err.println("Error al parsear la Hora");
                        e.printStackTrace();
                    }

                    String temperature = response.getJSONObject("current").getString("temp_c");
                    txtTemperatura.setText(((int)Double.parseDouble(temperature))+"ºC");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.with(MainActivity.this).load("http:".concat(conditionIcon)).into(imgEstado);
                    txtEstado.setText(condition);
                    String feelslike = response.getJSONObject("current").getString("feelslike_c");
                    txtSensacionTermica.setText("Sensación termica: "+(int)Double.parseDouble(feelslike)+"ºC");

                    JSONObject forecastObject = response.getJSONObject("forecast");
                    JSONObject forcastO = forecastObject.getJSONArray("forecastday").getJSONObject(0);
                    String maxTemp = forcastO.getJSONObject("day").getString("maxtemp_c");
                    String minTemp = forcastO.getJSONObject("day").getString("mintemp_c");
                    txtTempMaxMin.setText((int)Double.parseDouble(minTemp)+"ºC - "+(int)Double.parseDouble(maxTemp)+"ºC");

                    JSONArray hourArray = forcastO.getJSONArray("hour");
                    for (int i = 0;i<hourArray.length();i++){
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String hora = hourObj.getString("time");
                        String temperaturaHora = String.valueOf((int)Double.parseDouble(hourObj.getString("temp_c")));
                        String imgHora = hourObj.getJSONObject("condition").getString("icon");
                        String velVientoHora = hourObj.getString("wind_kph");
                        arrayListModeloTiempo.add(new TiempoRecyclerContenedor(hora,temperaturaHora,imgHora,velVientoHora));

                    }
                    adaptadorRecyclerTiempo.notifyDataSetChanged();




                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, R.string.intro_city_valid, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean check = false;
        do{
        cityName= getCityName(latitude,longitude);

        System.out.println(cityName);
        check=true;}while (!check);


    }
    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos concedidos",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Por favor, concede los permisos para continuar",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
