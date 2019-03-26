package com.android.juillianlee.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends Activity {
    private FirebaseAuth firebaseAuth;
    private EditText textEmail;
    private EditText textSenha;
    private EditText textSenhaConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        firebaseAuth = FirebaseAuth.getInstance();
        textEmail = findViewById(R.id.editLoginCad);
        textSenha = findViewById(R.id.editSenhaCad);
        textSenhaConfirm = findViewById(R.id.editSenhaConfirm);

    }

    public void onClickCadastro(View view) {
        String email = textEmail.getText().toString();
        String senha = textSenha.getText().toString();
        String senhaConfirm = textSenhaConfirm.getText().toString();

        if(!senha.equals(senhaConfirm)) {
            Toast.makeText(CadastroActivity.this, "As senhas não são iguais.", Toast.LENGTH_SHORT).show();
            updateUI(null);
        } else {
            this.firebaseAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(CadastroActivity.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }


    }


    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null)
            callActivity(HomeActivity.class);
        else
            callActivity(CadastroActivity.class);
    }

    private void callActivity(Class newActivity) {
        Intent newIntent = new Intent(CadastroActivity.this,newActivity);
        startActivity(newIntent);
        finish();
    }
}
