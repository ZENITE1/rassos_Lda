package ao.znt.rassos_la.init;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import ao.znt.rassos_la.MainActivity;
import ao.znt.rassos_la.R;
import ao.znt.rassos_la.utils.ZeniteUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import static ao.znt.rassos_la.utils.ZeniteUtil.EMAIL_KEY;
import static ao.znt.rassos_la.utils.ZeniteUtil.FOTO_KEY;
import static ao.znt.rassos_la.utils.ZeniteUtil.ID_KEY;
import static ao.znt.rassos_la.utils.ZeniteUtil.NAME_KEY;
import static ao.znt.rassos_la.utils.ZeniteUtil.SHARE_PREFERENCES;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    private ProgressBar progressBar;

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    String nome, email,id,perfil;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initComponet();

        sharedPreferences = getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);

        email = sharedPreferences.getString(EMAIL_KEY,null);
        nome = sharedPreferences.getString(NAME_KEY,null);
        id = sharedPreferences.getString(ID_KEY,null);
        perfil = sharedPreferences.getString(FOTO_KEY,null);

        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();

        findViewById(R.id.button_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginManager.logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("email", "public_profile", "user_birthday"));
            }
        });

        findViewById(R.id.BtnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login com dados manual
          }
        });
    }
   /* public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) { }
        catch (NoSuchAlgorithmException e) { }
    }*/
    private void initComponet(){
        edtEmail = findViewById(R.id.editTextConfirmacaoPass);
        edtSenha = findViewById(R.id.editTextTextLoginPassWord);
        progressBar = findViewById(R.id.LoginProgressBar);
    }
    public void irCadastro(View view){
        startActivity(new Intent(this,CadasstroActivity.class));
    }
    public void facebookLogin() {

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                if (object != null) {
                                                    try {

                                                        nome = object.getString("name");
                                                        email = object.getString("email");
                                                        id = object.getString("id");
                                                        perfil = "https://graph.facebook.com/"+id+"/picture?height=60&width=60";

                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString(EMAIL_KEY,email);
                                                        editor.putString(NAME_KEY,nome);
                                                        editor.putString(ID_KEY,id);
                                                        editor.putString(FOTO_KEY,perfil);
                                                        editor.apply();
                                                        //obter foto de perfil
                                                        disconnectFromFacebook();

                                                        ZeniteUtil.alertaSucesso(LoginActivity.this,email,"Bem-vindo "+nome,new SweetAlertDialog.OnSweetClickListener(){
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                                                finish();
                                                                sweetAlertDialog.dismissWithAnimation();
                                                            }
                                                        });
                                                        // do action after Facebook login success
                                                        // or call your API
                                                    }
                                                    catch (JSONException | NullPointerException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id, name, email, gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                Log.v("LoginScreen", "---onCancel");
                            }
                            @Override
                            public void onError(FacebookException error)
                            {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "+ error.getMessage());
                            }
                        });
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                    }
                }).executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // add this line
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(email != null && nome != null && id != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
}
