package com.example.messagerie_app_android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messagerie_app_android.GlobalState;
import com.example.messagerie_app_android.R;
import com.example.messagerie_app_android.api.APIClient;
import com.example.messagerie_app_android.api.APIInterface;
import com.example.messagerie_app_android.api.structure.ConnectionStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    public EditText edtLogin;
    public EditText edtPasse;
    public Button btnLogin;
    public CheckBox cbRemember;
    public SharedPreferences sp;
    public GlobalState gs;
    public APIInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLogin = findViewById(R.id.edtLogin);
        edtPasse = findViewById(R.id.edtPasse);
        btnLogin = findViewById(R.id.btnLogin);
        cbRemember = findViewById(R.id.cbRemember);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        btnLogin.setOnClickListener(this);
        cbRemember.setOnClickListener(this);
        gs = (GlobalState) getApplication();
        apiService= APIClient.getClient().create(APIInterface.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Si le réseau est disponible, alors on réactive le bouton OK
        btnLogin.setEnabled(gs.verifReseau());

        // relire les préférences de l'application
        // mettre à jour le formulaire
        if (sp.getBoolean("remember",false)) {
            // on charge automatiquement les champs login/passe
            // on coche la case
            edtLogin.setText(sp.getString("login",""));
            edtPasse.setText(sp.getString("passe",""));
            cbRemember.setChecked(true);
        } else {
            // on vide
            edtLogin.setText("");
            edtPasse.setText("");
            cbRemember.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings: gs.alerter("Préférences");

                // Changer d'activité pour afficher SettingsActivity
                Intent toSettings = new Intent(this,Settings.class);
                startActivity(toSettings);


                break;
            case R.id.action_account: gs.alerter("Compte");break;

        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onClick(View view) {

        SharedPreferences.Editor editor = sp.edit();
        switch (view.getId()) {
            case R.id.btnLogin:
                // TODO : il faudrait sauvegarder les identifiants dans les préférences
                gs.alerter("click OK");
                //gs.requeteGET("http://tomnab.fr/fixture/","");
                //JSONAsyncTask reqGET = new JSONAsyncTask();
                //reqGET.execute("http://tomnab.fr/fixture/","cle=valeur");

                String passe=edtPasse.getText().toString();
                String login=edtLogin.getText().toString();
                Log.i("IG2I", login+" "+passe);

                Call<ConnectionStatus> call1 = apiService.doLogin(login, passe);
                call1.enqueue(new Callback<ConnectionStatus>() {
                    @RequiresApi(api = Build.VERSION_CODES.S)

                    @Override
                    public void onResponse(Call<ConnectionStatus> call, Response<ConnectionStatus> response) {
                        ConnectionStatus status = response.body();
                        Log.i("IG2I",status.toString());
                        Log.i("IG2I", "+++++"+status.getId());


                        Intent versChoixConv = new Intent(Login.this,ChoixConversation.class);
                        Bundle bdl = new Bundle();
                        bdl.putString("hash",status.getHash());
                        bdl.putString("idUser",status.getId());
                        versChoixConv.putExtras(bdl);
                        startActivity(versChoixConv);



                    }
                    @Override
                    public void onFailure(Call<ConnectionStatus> call, Throwable t) {

                    }
                });



                break;
            case R.id.cbRemember:
                if (cbRemember.isChecked()) {
                    // on sauvegarde tout
                    editor.putBoolean("remember", true);
                    editor.putString("login",edtLogin.getText().toString());
                    editor.putString("passe",edtPasse.getText().toString());
                } else {
                    // on oublie tout
                    editor.putBoolean("remember", false);
                    editor.putString("login","");
                    editor.putString("passe","");
                }

                break;
        }
        editor.commit();
    }
}
