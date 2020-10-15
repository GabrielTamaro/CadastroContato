package br.usjt.ucsist.cadaluno.cad.ui;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.lifecycle.Observer;
        import androidx.lifecycle.ViewModelProvider;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.orhanobut.hawk.Hawk;

        import br.usjt.ucsist.cadaluno.R;
        import br.usjt.ucsist.cadaluno.cad.model.Usuario;
        import br.usjt.ucsist.cadaluno.cad.model.UsuarioViewModel;

public class CadastroActivity extends AppCompatActivity {

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;

    private EditText editTextNome;
    private EditText editTextCPF;
    private EditText editTextEmail;
    private EditText editTextProfissao;
    private EditText editTextSenha;
    private EditText editTextEscolaridade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Hawk.init(this).build();

        editTextNome = findViewById(R.id.editTextNome);
        editTextCPF = findViewById(R.id.editTextCpf);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextProfissao = findViewById(R.id.editTextProfissao);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextEscolaridade = findViewById(R.id.editTextEscolaridade);


        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateView(usuario);
            }
        });
    }

    private void updateView(Usuario usuario){
        if(usuario != null && usuario.getId() > 0){
            usuarioCorrente = usuario;
            editTextNome.setText(usuario.getNome());
            editTextCPF.setText(usuario.getCpf());
            editTextEmail.setText(usuario.getEmail());
            editTextProfissao.setText(usuario.getProfissao());
            editTextSenha.setText(usuario.getSenha());
            editTextEscolaridade.setText(usuario.getEscolaridade());
        }
    }

    public void salvar(View view) {
        if(usuarioCorrente == null){
            usuarioCorrente = new Usuario();
        }
        usuarioCorrente.setNome(editTextNome.getText().toString());
        usuarioCorrente.setCpf(editTextCPF.getText().toString());
        usuarioCorrente.setEmail(editTextEmail.getText().toString());
        usuarioCorrente.setProfissao(editTextProfissao.getText().toString());
        usuarioCorrente.setSenha(editTextSenha.getText().toString());
        usuarioCorrente.setEscolaridade(editTextEscolaridade.getText().toString());


        usuarioViewModel.insert(usuarioCorrente);
        Toast.makeText(this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();

        Hawk.put("tem_cadastro", true);
        finish();
    }
}