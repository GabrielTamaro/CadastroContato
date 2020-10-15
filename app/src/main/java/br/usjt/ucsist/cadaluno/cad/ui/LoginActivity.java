package br.usjt.ucsist.cadaluno.cad.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.cad.model.Usuario;
import br.usjt.ucsist.cadaluno.cad.model.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLongin;
    private TextView textViewNovoCadastro;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;

    private EditText editTextEmail;
    private EditText editTextSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();

        editTextEmail = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);

        buttonLongin = findViewById(R.id.buttonLogin);
        textViewNovoCadastro =findViewById(R.id.textViewNovoCadastro);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateUsuario(usuario);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(Hawk.contains("tem_cadastro")){
            if(Hawk.get("tem_cadastro")){
                desbloquear();
            }else{
                bloquear();
            }

        }else{
            bloquear();
        }
    }

    private void updateUsuario(Usuario usuario){
        usuarioCorrente = usuario;
    }


    private void bloquear(){
        buttonLongin.setEnabled(false);
        buttonLongin.setBackgroundColor(getResources().getColor(R.color.cinza));
        textViewNovoCadastro.setVisibility(View.VISIBLE);
    }

    private void desbloquear(){
        buttonLongin.setEnabled(true);
        buttonLongin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textViewNovoCadastro.setVisibility(View.GONE);
    }

    public void novoCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        if(usuarioCorrente != null){
            if(usuarioCorrente.getEmail().equalsIgnoreCase(editTextEmail.getText().toString())
                && usuarioCorrente.getSenha().equals(editTextSenha.getText().toString())){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}