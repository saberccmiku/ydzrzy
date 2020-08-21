package com.thxx.ydzrzy.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.thxx.ydzrzy.R;
import com.thxx.ydzrzy.adapter.CommonRecyclerAdapter;
import com.thxx.ydzrzy.adapter.CommonRecyclerViewHolder;
import com.thxx.ydzrzy.base.RxBaseActivity;
import com.thxx.ydzrzy.entity.Menu;
import com.thxx.ydzrzy.helper.ArcgisHelper;
import com.thxx.ydzrzy.helper.ArcgisHelperFactory;
import com.thxx.ydzrzy.map.MapManager;
import com.thxx.ydzrzy.utils.JsonUtil;
import com.thxx.ydzrzy.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends RxBaseActivity {

    private List<Menu> mapLayerMenuList;
    private CommonRecyclerAdapter<Menu> adapter;
    private List<Point> points;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        MapView mapView = findViewById(R.id.main_mapview);
        setupMap(mapView);
        initSwitchLayer();
    }

    @Override
    public void loadData() {
        mapLayerMenuList.addAll(JsonUtil.parseAssetJson2List(this, "map_type_menu.json", Menu.class));
        adapter.notifyDataSetChanged();
    }

    private void initSwitchLayer() {
        mapLayerMenuList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_layer);
        adapter = new CommonRecyclerAdapter<Menu>(this, R.layout.item_switch_layer, mapLayerMenuList) {
            @Override
            public void convertView(CommonRecyclerViewHolder holder, Menu menu) {
                TextView tvMap = holder.getView(R.id.tv_map);
                Drawable drawable;

                try {
                    if (menu.isSelected()) {
                        drawable = MainActivity.this.getResources().getDrawable(ResourceUtil.getResourceId(menu.getSelectedDrawable()));
                    } else {
                        drawable = MainActivity.this.getResources().getDrawable(ResourceUtil.getResourceId(menu.getDefaultDrawable()));
                    }
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvMap.setCompoundDrawables(null, drawable, null, null);
                    tvMap.setText(menu.getTitle());

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < mapLayerMenuList.size(); i++) {
                    mapLayerMenuList.get(i).setSelected(false);
                }
                if (!mapLayerMenuList.get(position).isSelected()) {
                    mapLayerMenuList.get(position).setSelected(true);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }


    @BindView(R.id.main)
    DrawerLayout drawerLayout;

    @OnClick({R.id.tv_layer})
    public void onClick() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public void initToolBar() {
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupMap(MapView mapView) {
        if (mapView != null) {
            ArcGISRuntimeEnvironment.setLicense(getResources().getString(R.string.arcgis_license_key));
            // 去除背后的网格
            //去掉gis的网格背景
            BackgroundGrid backgroundGrid = new BackgroundGrid();
            backgroundGrid.setColor(0xf6f6f6f6);
            backgroundGrid.setGridLineWidth(0);
            mapView.setBackgroundGrid(backgroundGrid);
            //去掉gis的logo图标
            mapView.setAttributionTextVisible(false);
            ArcGISMap arcGISMap = new ArcGISMap();
            MapManager.loadTdtMap(mapView, arcGISMap);
            ArcgisHelper arcgisHelper = ArcgisHelperFactory.createArcgisHelper();
            //设置我的位置
            arcgisHelper.setupLocationDisplay(this, mapView);
            GraphicsOverlay graphicsOverlay = arcgisHelper.createGraphicsOverlay(mapView);
            // 点击地图画点成形
            points = new ArrayList<>();
            mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mapView) {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
                    Point point = mMapView.screenToLocation(screenPoint);
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

}
