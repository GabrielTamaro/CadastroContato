package br.usjt.ucsist.cadaluno.cad.ui;

        import androidx.annotation.IdRes;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //aqui vai ser o perfil
        replaceFragment(R.id.frameLayoutF, PerfilFragment.newInstance(true,""), "PERFILFRAGMENT", "PERFILINICIAL");

    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }
}