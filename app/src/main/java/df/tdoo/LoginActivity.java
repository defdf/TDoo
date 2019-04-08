package df.tdoo;

import androidx.appcompat.app.AppCompatActivity;
import df.tdoo.Network.APICalls;
import df.tdoo.Utilities.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private TextView createAccount;
    private EditText email;
    private EditText password;
    private CheckBox rememberMe;

    private Context context;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        login = findViewById(R.id.login_button);
        createAccount = findViewById(R.id.login_button_create_account);
        email = findViewById(R.id.login_textfield_email);
        password = findViewById(R.id.login_textfield_password);
        rememberMe = findViewById(R.id.remember_me_box);

        prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        boolean rememberMeBool = prefs.getBoolean("rememberMe", false);
        if (rememberMeBool) {
            String usernameOrEmail = prefs.getString("usernameOrEmail", "");
            String passwordString = prefs.getString("password", "");

            email.setText(usernameOrEmail);
            password.setText(passwordString);
            rememberMe.setChecked(true);
        }

        createAccount.setOnClickListener(view->{
            // create account fragment
            //. addToBackStack("create_account");
        });

        login.setOnClickListener(view -> {
            try{
                performLogin();
            }catch(JSONException e){
                e.printStackTrace();
            }
        });
    }

    private void performLogin() throws JSONException{
        APICalls api= new APICalls();
        String usernameOrEmail=email.getText().toString();
        String passwordString=password.getText().toString();

        if (rememberMe.isChecked()){
            prefs.edit().putString("usernameOrEmail",usernameOrEmail)
                    .putString("password",passwordString)
                    .putBoolean("rememberMe",true)
                    .apply();
        }else {
            prefs.edit().putBoolean("rememberMe",false)
                    .remove("usernameOrEmail")
                    .remove("password")
                    .apply();
        }

        JSONObject loginDetails=new JSONObject();
        loginDetails.put("usernameOrEmail", usernameOrEmail);
        loginDetails.put("password",passwordString);

        String loginUrl= Constants.BASE_URL+"user/login";
        api.post(loginDetails.toString(), loginUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    SharedPreferences prefs=getSharedPreferences("preferences",MODE_PRIVATE);
                    String responseString=response.body().string();

                    try {
                        JSONObject jsonObject=new JSONObject(responseString);
                        String authToken=jsonObject.getString("token");
                        prefs.edit().putString("authToken",authToken).apply();

                        Intent intent=new Intent(context, MainNavigatorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
