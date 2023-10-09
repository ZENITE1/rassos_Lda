package ao.znt.rassos_la.servicos_ussd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import ao.znt.rassos_la.R;
import ao.znt.rassos_la.utils.ZeniteUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ServicesUtisl {

    private String TAG = ao.znt.rassos_la.MainActivity.class.getSimpleName();

   public boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" + USSDService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
             } catch (Settings.SettingNotFoundException e) {
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "********* O SERVÇO DE ACESSIBILIDADE ESTÁ ABILITADO ************");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "********** O SERVÇO DE ACESSIBILIDADE ESTÁ DESABILITADO *******");
        }

        return false;
    }
    public void showAviso(Activity activity){
        ZeniteUtil.alertaConfirmacao(activity, "Permisão de acessibilidade", "Para o correto funcionamento do recurso de pagamento do Rassos_la, precisas ablitar a permisão de acessibilidade do serviço: " + activity.getString(R.string.app_name), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                activity.startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
            }
        });
    }
}

