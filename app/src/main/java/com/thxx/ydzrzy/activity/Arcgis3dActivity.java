package com.thxx.ydzrzy.activity;

import android.os.Bundle;

import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.thxx.ydzrzy.R;
import com.thxx.ydzrzy.base.RxBaseActivity;

/**
 * 3d地图
 */
public class Arcgis3dActivity extends RxBaseActivity {

    private SceneView mSceneView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_arcgis_3d;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSceneView = findViewById(R.id.sceneView);
        setupScene();
    }

    @Override
    public void initToolBar() {

    }

    private void setupScene() {
        if (mSceneView != null) {
            // *** ADD ***
            Basemap.Type basemapType = Basemap.Type.IMAGERY_WITH_LABELS;
            ArcGISScene scene = new ArcGISScene(basemapType);
            mSceneView.setScene(scene);
            addTrailheadsLayer();
            setElevationSource(scene);
        }
    }

    private void addTrailheadsLayer() {
        String url = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails/FeatureServer/0";
        final ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        mSceneView.getScene().getOperationalLayers().add(featureLayer);
        Camera camera = new Camera(
                33.950896,
                -118.525341,
                16000.0,
                0.0,
                50.0,
                0);
        mSceneView.setViewpointCamera(camera);
    }

    private void setElevationSource(ArcGISScene scene) {
        ArcGISTiledElevationSource elevationSource = new ArcGISTiledElevationSource(
                "http://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer");
        scene.getBaseSurface().getElevationSources().add(elevationSource);
    }
}
