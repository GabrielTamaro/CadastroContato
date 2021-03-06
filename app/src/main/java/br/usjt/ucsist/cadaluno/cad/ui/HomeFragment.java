package br.usjt.ucsist.cadaluno.cad.ui;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.cad.model.Contato;
import br.usjt.ucsist.cadaluno.cad.model.ContatoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ContatoViewModel contatoViewModel;
    private List<Contato> contatos;
    private ContatoAdapter adapter;

    private ProgressBar progressBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        adapter = new ContatoAdapter();
        contatoViewModel = new ViewModelProvider(this).get(ContatoViewModel.class);
        contatoViewModel.getContatosResponseLiveData().observe(this, new Observer<List<Contato>>() {
            @Override
            public void onChanged(List<Contato> contatosList) {
                if (contatosList != null) {
                    adapter.setResults(contatosList);
                }
                progressBar.setVisibility(View.GONE);
            }
        });

        adapter.setOnItemClickListener(new ContatoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, Contato contato) {
                replaceFragment(R.id.frameLayout,
                        ContatoFragment.newInstance("", contato),
                        "FRAGMENTCONTATO",
                        "contato_click");
            }
        });
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewContatos);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            progressBar = view.findViewById(R.id.progressBar);

            return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        contatoViewModel.getContatos();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }
}
