package devandroid.wesley.appgaseta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import devandroid.wesley.appgaseta.R;
import devandroid.wesley.appgaseta.view.apoio.UtilGasEta;
import devandroid.wesley.appgaseta.view.controller.CombustivelController;
import devandroid.wesley.appgaseta.view.model.Combustivel;

public class MainActivity extends AppCompatActivity {
    List<Combustivel> dados;
    CombustivelController controller;
    EditText editGasolina;
    EditText editEtanol;
    TextView txtResultado;
    double precoGasolina;
    double precoEtanol;
    String recomendacao;
    Button btnCalcular;
    Button btnLimpar;
    Button btnSalvar;
    Button btnFinalizar;
    Combustivel combustivelGasolina;
    Combustivel combustivelEtanol;
    SharedPreferences preferences;

    SharedPreferences.Editor dadosPreferences;

    // psfs
    public static final String NOME_PREFERENCES = "pref_gaseta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        initiForm();

        controller = new CombustivelController(MainActivity.this);
        dados = controller.getListaDeDados();
        Combustivel obj =dados.get(1);
        obj.setNomeDoCombustivel("Macarrão");
        obj.setPrecoDoCombustivel(99);
        obj.setRecomendacao("Agua");
        controller.alterar(obj);
        Log.i("dados", "onCreate: ");

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            boolean isDadosOk = true;

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editGasolina.getText())) {
                    editGasolina.setError("* Obrigatório");
                    editGasolina.requestFocus();
                    isDadosOk = false;
                }

                if (TextUtils.isEmpty(editEtanol.getText())) {
                    editEtanol.setError("* Obrigatório");
                    editEtanol.requestFocus();
                    isDadosOk = false;
                }

                if (isDadosOk) {

                    precoGasolina = Double.parseDouble(editGasolina.getText().toString());
                    precoEtanol = Double.parseDouble(editEtanol.getText().toString());

                    recomendacao = UtilGasEta.calcularMelhorOpcao(precoGasolina, precoEtanol);
                    txtResultado.setText(recomendacao);
                    btnSalvar.setEnabled(true);


                } else {
                    btnSalvar.setEnabled(false);
                    Toast.makeText(MainActivity.this,
                            "Por favor, digite os dados obrigatórios...",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEtanol.setText("");
                editGasolina.setText("");
                btnLimpar.setEnabled(false);
                controller.limpar();

            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Volte Sempre", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cria um objeto do tipo comustivel para gasolina e outro apra etanol
                combustivelGasolina = new Combustivel();
                combustivelEtanol = new Combustivel();
                // copula o objeto com seus atributos
                combustivelGasolina.setNomeDoCombustivel("Gasolina");
                combustivelGasolina.setPrecoDoCombustivel(precoGasolina);
                combustivelEtanol.setNomeDoCombustivel("Etanol");
                combustivelEtanol.setPrecoDoCombustivel(precoEtanol);
                // Salva no objeto qual e a recomendação
                combustivelGasolina.setRecomendacao(UtilGasEta.calcularMelhorOpcao(precoGasolina, precoEtanol));
                combustivelEtanol.setRecomendacao(UtilGasEta.calcularMelhorOpcao(precoGasolina, precoEtanol));
                controller.salvar(combustivelGasolina);
            }
        });

    }

    private void initiForm() {
        editGasolina = findViewById(R.id.editGasolina);
        editEtanol = findViewById(R.id.editEtanol);

        txtResultado = findViewById(R.id.txtResultado);

        btnCalcular = findViewById(R.id.btnCalcular);

        btnLimpar = findViewById(R.id.btnLimpar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnFinalizar = findViewById(R.id.btnFinalizar);
    }


}