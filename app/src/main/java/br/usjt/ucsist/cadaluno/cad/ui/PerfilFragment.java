package br.usjt.ucsist.cadaluno.cad.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;


import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.cad.model.Usuario;
import br.usjt.ucsist.cadaluno.cad.model.UsuarioViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean mParam1;
    private String mParam2;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;

    private EditText editTextNome;
    private EditText editTextCPF;
    private EditText editTextEmail;
    private EditText editTextProfissao;
    private EditText editTextSenha;
    private EditText editTextEscolaridade;

    private Button buttonSalvar;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(boolean param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getBoolean(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Hawk.init(getActivity()).build();

        editTextNome = view.findViewById(R.id.editTextNomeF);
        editTextCPF = view.findViewById(R.id.editTextCpfF);
        editTextEmail = view.findViewById(R.id.editTextEmailF);
        editTextSenha = view.findViewById(R.id.editTextSenhaF);
        editTextEscolaridade = view.findViewById(R.id.editTextEscolaridadeF);
        editTextProfissao = view.findViewById(R.id.editTextProfissaoF);

        buttonSalvar= view.findViewById(R.id.buttonSalvarF);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Salvar dados
                salvar();
            }
        });
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(getActivity(), new Observer<Usuario>() {
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

    public void salvar() {
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
        Toast.makeText(getActivity(), "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();

        Hawk.put("tem_cadastro", true);
        if(mParam1){
            getActivity().finish();
        }
    }
}