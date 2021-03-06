/**
 * Created by johankasperi 2018-02-09.
 */

package se.bonniernews.rn3d;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.widget.RelativeLayout;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.util.DisplayMetrics;
import android.graphics.Color;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.andresoviedo.app.model3D.view.ModelSurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RN3DView extends RelativeLayout {
    public ModelSurfaceView modelView;

    private Context context;
    private String modelSrc;
    private String textureSrc;
    private float[] backgroundColor;
    private float scale = 1f;

    public RN3DView(Context context) {
        super(context);
        this.context = context;
    }

    private void tryInitScene() {
        if (context != null && modelSrc != null && textureSrc != null && backgroundColor != null) {
            this.modelView = new ModelSurfaceView(context, this, modelSrc, textureSrc, backgroundColor);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            this.modelView.setLayoutParams(layoutParams);
            this.modelView.setScale(this.scale);
            addView(this.modelView);
        }
    }

    /**
     * React props
     */
    public void setModelSrc(final String modelSrc) {
        this.modelSrc = modelSrc;
        this.tryInitScene();
    }

    public void setTextureSrc(final String textureSrc) {
        this.textureSrc = textureSrc;
        this.tryInitScene();
    }

    public void setBackgroundColor(final Integer color) {
        if (color == null) {
            return;
        }
        float red = (float) Color.red(color) / 255f;
        float green = (float) Color.green(color) / 255f;
        float blue = (float) Color.blue(color) / 255f;
        float alpha = (float) Color.alpha(color) / 255f;

        this.backgroundColor = new float[] {red, green, blue, alpha};
        this.tryInitScene();
    }

    public void setScale(float scale) {
        this.scale = scale;
        if (this.modelView != null) {
            modelView.setScale(scale);
        }
    }

    /**
     * React events
     */
    public void loadModelSuccess() {
        WritableMap event = Arguments.createMap();
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class)
                .receiveEvent(getId(), "loadModelSuccess", event);
    }

    public void loadModelError() {
        WritableMap event = Arguments.createMap();
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class)
                .receiveEvent(getId(), "loadModelError", event);
    }
}