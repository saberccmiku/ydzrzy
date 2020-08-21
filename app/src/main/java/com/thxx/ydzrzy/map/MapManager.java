package com.thxx.ydzrzy.map;

import com.esri.arcgisruntime.io.RequestConfiguration;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.thxx.ydzrzy.map.amap.AMapTiledLayerClass;
import com.thxx.ydzrzy.map.goole.GoogleMapLayer;
import com.thxx.ydzrzy.map.tdt.TdtMethodsClass;

public class MapManager {

    /***
     *  加载谷歌地图
     * @param mapView MapView
     */
    public static void loadGooleMap(MapView mapView, ArcGISMap arcGISMap) {
        final WebTiledLayer webTiledLayer =
                GoogleMapLayer.CreateGoogleLayer(GoogleMapLayer.MapType.IMAGE);
        webTiledLayer.loadAsync();
        arcGISMap.setBasemap(new Basemap(webTiledLayer));
        mapView.setMap(arcGISMap);
    }

    public static void loadAmap(MapView mapView, ArcGISMap arcGISMap) {
        // 加载高德地图
        WebTiledLayer aMap = AMapTiledLayerClass.CreateAMapTiledLayer(AMapTiledLayerClass.LayerType.AMAP_IMAGE);
        aMap.loadAsync();
        arcGISMap.setBasemap(new Basemap(aMap));
        mapView.setMap(arcGISMap);
    }

    /***
     *  加载天地图
     * @param mapView MapView
     */
    public static void loadTdtMap(MapView mapView, ArcGISMap arcGISMap) {
        //注意：在100.2.0之后要设置RequestConfiguration
        RequestConfiguration requestConfiguration = new RequestConfiguration();
        requestConfiguration.getHeaders().put("referer", "http://www.arcgis.com");
//        // 加载天地图层-电子地图
//        WebTiledLayer vecordBaseTiledLayer = TdtMethodsClass.CreateTianDiTuTiledLayer(TdtMethodsClass.LayerType.TIANDITU_VECTOR_MERCATOR);
//        // 加载中文标注图层-电子地图
//        WebTiledLayer vecordWordTiledLayer = TdtMethodsClass.CreateTianDiTuTiledLayer(TdtMethodsClass.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR);

        // 加载天地图层-电子地图
        WebTiledLayer vecordBaseTiledLayer = TdtMethodsClass.CreateTianDiTuTiledLayer(TdtMethodsClass.LayerType.TIANDITU_IMAGE_2000);
        // 加载中文标注图层-电子地图
        WebTiledLayer vecordWordTiledLayer = TdtMethodsClass.CreateTianDiTuTiledLayer(TdtMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);

        vecordBaseTiledLayer.setRequestConfiguration(requestConfiguration);
        vecordWordTiledLayer.setRequestConfiguration(requestConfiguration);

        vecordBaseTiledLayer.loadAsync();
        vecordWordTiledLayer.loadAsync();

        Basemap basemap = new Basemap();

        basemap.getBaseLayers().add(vecordBaseTiledLayer);
        basemap.getBaseLayers().add(vecordWordTiledLayer);

        arcGISMap.setBasemap(basemap);
        mapView.setMap(arcGISMap);
    }
}
