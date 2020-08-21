package com.thxx.ydzrzy.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.FeatureCollectionTable;
import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.FeatureCollectionLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.thxx.ydzrzy.entity.PointTableFeature;

import java.util.ArrayList;
import java.util.List;

public class ArcgisHelper {

    /**
     * 处理应用程序配置和启动位置服务所需的一切
     */
    public void setupLocationDisplay(Context context, MapView mapView) {
        LocationDisplay locationDisplay = mapView.getLocationDisplay();
        //添加事件侦听器以侦听位置数据源更改事件，并在此处检查用户是否已授予对设备GPS位置的访问权限。如果未授予许可，则向用户请求许可。
        locationDisplay.addDataSourceStatusChangedListener(dataSourceStatusChangedEvent -> {
            if (dataSourceStatusChangedEvent.isStarted() || dataSourceStatusChangedEvent.getError() == null) {
                return;
            }

            int requestPermissionsCode = 2;
            String[] requestPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (!(ContextCompat.checkSelfPermission(context, requestPermissions[0]) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, requestPermissions[1]) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions((Activity) context, requestPermissions, requestPermissionsCode);
            } else {
                String message = String.format("Error in DataSourceStatusChangedListener: %s",
                        dataSourceStatusChangedEvent.getSource().getLocationDataSource().getError().getMessage());
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
        // 设置指南针导航的地图平移模式并开始位置显示
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
        locationDisplay.startAsync();
    }

    /**
     * 在地图上添加图层
     *
     * @param mapView    MapView
     * @param mapService 地图服务
     */
    public void addTrailheadsLayer(MapView mapView, String mapService) {
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(mapService);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        ArcGISMap map = mapView.getMap();
        map.getOperationalLayers().add(featureLayer);
    }

    /**
     * 创建一个GraphicsOverlay对象，将其分配给成员变量，然后将其添加到地图视图。
     *
     * @param mapView MapView
     * @return GraphicsOverlay
     */
    public GraphicsOverlay createGraphicsOverlay(MapView mapView) {
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        return graphicsOverlay;
    }

    /**
     * 将点图形添加到图形叠加层
     */
    public void createPointGraphics(GraphicsOverlay graphicsOverlay, Point point) {
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic pointGraphic = new Graphic(point, pointSymbol);
        graphicsOverlay.getGraphics().add(pointGraphic);
    }

    /**
     * 在地图上绘制线形图
     */
    public void createPolylineGraphics(GraphicsOverlay graphicsOverlay, List<Point> points) {
        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWebMercator());
        polylinePoints.addAll(points);
        Polyline polyline = new Polyline(polylinePoints);
        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 3.0f);
        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
        graphicsOverlay.getGraphics().add(polylineGraphic);
    }

    /**
     * 添加多边形图形
     */
    public void createPolygonGraphics(GraphicsOverlay graphicsOverlay, List<Point> points) {
        PointCollection polygonPoints = new PointCollection(SpatialReferences.getWebMercator());
        polygonPoints.addAll(points);
        Polygon polygon = new Polygon(polygonPoints);
        SimpleFillSymbol polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.rgb(226, 119, 40),
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic polygonGraphic = new Graphic(polygon, polygonSymbol);
        graphicsOverlay.getGraphics().add(polygonGraphic);
    }


    /**
     * 将FeatureCollectionLayer添加到地图视图
     */
    public void createFeatureCollection(MapView mapView, SimpleMarkerSymbol.Style style, List<Field> pointFields, List<PointTableFeature> pointTables) {
        if (mapView != null) {
            FeatureCollection featureCollection = new FeatureCollection();
            FeatureCollectionLayer featureCollectionLayer = new FeatureCollectionLayer(featureCollection);
            mapView.getMap().getOperationalLayers().add(featureCollectionLayer);
            createPointTable(featureCollection, style, pointFields, pointTables);
        }
    }

    /**
     * 创建要素，将其添加到Feature Collection Table中，然后在地图上的Feature Layer中显示它们。要素将直接从纬度和经度坐标创建。
     */
    private void createPointTable(FeatureCollection featureCollection, SimpleMarkerSymbol.Style style, List<Field> pointFields, List<PointTableFeature> pointTables) {

        // list of features in the collection
        List<Feature> features = new ArrayList<>();

        // Define the schema for the feature collection table by adding field definitions
        //pointFields.add(Field.createString("Place", "Place Name", 50));

        //Create a FeatureCollectionTable object using the schema
        FeatureCollectionTable pointsTable = new FeatureCollectionTable(pointFields, GeometryType.POINT, SpatialReferences.getWgs84());

        // Create the symbol and renderer that will be used to visualize the features on the map
        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(style, 0xFF0000FF, 18);
        SimpleRenderer renderer = new SimpleRenderer(simpleMarkerSymbol);
        pointsTable.setRenderer(renderer);

        // Add the table to the featureCollection object
        featureCollection.getTables().add(pointsTable);

        // Create a feature for each of the 3 sporting venues that will be added to the pointTable
        for (PointTableFeature feature : pointTables) {
            features.add(pointsTable.createFeature(feature.getAttributes(), feature.getPoint()));
        }
//        // Dodger Stadium
//        Map<String, Object> attributes1 = new HashMap<>();
//        attributes1.put(pointFields.get(0).getName(), "Malibu Pier");
//        Point point1 = new Point(-118.676726, 34.037288, SpatialReferences.getWgs84());
//        features.add(pointsTable.createFeature(attributes1, point1));
//
//        // Los Angeles Memorial Coliseum
//        Map<String, Object> attributes2 = new HashMap<>();
//        attributes2.put(pointFields.get(0).getName(), "Malibu Hindi Temple");
//        Point point2 = new Point(-118.709726, 34.095097, SpatialReferences.getWgs84());
//        features.add(pointsTable.createFeature(attributes2, point2));
//
//        // Staples Center
//        Map<String, Object> attributes3 = new HashMap<>();
//        attributes3.put(pointFields.get(0).getName(), "Escondido Falls");
//        Point point3 = new Point(-118.779438, 34.044211, SpatialReferences.getWgs84());
//        features.add(pointsTable.createFeature(attributes3, point3));

        // Add the features asynchronously to the pointTable
        pointsTable.addFeaturesAsync(features);
    }


}
