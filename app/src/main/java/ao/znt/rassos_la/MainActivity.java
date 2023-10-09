package ao.znt.rassos_la;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ao.znt.rassos_la.init.LoginActivity;
import ao.znt.rassos_la.servicos_ussd.FaregroungService;
import ao.znt.rassos_la.servicos_ussd.USSDController;
import ao.znt.rassos_la.servicos_ussd.USSDService;
import ao.znt.rassos_la.utils.ZeniteUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static ao.znt.rassos_la.utils.ZeniteUtil.*;
import static ao.znt.rassos_la.utils.ZeniteUtil.EMAIL_KEY;
import static ao.znt.rassos_la.utils.ZeniteUtil.ID_KEY;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    private static final int IMAGE_GALERY_REQUEST = 1;
    //RecyclerView.Adapter recycleAdapter;
    //RecyclerView recyclerView;
    private String TAG = MainActivity.class.getSimpleName();
    LinearLayout linearLayoutCarteiraKwanza, linearLayoutCarteiraMonay;
    ImageView imageViewPerfil, imageViewPost;
    TextView txtNome;
    SharedPreferences sharedPreferences;
    String nome, email, id, imagemPerfil;
    private static boolean estado;
    private StringBuilder registro = new StringBuilder();//GUARDAR A CADA ONDESTROY

    public static boolean isActivityBackground(){
        return estado;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public static void isAppBackground(){ estado = true; }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public static void isAppFareground(){
        estado = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashbord_perfil);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        sharedPreferences = getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            nome = sharedPreferences.getString(NAME_KEY, null);
            email = sharedPreferences.getString(EMAIL_KEY, null);
            id = sharedPreferences.getString(ID_KEY, null);
            imagemPerfil = sharedPreferences.getString(FOTO_KEY, null);
        }

        txtNome = findViewById(R.id.textView1);
        imageViewPost = findViewById(R.id.imageView3);
        ImageView imageView = findViewById(R.id.imageView13);
        imageViewPerfil = findViewById(R.id.imageView1);
        linearLayoutCarteiraKwanza = findViewById(R.id.carteiraEKanza);
        linearLayoutCarteiraMonay = findViewById(R.id.carteiraMonay);

        txtNome.setText(nome);

        carregarImagen(MainActivity.this,imagemPerfil,imageViewPerfil,id);
      //  recyclerView = findViewById(R.id.recycleViewCards);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(linearLayoutManager);
        /*ArrayList<Cartao> cartaos = new ArrayList<>();
        cartaos.add(new Cartao(922922854, Cartao.QUALQUER, Cartao.E_KWANZA, 1234));
        cartaos.add(new Cartao(922922859, Cartao.QUALQUER, Cartao.ATLANTICO, 1234));
        cartaos.add(new Cartao(922922857, Cartao.UNITEL, Cartao.MONAY, 1234));

        CartaoViewAdapter adapter = new CartaoViewAdapter(cartaos);
        recyclerView.setAdapter(adapter);

        CartaoViewAdapter.ViewHolder cv = new CartaoViewAdapter.ViewHolder(recyclerView, new CartaoViewAdapter.ViewHolder.onItemClickListener() {
            @Override
            public void onRecyclerItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "clicado " + position, Toast.LENGTH_SHORT).show();
            }
        });*/
        imageViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ComerciantesActivity.class));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaAvisoCanselAndConfirm(MainActivity.this, "Log out", "Deseja sair da App?", "Sim", "Não", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            }
        });
        /* Código para carregar imagem apartir da galeria*/
        /*imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,getString(R.string.app_name)),IMAGE_GALERY_REQUEST);
            }
        });
        //CARREGAR IMAGEM DO FACEBOOK
        ZeniteUtil.carregarImagen(MainActivity.this,imagemPerfil,imageViewPerfil,id);

    }
    private void saveImg(Bitmap bitmap){

        if(imageViewPerfil != null){
                imageViewPerfil.setImageBitmap(bitmap);
            }
    }
    public String getImagePath(Uri uri){
        String[] campos = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, campos,null,null,null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }
    */
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_GALERY_REQUEST && resultCode == RESULT_OK){
            new LoadImageTask(this).execute(data.getData());
        }
    }
    class LoadImageTask  extends AsyncTask {
        WeakReference weakReference;
        public LoadImageTask(MainActivity activity){
            this.weakReference = new WeakReference(activity);

        }
        public MainActivity getActivity(){
            return (MainActivity) weakReference.get();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

    Uri uri = (Uri)objects[0];
    String caminhoFoto = getImagePath(uri);
    int targetH = imageViewPerfil.getHeight();
    int targetW = imageViewPerfil.getWidth();
    BitmapFactory.Options bOptions = new BitmapFactory.Options();
    bOptions.inJustDecodeBounds = true;
    String nomeFoto = "perfil";
            BitmapFactory.decodeFile(caminhoFoto,bOptions);
    int photoW = bOptions.outWidth;
    int photoH = bOptions.outHeight;
    int scaleFactor = Math.min(photoW/targetW,photoH/targetH);

    bOptions.inJustDecodeBounds = false;
    bOptions.inSampleSize = scaleFactor;
    bOptions.inPurgeable = true;

            if (getActivity() != null){
        return BitmapFactory.decodeFile(caminhoFoto,bOptions);
    }
            return null;
}
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(getActivity() != null){
            getActivity().saveImg((Bitmap) o);
        }
    }
}*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void showMenuCarteira(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.carteira_menu,popupMenu.getMenu());
        popupMenu.setGravity(Gravity.CENTER);


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.carteira_change_data:
                        Toast.makeText(MainActivity.this, "Alterar dados", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.carteira_consulta:
                        pedirConfirmacao(view.getId());
                        Toast.makeText(MainActivity.this, "Consultar saldo", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    private void pedirConfirmacao(int viewIdCarteira){
        if(isAccessibilitySettingsOn(MainActivity.this)){//verificar se a acessibilidade esta on
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view = layoutInflater.inflate(R.layout.confirmacao,null);

            final EditText editTextBip = (EditText)  view.findViewById(R.id.editTextConfirmacaoPass);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(view);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("canselar",(dialog, which) -> {dialog.dismiss();});
            alertDialogBuilder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String bip = editTextBip.getText().toString();// Verificar se nao está vasia TODO
                    consultaSaldo(bip,viewIdCarteira);
                    //alerta(MainActivity.this,"Saldo disponivel","KZ");
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else {
            showPermisaoDeAcessibilidade(this);
        }
    }
    private void consultaSaldo(String bip, int carteira){
            switch (carteira){
                case R.id.carteiraEKanza: consultaSaldoEkwansa(bip);
                break;
                case R.id.carteiraMonay: consultaSaldoMonay(bip);
                break;
            }

    }
    private void consultaSaldoEkwansa(String bip) {
        final USSDController ussdController = USSDController.getInstance(MainActivity.this);
        registro.append("REGISTRO DE OPERAÇÃO REALIZADA AOS: "+getDataFormatada()+".\n");
        String number = "*123#".toString().trim();
/*FAREGROUND CAREGANDO ...*/
        final Intent svc = new Intent(MainActivity.this, FaregroungService.class);
        svc.putExtra(FaregroungService.EXTRA,"PROCESANDO ...");
        MainActivity.this.startService(svc);

        ussdController.callUSSDInvoke(number, new USSDController.CallbackInvoke() {
            @Override
            public void responseInvoke(String message) {
                registro.append("\n****\n" + message);

                ussdController.send("2", new USSDController.CallbackMessage() {
                    @Override
                    public void responseMessage(String message) {
                        registro.append("\n****\n" + message);
                        ussdController.send("1", new USSDController.CallbackMessage() {
                            @Override
                            public void responseMessage(String message) {
                                ussdController.cansel();
                                getApplicationContext().stopService(svc);

                            }
                        });

                    }
                });

            }

            @Override
            public void over(String message) {
                registro.append("\n \n \n" + message+"  FIM DE OPERAÇÃO ");
                getApplicationContext().stopService(svc);
                alerta(MainActivity.this, "Resultado da Consulta",message);

            }
        });


    }
    private void consultaSaldoMonay(String bip){
        alerta(MainActivity.this,"TODO","TODO");
    }
    private String getDataFormatada(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + USSDService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
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
                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
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
    public void showPermisaoDeAcessibilidade(Activity activity){
        ZeniteUtil.alertaConfirmacao(activity, "Permisão de acessibilidade", "Para o correto funcionamento do recurso de pagamento do Rassos_la, precisas ablitar a permisão de acessibilidade do serviço: " + activity.getString(R.string.app_name), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                activity.startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

}