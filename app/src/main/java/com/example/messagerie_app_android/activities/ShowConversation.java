package com.example.messagerie_app_android.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messagerie_app_android.R;
import com.example.messagerie_app_android.api.APIClient;
import com.example.messagerie_app_android.api.APIInterface;
import com.example.messagerie_app_android.api.structure.ListMessages;
import com.example.messagerie_app_android.api.structure.Message;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowConversation extends AppCompatActivity implements View.OnClickListener{

    public EditText contentNewMessage;
    public Button btnSendNewMessage;
    String hash;
    String idConv;
    String idUser;
    String theme;
    Boolean active;
    APIInterface apiService;
    LinearLayout messagesLayout;
    ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //APIInterface apiService = APIClient.getClient().create(APIInterface.class);



        setContentView(R.layout.activity_show_conversation);

        contentNewMessage = findViewById(R.id.inputMessage);
        btnSendNewMessage = findViewById(R.id.sendMessage);
        scrollView = findViewById(R.id.scrollView2);
        btnSendNewMessage.setOnClickListener(this);

        Bundle b = this.getIntent().getExtras();
        hash = b.getString("hash");
        idConv = b.getString("idConv");
        theme = b.getString("theme");
        active = b.getBoolean("active");
        idUser =b.getString("idUser");

        Log.i("IG2I", hash);
        Log.i("IG2I", active.toString());
        Log.i("IG2I", idConv);
        Log.i("IG2I", theme);
        Log.i("IG2I", ""+idUser);


        /*Call<ListMessages> call1 = apiService.doGetListMessages(hash, Integer.parseInt(id));
        call1.enqueue(new Callback<ListMessages>() {
            @RequiresApi(api = Build.VERSION_CODES.S)

            @Override
            public void onResponse(Call<ListMessages> call, Response<ListMessages> response) {
                ListMessages listeMsgs = response.body();
                Log.i("IG2I", listeMsgs.toString());
                messagesLayout = (LinearLayout) findViewById(R.id.messages_zone);

                for (Message message : listeMsgs.getMessages()) {

                    Log.i("IG2I", message.toString());
                    TextView t = new TextView(ShowConversation.this);
                    t.setText("\n[" + message.getAuteur() + "] " + message.getContenu());
                    t.setTextColor(Color.parseColor(message.getCouleur()));
                    messagesLayout.addView(t);
                }
            }

            @Override
            public void onFailure(Call<ListMessages> call, Throwable t) {

            }
        });
        /*TextView labelConversation = findViewById(R.id.conversation_title);
        labelConversation.setText(theme);*/

        getMessages();


    }


    private void getMessages(){
        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListMessages> call1 = apiService.doGetListMessages(hash, Integer.parseInt(idConv)).clone();
        call1.enqueue(new Callback<ListMessages>() {
            @RequiresApi(api = Build.VERSION_CODES.S)

            @Override
            public void onResponse(Call<ListMessages> call, Response<ListMessages> response) {
                ListMessages listeMsgs = response.body();
                Log.i("IG2I", listeMsgs.toString());
                messagesLayout = (LinearLayout) findViewById(R.id.messages_zone);
                messagesLayout.removeAllViews();
                TextView t = new TextView(ShowConversation.this);

                for (Message message : listeMsgs.getMessages()) {

                    Log.i("IG2I", message.toString());
                    t = new TextView(ShowConversation.this);


                    if(Integer.parseInt(message.getUserId()) == Integer.parseInt(idUser)){
                        Log.i("IG2I", "droite");
                        t.setGravity(Gravity.RIGHT);
                    }
                    float r=20; // the border radius in pixel
                    ShapeDrawable shape = new ShapeDrawable (new RoundRectShape(new float[] { r, r, r, r, r, r, r, r },null,null));
                    shape.getPaint().setColor(Color.RED);
                    t.setBackground(shape);

                    /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                            250,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
*/


                    t.setText("\n[" + message.getAuteur() + "] " + message.getContenu());
                    //t.setBackgroundColor(Color.parseColor(message.getCouleur()));
                    t.setTextColor((Color.parseColor("#FFFFFF")));
                    //

                    //t.setLayoutParams(params);
                    messagesLayout.addView(t);
                }

                scrollView.scrollToDescendant(t);
            }

            @Override
            public void onFailure(Call<ListMessages> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.sendMessage:
                // TODO : il faudrait sauvegarder les identifiants dans les préférences
                Log.i("IG2I", "ENVOI");
                String newMessage = contentNewMessage.getText().toString();
                contentNewMessage.getText().clear();

                //GlobalState.hideKeyboard(this);


                //contentNewMessage.re
                //Log.i("IG2I", );
                apiService = APIClient.getClient().create(APIInterface.class);
                Call<Message> call1 = apiService.doPostMessages(hash, Integer.parseInt(idConv), newMessage);
                call1.enqueue(new Callback<Message>() {
                    @RequiresApi(api = Build.VERSION_CODES.S)

                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message messageAdded = response.body();
                        Log.i("IG2I",messageAdded.toString());
                        getMessages();

                    }
                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });

                break;
        }

        /*public static void hideKeyboard(Activity activity) {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }*/



    }
}

