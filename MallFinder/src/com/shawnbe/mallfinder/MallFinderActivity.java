package com.shawnbe.mallfinder;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

//import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MallFinderActivity extends MapActivity implements LocationListener{
    
	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private GeoPoint currentPoint;
	private Location currentLocation = null;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);
        mapView.setStreetView(true);
        mapController = mapView.getController();
        mapController.setZoom(13);
        getLastLocation();
        animateToCurrentLocation();
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void onLocationChanged(Location newLocation) {
        // TODO Auto-generated method stub
        setCurrentLocation(newLocation);
    }
     
    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub
    }
     
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub
    }
     
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(getBestProvider(), 1000, 1, this);
    }
     
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
    
    public void getLastLocation(){
        String provider = getBestProvider();
        currentLocation = locationManager.getLastKnownLocation(provider);
        if(currentLocation != null){
            setCurrentLocation(currentLocation);
        }
        else
        {
            Toast.makeText(this, "Location not yet acquired", Toast.LENGTH_LONG).show();
        }
    }
    
    public void animateToCurrentLocation(){
        if(currentPoint!=null){
            mapController.animateTo(currentPoint);
        }
    }
    
    public String getBestProvider(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        String bestProvider = locationManager.getBestProvider(criteria, true);
        return bestProvider;
    }
    
    public void setCurrentLocation(Location location){
        int currLatitude = (int) (location.getLatitude()*1E6);
        int currLongitude = (int) (location.getLongitude()*1E6);
        currentPoint = new GeoPoint(currLatitude,currLongitude);
     
        currentLocation = new Location("");
        currentLocation.setLatitude(currentPoint.getLatitudeE6() / 1e6);
        currentLocation.setLongitude(currentPoint.getLongitudeE6() / 1e6);
    }
}