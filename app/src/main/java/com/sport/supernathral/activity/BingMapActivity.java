package com.sport.supernathral.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.microsoft.maps.Geolocation;
import com.microsoft.maps.MapAnimationKind;
import com.microsoft.maps.MapElementLayer;
import com.microsoft.maps.MapIcon;
import com.microsoft.maps.MapImage;
import com.microsoft.maps.MapProjection;
import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapScene;
import com.microsoft.maps.MapServices;
import com.microsoft.maps.MapStyleSheet;
import com.microsoft.maps.MapStyleSheets;
import com.microsoft.maps.MapView;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.BingLocalSearch;

import java.util.List;

public class BingMapActivity extends AppCompatActivity {

    private static final MapStyleSheet[] MAP_STYLES = {
            MapStyleSheets.roadLight(),
            MapStyleSheets.roadDark(),
            MapStyleSheets.roadCanvasLight(),
            MapStyleSheets.aerial(),
            MapStyleSheets.aerialWithOverlay(),
            MapStyleSheets.roadHighContrastLight(),
            MapStyleSheets.roadHighContrastDark(),
    };
    private static final int POSITION_CUSTOM = MAP_STYLES.length;

    private static final Geolocation LOCATION_11st =
            new Geolocation(39.921901702880859,116.44355010986328);

    Toolbar toolbar;
    private MapView mMapView;
    private MapElementLayer mPinLayer;
    private MapImage mPinImage;

    String address_ch, title_ch, address_en, title_en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bingmap_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);



        mMapView = new MapView(this, MapRenderMode.VECTOR);
        mMapView.setCredentialsKey(BingLocalSearch.CREDENTIALS_KEY);
        ((FrameLayout)findViewById(R.id.map_view)).addView(mMapView);

        mPinLayer = new MapElementLayer();

        mMapView.getLayers().add(mPinLayer);
        mPinImage = getPinImage();

        mMapView.setMapProjection(MapProjection.GLOBE);
        mMapView.setMapProjection(MapProjection.WEB_MERCATOR);
        //mMapView.setLanguage("zh-cn");

        MapServices.setCredentialsKey(BingLocalSearch.CREDENTIALS_KEY);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            title_en = bundle.getString("event_venue");
            address_en = bundle.getString("event_address");
            title_ch = bundle.getString("event_venue_chinese");
            address_ch = bundle.getString("event_address_chinese");

            getLocation();
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    private void getLocation(){

        //String query = "Level 15 NCI Tower, No. 12 A Jianguomenwai Ave, Chaoyang District, Beijing, 100022";

        String title = title_en + "\n" + address_en;

        String query = address_en.replace(" ", "+");
        BingLocalSearch.sendRequest(BingMapActivity.this,
                query, mMapView.getBounds(), new BingLocalSearch.Callback() {
                    @Override
                    public void onSuccess(List<BingLocalSearch.Poi> results) {
                        clearPins();
                        for (BingLocalSearch.Poi result : results) {

                            addPin(result.location, title);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(BingMapActivity.this,
                                "No search results found",
                                Toast.LENGTH_LONG).show();
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();
        /*mMapView.setScene(
                MapScene.createFromLocationAndZoomLevel(LOCATION_11st, 11),
                MapAnimationKind.NONE);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.suspend();
        }
    }

    private void addPin(Geolocation location, String title) {
        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(location);
        pushpin.setTitle(title);
        pushpin.setImage(mPinImage);
        mPinLayer.getElements().add(pushpin);

        mMapView.setScene(
                MapScene.createFromLocationAndZoomLevel(location, 12),
                MapAnimationKind.NONE);
    }

    private void clearPins() {
        mPinLayer.getElements().clear();
    }

    private MapImage getPinImage() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),
                R.mipmap.marker_red, null);

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return new MapImage(bitmap);
    }


}
