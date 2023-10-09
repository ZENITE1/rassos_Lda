package ao.znt.rassos_la.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ao.znt.rassos_la.MainActivity;
import ao.znt.rassos_la.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ZeniteUtil {


    public static final String SHARE_PREFERENCES = "rassos_la_preferences";
    public static final String EMAIL_KEY = "rassos_la_email_key";
    public static final String NAME_KEY = "rassos_la_name_key";
    public static final String ID_KEY = "rassos_la_id_key";
    public static final String FOTO_KEY = "rassos_la_foto_key";

    public static void alerta(Context context, String titulo,String mensagem){
        new SweetAlertDialog(context)
                .setTitleText(titulo)
                .setContentText(mensagem)
                .show();
    }
    public static void alertaConfirmacao(Context context, String titulo,String mensagem, SweetAlertDialog.OnSweetClickListener listener){
        new SweetAlertDialog(context)
                .setTitleText(titulo)
                .setContentText(mensagem)
                .setConfirmText("ok")
                .setConfirmClickListener(listener)
                .show();
    }
    public static void alertaErro(Context context, String titulo,String mensagem){
        new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE)
                .setTitleText(titulo)
                .setContentText(mensagem)
                .show();
    }
    public static void alertaAviso(Context context, String titulo,String mensagem){
        new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titulo)
                .setContentText(mensagem)
                .show();
    }
    public static void alertaSucesso(Context context, String titulo,String mensagem,SweetAlertDialog.OnSweetClickListener listener){
        new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(titulo)
                .setConfirmText("ok")
                .setConfirmClickListener(listener)
                .setContentText(mensagem)
                .show();
    }
    public static void alertaAvisoCanselAndConfirm(Context context, String titulo,String mensagem,String confirmText,String canselText,SweetAlertDialog.OnSweetClickListener listener){
        new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titulo)
                .setContentText(mensagem)
                .setConfirmText(confirmText)
                .setCancelText(canselText)
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .setConfirmClickListener(listener)
                .show();
    }
    public static void carregarImagen(Context context,String url,ImageView imageView, String id){

        Picasso picasso = Picasso.with(context);

        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("Success 1"," ********** online img baixada ***********");
                        armazenaEmCache(context,id,url);
                    }
                    @Override
                    public void onError() {
                        if(obterImageCache(context,id)!=null){
                            File file = obterImageCache(context,id);
                            picasso.load(file).into(imageView);
                            return;
                        }
                        picasso.load(url)
                                .error(R.drawable.avactar_perfil)
                                .into(imageView);
                    }
                });
    }
    private static void armazenaEmCache(Context context,String id,String url){
        Thread thread = new Thread(){
          public void run(){
              File file = new File(context.getExternalCacheDir().getAbsolutePath()+"/"+id+".jpg");
              try{
                  Bitmap bitmap = Picasso.with(context.getApplicationContext()).load(url).get();
                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                  Log.i("MEU Picasso","IMG armazenada em cache: "+context.getExternalCacheDir());
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
        };
        thread.start();
    }
    private static File obterImageCache(Context context, String id){
        List<File> files = new LinkedList<>(Arrays.asList(context.getExternalCacheDir().listFiles()));
        for (File file : files){
            if(file.getName().equals(id+".jpg")){
                Log.i("MEU Picasso","IMG obtida do cahe: "+file.getAbsolutePath());
                return file;
            }
        }
        return null;
    }

}
