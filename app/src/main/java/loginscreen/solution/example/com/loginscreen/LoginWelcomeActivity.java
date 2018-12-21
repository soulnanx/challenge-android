package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LoginWelcomeActivity extends AppCompatActivity {

    private User user;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(LoginWelcomeActivity.this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);
        init();
    }

    private void init() {
        loadValues();
    }

    private void loadValues() {
        user = User.load(this);
        ((TextView) findViewById(R.id.tv_name)).setText(user.getName());
        ((TextView) findViewById(R.id.tv_email)).setText(user.getEmail());
        ((TextView) findViewById(R.id.tv_phone)).setText(user.getPhone());
    }
}
