package com.android.juillianlee.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    private FirebaseAuth firebaseAuth;
    private EditText textLogin;
    private EditText textSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.firebaseAuth = FirebaseAuth.getInstance();
        textLogin = findViewById(R.id.editLogin);
        textSenha = findViewById(R.id.editSenha);
    }

    public void onClickLogin(View view) {
        // TODO validar se não é nulo os vandroidTestApialores informados
        String email = textLogin.getText().toString();
        String senha = textSenha.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkUserLogado();
                        } else {
                            Toast.makeText(MainActivity.this, "Falha na autenticação.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.checkUserLogado();
    }

    public void onClickCadastrar(View view) {
        this.callActivity(CadastroActivity.class);
    }

    private void checkUserLogado() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null)
            callActivity(HomeActivity.class);
    }

    private void callActivity(Class newActivity) {
        Intent newIntent = new Intent(MainActivity.this,newActivity);
        startActivity(newIntent);
        finish();
    }

}
