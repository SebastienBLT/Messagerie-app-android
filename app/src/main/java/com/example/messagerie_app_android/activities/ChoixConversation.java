package com.example.messagerie_app_android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messagerie_app_android.GlobalState;
import com.example.messagerie_app_android.R;
import com.example.messagerie_app_android.api.APIClient;
import com.example.messagerie_app_android.api.APIInterface;
import com.example.messagerie_app_android.api.structure.Conversation;
import com.example.messagerie_app_android.api.structure.ListConversations;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoixConversation extends AppCompatActivity implements View.OnClickListener {

    GlobalState gs;
    public Spinner spinConversations;
    public Button btnChoixConv ;
    private String idUser;

    public class MyCustomAdapter extends ArrayAdapter<Conversation> {

        private int layoutId;
        private ArrayList<Conversation> dataConvs;

        public MyCustomAdapter(Context context,
                               int itemLayoutId,
                               ArrayList<Conversation> objects) {
            super(context, itemLayoutId, objects);
            layoutId = itemLayoutId;
            dataConvs = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            //return getCustomView(position, convertView, parent);
            // getLayoutInflater() vient de Android.Activity => il faut utiliser une classe interne
            LayoutInflater inflater = getLayoutInflater();
            View item = inflater.inflate(layoutId, parent, false);
            Conversation nextC = dataConvs.get(position);

            TextView label = (TextView) item.findViewById(R.id.conversation_title);
            label.setText(nextC.getTheme());

            ImageView icon = (ImageView) item.findViewById(R.id.conservation_icon);
            if (nextC.getActive()) icon.setImageResource(R.drawable.icon36);
            else icon.setImageResource(R.drawable.icongray36);

            return item;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return getCustomView(position, convertView, parent);
            LayoutInflater inflater = getLayoutInflater();
            View item = inflater.inflate(layoutId, parent, false);
            Conversation nextC = dataConvs.get(position);

            TextView label = (TextView) item.findViewById(R.id.conversation_title);
            label.setText(nextC.getTheme());

            ImageView icon = (ImageView) item.findViewById(R.id.conservation_icon);
            if (nextC.getActive()) icon.setImageResource(R.drawable.icon);
            else icon.setImageResource(R.drawable.icongray);
            return item;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_conversation);
        gs = (GlobalState) getApplication();
        Bundle bdl = this.getIntent().getExtras();
        gs.alerter("hash : " + bdl.getString("hash"));
        String hash = bdl.getString("hash");
        idUser = bdl.getString("idUser");
        Log.i("IG2I", idUser+"");

        btnChoixConv = findViewById(R.id.btnChoixConv);
        btnChoixConv.setOnClickListener(this);

        APIInterface apiService = APIClient.getClient().create(APIInterface.class);

        Call<ListConversations> call1 = apiService.doGetListConversation(hash);
        call1.enqueue(new Callback<ListConversations>() {
            @Override
            public void onResponse(Call<ListConversations> call, Response<ListConversations> response) {
                ListConversations listeConvs = response.body();
                Log.i(gs.CAT,listeConvs.toString());

                spinConversations = (Spinner) findViewById(R.id.spinConversations);

                //ArrayAdapter<Conversation> dataAdapter = new ArrayAdapter<Conversation>(
                //        ChoixConversationActivity.this,
                //        android.R.layout.simple_spinner_item,
                //        listeConvs.getConversations());
                //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //spinConversations.setAdapter(dataAdapter);
                spinConversations.setAdapter(new MyCustomAdapter(
                        ChoixConversation.this,
                        R.layout.spinner_item,
                        listeConvs.getConversations()));




            }

            @Override
            public void onFailure(Call<ListConversations> call, Throwable t) {
                call.cancel();
            }


        });



    }


    public void onClick(View view) {

        gs.alerter(spinConversations.toString() );
        Log.i(gs.CAT, spinConversations.getSelectedItem().toString());

        Conversation conv = (Conversation) spinConversations.getSelectedItem();
        Log.i(gs.CAT, conv.getId());

        Bundle bdl = this.getIntent().getExtras();
        String hash = bdl.getString("hash");
        Log.i(gs.CAT, hash);

        Intent myIntent;
        Bundle myBdl = new Bundle();
        myBdl.putString("idConv", conv.getId());
        myBdl.putString("theme", conv.getTheme());
        myBdl.putBoolean("active", conv.getActive());
        myBdl.putString("hash", hash);
        myBdl.putString("idUser", idUser);


        myIntent = new Intent(ChoixConversation.this,ShowConversation.class);
        myIntent.putExtras(myBdl);
        startActivity(myIntent);
    }
}
