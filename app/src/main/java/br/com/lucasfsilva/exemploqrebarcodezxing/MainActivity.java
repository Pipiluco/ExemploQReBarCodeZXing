package br.com.lucasfsilva.exemploqrebarcodezxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btnScan;
    private TextView tvNome, tvEndereco;

    private IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvEndereco = (TextView) findViewById(R.id.tvEndereco);
        btnScan = (Button) findViewById(R.id.btnScan);

        intentIntegrator = new IntentIntegrator(this);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "Sem resultados!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result.getContents());
                    tvNome.setText(jsonObject.getString("nome"));
                    tvEndereco.setText(jsonObject.getString("endereco"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
