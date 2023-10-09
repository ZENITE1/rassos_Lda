package ao.znt.rassos_la.init;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import ao.znt.rassos_la.R;
public class CadasstroActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioCliente, radioComerciante;
    LinearLayout layoutProdutoDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadasstro);
        getSupportActionBar().hide();

        layoutProdutoDescricao = findViewById(R.id.layoutProdutoDescricao);
        radioGroup = findViewById(R.id.RadioGroupCadastro);
        radioCliente = findViewById(R.id.RadioButtomClienteCadastro);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(group.getCheckedRadioButtonId()==R.id.RadioButtomComercianteCadastro)
                    layoutProdutoDescricao.setVisibility(View.VISIBLE);
                else if(group.getCheckedRadioButtonId()==R.id.RadioButtomClienteCadastro)
                    layoutProdutoDescricao.setVisibility(View.GONE);
            }
        });

    }
}