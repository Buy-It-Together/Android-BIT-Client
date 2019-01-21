package com.ujazdowski.client.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.ui.arguments.MapArguments;
import dagger.android.support.DaggerAppCompatActivity;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapActivity extends DaggerAppCompatActivity {

    @BindView(R.id.map_activity_map_view)
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        IConfigurationProvider configurationProvider = Configuration.getInstance();
        configurationProvider.load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        configurationProvider.setUserAgentHttpHeader("BIT IT!");
        configurationProvider.setUserAgentValue("BIT IT!");

        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        setUpMap((MapArguments) getIntent().getSerializableExtra(MapArguments.class.getSimpleName()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void setUpMap(MapArguments arguments) {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(Double.valueOf(arguments.getLatitude()), Double.valueOf(arguments.getLongitude()));
        IMapController mapController = mapView.getController();
        mapController.setZoom(9.5);
        mapController.setCenter(startPoint);

        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle(arguments.getLocation());
        mapView.getOverlays().add(startMarker);
        mapView.invalidate();
    }
}
