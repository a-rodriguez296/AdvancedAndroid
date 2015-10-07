package arf.com.everpobre;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by arodriguez on 9/29/15.
 * Esto es como una especie de appDelegate
 */
public class EverpobreApp extends Application{


    //El contexto es lo que me permite tener acceso a los recursos del sistema. Archivos, etc
    private static WeakReference<Context> sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(EverpobreApp.class.getCanonicalName(), getString(R.string.log_everpobre_starting));

        sContext = new WeakReference(getApplicationContext()) ;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.d(EverpobreApp.class.getCanonicalName(), getString(R.string.log_everpobre_low_memory));
    }


    public static Context getAppContext(){
        return sContext.get();
    }
}
