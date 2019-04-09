package df.tdoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import df.tdoo.Utilities.DialogHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainNavigatorActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =menuItem -> {
        switch (menuItem.getItemId()){
            case R.id.bottom_navigator_profile:
                //loadFragment(new ProfileFragment());
                return true;
            case R.id.bottom_navigator_todo:
                //loadFragment(new TodoFragment());
                return true;
            case R.id.bottom_navigator_search:
                //loadFragment(new SearchFragment());
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigator);

        BottomNavigationView navigation=findViewById(R.id.main_navigator);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //loadFragment(new TodoFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out :
                DialogInterface.OnClickListener onClickListener = (dialogInterface, choice) -> {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE :
                            SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
                            prefs.edit().remove("authToken").apply();

                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialogInterface.cancel();
                            break;
                    }
                };

                DialogHelper helper = new DialogHelper();
                helper.createYesNoAlert("Are you sure you want to log out?",
                        this,
                        onClickListener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
