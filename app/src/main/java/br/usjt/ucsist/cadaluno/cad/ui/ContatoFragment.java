package br.usjt.ucsist.cadaluno.cad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.util.List;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.cad.model.Contato;
import br.usjt.ucsist.cadaluno.cad.model.ContatoViewModel;
import br.usjt.ucsist.cadaluno.cad.model.Usuario;
import br.usjt.ucsist.cadaluno.cad.model.UsuarioViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContatoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Contato mParam2;

    private ContatoViewModel contatoViewModel;
    private Contato contatoCorrente;

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;

    private Button buttonSalvar;

    private ImageView fotoContato;
    private TextView linkContato;

    public ContatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContatoFragment newInstance(String param1, Contato param2) {
        ContatoFragment fragment = new ContatoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = (Contato) getArguments().getSerializable(ARG_PARAM2);
        }
        contatoCorrente = new Contato();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contato, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        editTextNome = view.findViewById(R.id.editTextNomeC);
        editTextEmail = view.findViewById(R.id.editTextEmailC);
        editTextTelefone = view.findViewById(R.id.editTextTelefoneC);

        fotoContato = view.findViewById(R.id.fotoContato);
        linkContato = view.findViewById(R.id.linkContato);
        linkContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Salvar dados
                tirarFoto();
            }
        });
        buttonSalvar = view.findViewById(R.id.buttonSalvarC);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Salvar dados
                salvar();
            }
        });
        contatoViewModel = new ViewModelProvider(this).get(ContatoViewModel.class);

        contatoViewModel.getSalvoSucesso().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean sucesso) {
                if (sucesso) {

                    Toast.makeText(getActivity(), "Contato salvo com sucesso", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity(), "Falha ao salvar o contato", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(mParam2!=null){
            contatoCorrente = mParam2;
            editTextNome.setText(contatoCorrente.getNome());
            editTextEmail.setText(contatoCorrente.getEmail());
            editTextTelefone.setText(contatoCorrente.getTelefone());
            fotoContato.setImageBitmap(ImageUtil.decode(contatoCorrente.getImagem()));
        }
    }

    public void tirarFoto(){
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fotoContato.setImageBitmap(imageBitmap);
            contatoCorrente.setImagem(ImageUtil.encode(imageBitmap));
            Log.d("IMAGEMBITMAPENCODED-->",contatoCorrente.getImagem());
        }

    }


    public void salvar() {
        hideSoftKeyboard(getActivity());

        if (validarCampos()) {
            contatoCorrente.setNome(editTextNome.getText().toString());
            contatoCorrente.setEmail(editTextEmail.getText().toString());
            contatoCorrente.setTelefone(editTextTelefone.getText().toString());

            contatoViewModel.salvarContato(contatoCorrente);
        }
    }

    public boolean validarCampos(){

        boolean valido = true;
        if(editTextNome.getText().toString().trim().length()==0){
            valido = false;
            Toast.makeText(getActivity(),"Nome inválido!",
                    Toast.LENGTH_SHORT).show();
        }
        if(editTextEmail.getText().toString().trim().length()==0){
            valido = false;
            Toast.makeText(getActivity(),"Email inválido!",
                    Toast.LENGTH_SHORT).show();
        }
        if(editTextTelefone.getText().toString().trim().length()==0){
            valido = false;
            Toast.makeText(getActivity(),"Telefone inválido!",
                    Toast.LENGTH_SHORT).show();
        }
        return valido;
    }

        private void limparCampos () {
            editTextNome.setText("");
            editTextEmail.setText("");
            editTextTelefone.setText("");
            fotoContato.setImageResource(R.drawable.ic_place_holder);
        }

    public void onChanged(@Nullable final Boolean sucesso) {
        if(sucesso){
            Toast.makeText(getActivity(),"Contato salvo com sucesso",
                    Toast.LENGTH_SHORT).show();
            limparCampos();
        }else{
            Toast.makeText(getActivity(),"Falha ao salvar o contato!",
                    Toast.LENGTH_SHORT).show();
        }

        if(mParam2 != null){
        contatoCorrente = mParam2;
        editTextNome.setText(contatoCorrente.getNome());
        editTextEmail.setText(contatoCorrente.getEmail());
        editTextTelefone.setText(contatoCorrente.getTelefone());
        }


        }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}