package com.thxx.ydzrzy.listener;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;

import com.thxx.ydzrzy.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.maps.AndroidJni;
import com.tianditu.maps.Texture.UtilTextureDrawable;

import javax.microedition.khronos.opengles.GL10;

import static com.tianditu.maps.Texture.UtilTextureBase.BoundType.BOUND_TYPE_CENTER;

public class TdtLocationOverlay extends MyLocationOverlay {

    private Context context;

    public TdtLocationOverlay(Context context, MapView mapView) {
        super(context, mapView);
        this.context = context;
    }

    @Override
    protected void drawMyLocation(GL10 gl, MapView mapView, Location lastFix, GeoPoint myLocation, long when) {
        //获得屏幕坐标
        Point point = new Point();
        mapView.getProjection().toPixels(myLocation, point);
        //默认精度
        float accuracy = getAccuracy();
        //指定精度
        accuracy = 500;
        //获得实际误差距离
        float distance = mapView.getProjection().metersToEquatorPixels(accuracy);
        AndroidJni.OpenglFillRound(point.x, point.y, (int) distance, 0, 360, 137, 170, 213, 77);
        //创建Drawable
        UtilTextureDrawable drawable = new UtilTextureDrawable(context, R.drawable.locate_onmap, BOUND_TYPE_CENTER);
        drawable.DrawTexture(gl, point, 0.0F);
    }

}
