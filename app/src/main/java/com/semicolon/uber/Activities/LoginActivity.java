package com.semicolon.uber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.semicolon.uber.R;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn,regBtn;
    private AlertDialog.Builder loginBuilder,regBuilder;
    private RelativeLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        root = findViewById(R.id.root);
        loginBtn = findViewById(R.id.loginBtn);
        regBtn = findViewById(R.id.regBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateLoginAlertDialog();
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slid_up,R.anim.slid_out);
            }
        });
    }
    private void CreateLoginAlertDialog()
    {
        loginBuilder = new AlertDialog.Builder(this);
        loginBuilder.setCancelable(true);
        loginBuilder.create().setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.login_layout,null);
        final EditText user_name = view.findViewById(R.id.edt_user_name);
        final EditText user_password = view.findViewById(R.id.edt_password);
        Button login = view.findViewById(R.id.loginBtn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m_name= user_name.getText().toString();
                String m_pass= user_password.getText().toString();
                Login(m_name,m_pass);
            }
        });




        loginBuilder.setView(view);
        loginBuilder.show();
    }

    private void Login(String m_name, String m_pass) {

        if (TextUtils.isEmpty(m_name))
        {
            Snackbar.make(root, "Username is empty", Snackbar.LENGTH_LONG).show();

        }else if (TextUtils.isEmpty(m_pass))
        {

            Snackbar.make(root,"password is empty",Snackbar.LENGTH_LONG).show();

        }else if (m_pass.length()<5)
        {
            Snackbar.make(root,"password is too short",Snackbar.LENGTH_LONG).show();

        }else
        {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }
    }
}

