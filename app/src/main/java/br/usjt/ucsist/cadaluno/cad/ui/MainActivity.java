package br.usjt.ucsist.cadaluno.cad.ui;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.usjt.ucsist.cadaluno.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(R.id.frameLayout, HomeFragment.newInstance("",""), "HOMEFRAGMENT", "HOME");

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        //aqui é onde vai chamar a fragment Home
                        replaceFragment(R.id.frameLayout, HomeFragment.newInstance("",""), "HOMEFRAGMENT", "HOME");
                        return true;

                    case R.id.contato:
                        //aqui ele vai chamar a fragment contato
                        replaceFragment(R.id.frameLayout, ContatoFragment.newInstance("", null), "CONTATOFRAGMENT", "Contato");
                        return true;

                    case R.id.perfil:
                        //aqui vai ser o perfil
                        replaceFragment(R.id.frameLayout, PerfilFragment.newInstance(false,""), "PERFILFRAGMENT", "Perfil");
                        return true;

                    case R.id.config:
                        //aqui será a configuração
                        replaceFragment(R.id.frameLayout, ConfiguracaoFragment.newInstance("",""), "CONFIGFRAGMENT", "Config");
                        return true;

                    case R.id.mapa:
                        //aqui será a mapa
                        replaceFragment(R.id.frameLayout, new MapaFragment(), "MAPAFRAGMENT", "Mapa");
                        return true;


                }


                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sair:
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
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