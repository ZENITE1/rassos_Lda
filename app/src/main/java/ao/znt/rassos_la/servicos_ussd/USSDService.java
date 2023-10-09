package ao.znt.rassos_la.servicos_ussd;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import ao.znt.rassos_la.MainActivity;


public class USSDService extends AccessibilityService{
    public static String TAG = USSDService.class.getSimpleName();
    private static AccessibilityEvent event;

    /*private static boolean isAppFareground(Context context){
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if(keyguardManager.isKeyguardLocked()){
            return false;
        }
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list;
        if((list = activityManager.getRunningAppProcesses()) != null){
            for (ActivityManager.RunningAppProcessInfo aList : list){
                ActivityManager.RunningAppProcessInfo info;
                if ((info = aList).pid == myPid){
                    return info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
                }
            }
        }
        return false;
    }*/
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        this.event = event;

        if(USSDController.instance != null){

            if(!MainActivity.isActivityBackground()||FaregroungService.isServiceFareground()){
                if (hasProblem(event) || LogView(event)) {
                    clickOnButton(event, USSDController.LABEL_OK);
                    USSDController.instance.callbackInvoke.over(event.getText().get(0).toString());
                } else if (isUSSDNeed(event)) {//tem uma janela de dialog
                    String response = event.getText().get(0).toString();//pega o  texto que tem nele
                    if (response.contains("\n")) {
                        response = response.substring(response.indexOf('\n') + 1);
                        Log.i(TAG, response);
                    }
                    if (notInputText(event)) {//nao tem EditText
                        clickOnButton(event, USSDController.LABEL_OK);//clica ok
                        USSDController.instance.callbackInvoke.over(response);//pega a resposta
                        Log.i(TAG, response);
                    } else {
                        // sent option 1
                        if (USSDController.instance.callbackMessage == null)
                            USSDController.instance.callbackInvoke.responseInvoke(response);
                        else {
                            USSDController.instance.callbackMessage.responseMessage(response);
                            USSDController.instance.callbackMessage = null;
                        }
                    }
                } else if (LogView(event) && notInputText(event)) { //se náo tem um inputText e a view está a demorar
                    clickOnButton(event, USSDController.LABEL_OK);
                    USSDController.instance.callbackInvoke.over(event.getText().get(0).toString());
                }
            }else {Log.i("APPPPPP","APP em BackGround");
            }
        }else {
            Log.i("APPPPPPP","O APP nao esta aberto a requisisao está a ser feita da chamada");
        }
    }
    public static void send(String text) {
        setTextIntoField(event, text);
        clickOnButton(event, USSDController.LABEL_SEND);//verificar a linguagem aqui. Agora a linguagem se adapta de acordo cam a do sistema
    }
    public static void cansel() {
        clickOnButton(event, USSDController.LABEL_CANCELAR);//verificar a linguagem aqui. Agora a linguagem se adapta de acordo cam a do sistema
    }
    private static void setTextIntoField(AccessibilityEvent event, String data) {//alterar dados dentro do editText
        USSDController ussdController = USSDController.instance;
        Bundle arguments = new Bundle();
        arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, data);
        for (int i = 0; i < event.getSource().getChildCount(); i++) {
            AccessibilityNodeInfo node = event.getSource().getChild(i);
            Log.d(TAG, i + ":" + node.getClassName());
            if (node != null && node.getClassName().equals("android.widget.EditText")
                    && !node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)) {
                ((ClipboardManager) ussdController.context
                        .getSystemService(Context.CLIPBOARD_SERVICE))
                        .setPrimaryClip(ClipData.newPlainText("text", data));
                node.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
        }
    }
    private static boolean notInputText(AccessibilityEvent event) {
        boolean flag = true;
        for (int i = 0; i < event.getSource().getChildCount(); i++) {
            AccessibilityNodeInfo node = event.getSource().getChild(i);
            if (node != null && node.getClassName().equals("android.widget.EditText"))
                flag = false;
        }
        return flag;
    }
    private boolean isUSSDWidget(AccessibilityEvent event) {//verificar se abriu uma janela de dialog
        return event.getClassName().equals("android.app.AlertDialog");
    }
    private boolean isUSSDNeed(AccessibilityEvent event) {
        return isUSSDWidget(event);
        //&& (event.getText().get(0).toString().contains(":"));
    }
    private boolean LogView(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && ((event.getText().get(0).toString().toLowerCase().contains(USSDController.REQUEST_ESPERE)
               // || (event.getText().get(0).toString().toLowerCase().contains("waint"))
        ));
    }
    protected boolean hasProblem(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && (event.getText().get(0).toString().toLowerCase().contains("problema")//PARA INTERNACIONALIZAR
                || event.getText().get(0).toString().toLowerCase().contains("desconocido")//PARA INTERNACIONALIZAR
                || event.getText().get(0).toString().toLowerCase().contains("problem")//PARA INTERNACIONALIZAR
                ||event.getText().get(0).toString().toLowerCase().contains("mmi inválido")//PARA INTERNACIONALIZAR
                ||event.getText().get(0).toString().toLowerCase().contains("invalid mmi code"));//PARA INTERNACIONALIZAR
    }
    private static void clickOnButton(AccessibilityEvent event, String label) {
        if (event.getSource() != null) {
            for (AccessibilityNodeInfo nodeButton
                    : event.getSource().findAccessibilityNodeInfosByText(label)) {
                Log.i("ALFREDO: ",""+nodeButton.getClassName());
                nodeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }
    @Override
    public void onInterrupt() {
        Log.d(TAG, "SERVICO INTERRONPIDO");
    }
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "SERVIÇO CANSELADO");
    }
}