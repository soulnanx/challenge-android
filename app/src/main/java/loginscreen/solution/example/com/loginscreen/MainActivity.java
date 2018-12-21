package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signUpBtn;
    private Button loginBtn;
    private Button createBtn;
    private Button signInBtn;

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText phone;

    private View contentName;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        init();
    }

    private void init() {
        loadValues();
        setEvents();
    }

    private void setEvents() {
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }


    private void loadValues() {
        signUpBtn = (Button) findViewById(R.id.bt_sign_in);
        loginBtn  = (Button) findViewById(R.id.bt_login);
        createBtn = (Button) findViewById(R.id.bt_create);
        signInBtn = (Button) findViewById(R.id.bt_signup);
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        phone = (EditText) findViewById(R.id.et_phone);

        contentName =  findViewById(R.id.lt_name);
        viewFlipper =  (ViewFlipper) findViewById(R.id.view_flipper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_sign_in:
                doLogin();
                break;
            case R.id.bt_login:
                showLoginValues();
                break;
            case R.id.bt_create:
                doCreate();
                break;
            case R.id.bt_signup:
                showCreateValues();
                break;

        }

    }

    private void doCreate() {

        if (isValidFields()){
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else if (isEmailInvalid()){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (isPasswordValid()){
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
        } else {
            createUser();
        }

    }

    private boolean isPasswordValid() {

        String passwordValue = password.getText().toString();
        Pattern p = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
        Matcher m = p.matcher(passwordValue);

        return !m.find();

    }

    private boolean isEmailInvalid() {
        String emailValue = email.getText().toString();
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(emailValue);

        return !m.find();
    }

    private void createUser() {
        String nameValue = name.getText().toString();
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        String phoneValue = phone.getText().toString();

        User user = new User(nameValue,
                emailValue,
                passwordValue,
                phoneValue);

        user.save(this);
        clear();
    }

    private void clear() {
        Toast.makeText(this, "The user was successfully created!", Toast.LENGTH_SHORT).show();
        clearFields();
        contentName.setVisibility(View.INVISIBLE);
        viewFlipper.showNext();
    }

    private void clearFields() {
        name.getText().clear();
//        email.getText().clear();
        password.getText().clear();
        phone.getText().clear();
    }

    private boolean isValidFields() {
        String nameValue = name.getText().toString();
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        String phoneValue = phone.getText().toString();

        return nameValue.isEmpty()
                || passwordValue.isEmpty()
                || emailValue.isEmpty()
                || phoneValue.isEmpty();
    }

    private void showCreateValues() {
        contentName.setVisibility(View.VISIBLE);
        viewFlipper.showNext();
    }

    private void showLoginValues() {
        contentName.setVisibility(View.INVISIBLE);
        viewFlipper.showNext();
    }

    private void doLogin() {
        if (isInvalidSignInFields()){
            Toast.makeText(this, "Email or Password id blank!", Toast.LENGTH_SHORT).show();
        } else if (isInvalidEmail()){
            Toast.makeText(this, "This email is invalid", Toast.LENGTH_SHORT).show();
        }

        if (validateCredentials()){
            Intent it = new Intent(this, LoginWelcomeActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(this, "Email or Password is invalid!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isInvalidEmail() {
        return false;
    }

    private boolean validateCredentials() {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();

        User user = User.load(this);

        return emailValue.equals(user.getEmail())
                && passwordValue.equals(user.getPassword());

    }

    private boolean isInvalidSignInFields() {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        return emailValue.isEmpty()
                || passwordValue.isEmpty();
    }
}
