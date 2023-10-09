package ao.znt.rassos_la.servicos_ussd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import ao.znt.rassos_la.R;
import ao.znt.rassos_la.utils.ZeniteUtil;

public class USSDController {
        protected static ao.znt.rassos_la.servicos_ussd.USSDController instance;

    public static String LABEL_SEND = "";
    public static String LABEL_CANCELAR = "";
    public static String LABEL_OK = "";
    public static String REQUEST_ESPERE = "";



        public Activity context;
        protected CallbackInvoke callbackInvoke;
        protected CallbackMessage callbackMessage;
        protected ServicesUtisl servicesUtisl = new ServicesUtisl();
        public static ao.znt.rassos_la.servicos_ussd.USSDController getInstance(Activity activity) {
            LABEL_CANCELAR = activity.getResources().getString(R.string.LABEL_CANCEL);
            LABEL_SEND = activity.getResources().getString(R.string.LABEL_SEND);
            LABEL_OK = activity.getResources().getString(R.string.LABEL_OK);
            REQUEST_ESPERE = activity.getResources().getString(R.string.REQUEST_ESPERE);
            if (instance == null)
                instance = new ao.znt.rassos_la.servicos_ussd.USSDController(activity);
            return instance;
        }

        private USSDController(Activity activity) {
            context = activity;
        }

        @SuppressLint("MissingPermission")
        public void callUSSDInvoke(String ussdPhoneNumber, CallbackInvoke callbackInvoke) {
            this.callbackInvoke = callbackInvoke;

            if (ussdPhoneNumber.isEmpty()) {
                callbackInvoke.over("número errado");
                return;
            }
            if (servicesUtisl.isAccessibilitySettingsOn(context)) {
                String uri = Uri.encode("#");
                if (uri != null)
                    ussdPhoneNumber = ussdPhoneNumber.replace("#", uri);
                Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);
                if (uriPhone != null)
                    context.startActivity(new Intent(Intent.ACTION_CALL, uriPhone));
            }else {
                ZeniteUtil.alertaAviso(context,"Acessibilidade", "Precisas activar o serviço: "+context.getString(R.string.app_name));
            }
        }
        public void send(String text, CallbackMessage callbackMessage){
            this.callbackMessage = callbackMessage;
            USSDService.send(text);
        }
        public void cansel(){
            USSDService.cansel();
        }

        public interface CallbackInvoke {
            void responseInvoke(String message);
            void over(String message);
        }

        public interface CallbackMessage {
            void responseMessage(String message);
        }
    }