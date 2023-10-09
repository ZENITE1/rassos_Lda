package ao.znt.rassos_la.servicos_ussd;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.squareup.picasso.Picasso;

import ao.znt.rassos_la.R;

public class FaregroungService extends Service implements LifecycleObserver {

    private Button overlayedButton;
    private ImageButton overlayedIButton;
    private ProgressBar overlayerProgressBar;
    private WindowManager wm;

    public final static String EXTRA = "carregando . ..";
    private String tittle = null;

    private static boolean estado = false;
    public static boolean isServiceFareground(){
        return estado;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public static void isAppBackground(){
        Log.i("FaregroungService","Background");estado = false;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public static void isAppFareground(){
        Log.i("FaregroungService","Fareground");estado = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(EXTRA))
            tittle = intent.getStringExtra(EXTRA);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        }
        //overlayerProgressBar = new ProgressBar(getApplicationContext());
        overlayedIButton = new ImageButton(this);

        overlayedButton = new Button(this);
        overlayedButton.setText(tittle);
        overlayedButton.setAlpha(0.7f);
        overlayedButton.setBackgroundColor(Color.DKGRAY);
        overlayedButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,26);

        //overlayedIButton.setImageResource(R.drawable.twitter);
        Picasso.with(getApplicationContext()).load(R.drawable.twitter).into(overlayedIButton);//Carregar um gif

        WindowManager.LayoutParams params =
                new WindowManager.LayoutParams
                        (WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT,
                                LAYOUT_FLAG
                                ,
                                WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG |
                                      WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER;
        wm.addView(overlayedIButton,params);
        //wm.addView(overlayedButton, params);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("FaregroungService","onDestroy");
        isAppBackground();
        if (overlayedIButton != null) {
            wm.removeView(overlayedIButton);
            overlayedIButton = null;

        }
       /* if (overlayedButton != null) {
            wm.removeView(overlayedButton);
            overlayedButton = null;

        }*/
    }

}