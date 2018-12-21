package loginscreen.solution.example.com.loginscreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

public class User implements Serializable {

    private String name;
    private String email;
    private String password;
    private String phone;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void save(final Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        SharedPreferences preferencesReader = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesReader.edit();
        editor.putString("user", json);
        editor.commit();
    }

    static public User load(final Context context) {
        SharedPreferences preferencesReader = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = preferencesReader.getString("user", "");
        return new Gson().fromJson(json, User.class);
    }

}
