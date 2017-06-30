package walden.com.appwidgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String TAG = "MyAppWidgetProvider";
    public static final String CLICK_ACTION = "com.walden.appwidgettest.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: ");
        if (intent.getAction().equals(CLICK_ACTION)) {
            Log.d(TAG, "onReceive: equals");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcbBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a20);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteView.setImageViewBitmap(R.id.iv_1, rotateBitmap(context, srcbBitmap, degree));
                        if (degree == 0) {
                            remoteView.setTextViewText(R.id.appwidget_tv, "投呗");
                        } else if(degree <1){

                        }else {
                            remoteView.setTextViewText(R.id.appwidget_tv, "我是假的~");
                        }
                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
                        remoteView.setOnClickPendingIntent(R.id.iv_1, pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteView);
                        SystemClock.sleep(30);
                    }

                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate: ");
        final int counter = appWidgetIds.length;
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted: ");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisabled: ");
    }


    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_1, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }


    private Bitmap rotateBitmap(Context context, Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        //matrix.setSinCos(1,1);
        matrix.setRotate(degree);
        Bitmap temBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return temBitmap;

    }
}
