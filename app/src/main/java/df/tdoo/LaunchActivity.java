package df.tdoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.auth0.android.jwt.JWT;

import java.util.Calendar;
import java.util.Date;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs=getSharedPreferences("preferences",MODE_PRIVATE);
        String authToken=prefs.getString("authToken","");

        if (!authToken.equals("")){
            JWT jwt=new JWT(authToken);
            Date expiresAt=jwt.getExpiresAt();
            if (Calendar.getInstance().getTime().before(expiresAt)){
                // go to MainNavigatorActivity
            } else {
                Intent intent=new Intent(this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }else {
            Intent intent=new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
