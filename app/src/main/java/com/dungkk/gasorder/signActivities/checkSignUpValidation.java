package com.dungkk.gasorder.signActivities;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.dungkk.gasorder.R;

public class checkSignUpValidation implements Runnable {

    private Thread t_check;
    private String threadName = "checkSignUpValidation";

    private EditText et_firstName;
    private EditText et_lastName;
    private EditText et_username;
    private EditText et_password;
    private EditText et_password2;
    private EditText et_phoneNumber;
    private EditText et_email;
    private Button btn_signup;

    private int running = 0;

    public checkSignUpValidation(EditText et_firstName, EditText et_lastName, EditText et_username, EditText et_password, EditText et_password2, EditText et_phoneNumber, EditText et_email, Button btn_signup) {
        this.et_firstName = et_firstName;
        this.et_lastName = et_lastName;
        this.et_username = et_username;
        this.et_password = et_password;
        this.et_password2 = et_password2;
        this.et_phoneNumber = et_phoneNumber;
        this.et_email = et_email;
        this.btn_signup = btn_signup;
    }

    @Override
    public void run() {

        while (running != 1)
        {
            if(et_firstName.getText().toString().trim().equals("") && et_lastName.getText().toString().trim().equals("") && et_username.getText().toString().trim().equals("") && et_password.getText().toString().trim().equals("") && et_phoneNumber.getText().toString().trim().equals("") && et_email.getText().toString().trim().equals("") && et_password2.getText().toString().trim().equals(et_password.getText().toString()))
            {
                btn_signup.setEnabled(true);
            }
            else {
                btn_signup.setEnabled(false);
            }
        }
    }

    public void start(){
        Log.d("SignUp", "Starting checking thread...");

        if(t_check == null)
        {
            t_check = new Thread(this, threadName);
            t_check.start();
        }

    }

    public void stop()
    {
        running = 1;
        t_check.interrupt();
    }
}
