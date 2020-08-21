package com.thxx.ydzrzy.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.internal.jni.CoreGrid;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.Grid;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.thxx.ydzrzy.R;
import com.thxx.ydzrzy.base.RxBaseActivity;
import com.thxx.ydzrzy.helper.ArcgisHelper;
import com.thxx.ydzrzy.helper.ArcgisHelperFactory;
import com.thxx.ydzrzy.map.MapManager;

import java.util.ArrayList;
import java.util.List;

public class ArcGisActivity extends RxBaseActivity {

    private MapView mMapView;
    private ArcGISMap arcGISMap;
    //定位
    private LocationDisplay mLocationDisplay;
    private List<Point> points;

    @Override
    public int getLayoutId() {
        return R.layout.activity_arcgis;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initViews(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.mapView);
        setupMap();
    }

    @Override
    public void initToolBar() {

    }


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupMap() {
        if (mMapView != null) {
            ArcGISRuntimeEnvironment.setLicense(getResources().getString(R.string.arcgis_license_key));
            // 去除背后的网格
            //去掉gis的网格背景
            BackgroundGrid backgroundGrid = new BackgroundGrid();
            backgroundGrid.setColor(0xf6f6f6f6);
            backgroundGrid.setGridLineWidth(0);
            mMapView.setBackgroundGrid(backgroundGrid);
            //去掉gis的logo图标
            mMapView.setAttributionTextVisible(false);
            arcGISMap = new ArcGISMap();
            MapManager.loadTdtMap(mMapView,arcGISMap);
            ArcgisHelper arcgisHelper = ArcgisHelperFactory.createArcgisHelper();
            //设置我的位置
            arcgisHelper.setupLocationDisplay(this, mMapView);
            GraphicsOverlay graphicsOverlay = arcgisHelper.createGraphicsOverlay(mMapView);
            // 点击地图画点成形
            points = new ArrayList<>();
            mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
                    Point point = mMapView.screenToLocation(screenPoint);
                    Log.d("图形", point.getX() + ":" + point.getY());
                    points.add(new Point(point.getX(), point.getY()));
                    //绘制点
                    arcgisHelper.createPointGraphics(graphicsOverlay, point);
                    //绘制线
                    arcgisHelper.createPolylineGraphics(graphicsOverlay, points);
                    //绘制多边形
                    arcgisHelper.createPolygonGraphics(graphicsOverlay, points);
                    return super.onSingleTapUp(e);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            mMapView.dispose();
        }
        super.onDestroy();
    }


}
