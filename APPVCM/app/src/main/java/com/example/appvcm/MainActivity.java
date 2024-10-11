package com.example.appvcm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;

import com.example.appvcm.adapters.TournamentAdapter;
import com.example.appvcm.clases.Tournament;
import com.example.appvcm.competition.nueva_competencia;
import com.example.appvcm.data.DatabaseManager;
import com.example.appvcm.player.PlayerActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.example.appvcm.databinding.ActivityMainBinding;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TournamentAdapter inProgressAdapter;
    private TournamentAdapter finishedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), nueva_competencia.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = binding.navView;

        // Configura los elementos de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        // Configura el listener para las opciones de menú
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    startActivity(intent);
                    drawer.closeDrawers();
                    return true;
                }
                // Aquí puedes manejar otras opciones del menú si es necesario
                return false;
            }
        });
        setupTabs();
    }
    private void setupTabs() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        TournamentPagerAdapter adapter = new TournamentPagerAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private class TournamentPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2; // Two tabs: "En curso" and "Acabadas"
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RecyclerView recyclerView = new RecyclerView(MainActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            if (position == 0) {
                // "En curso" tab: Fetch and display in-progress tournaments
                List<Tournament> inProgressTournaments = fetchTournaments(0); // Fetch from TABLE_TORNEO where terminado=0
                inProgressAdapter = new TournamentAdapter(inProgressTournaments,MainActivity.this);
                recyclerView.setAdapter(inProgressAdapter);
            } else {
                // "Acabadas" tab: Fetch and display finished tournaments
                List<Tournament> finishedTournaments = fetchTournaments(1); // Fetch from TABLE_TORNEO where terminado=1
                finishedAdapter = new TournamentAdapter(finishedTournaments, MainActivity.this);
                recyclerView.setAdapter(finishedAdapter);
            }

            container.addView(recyclerView);
            return recyclerView;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "En curso" : "Acabadas";
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private List<Tournament> fetchTournaments(int status) {
        DatabaseManager dbManager = new DatabaseManager(this);
        return dbManager.getTournamentsByStatus(status); // Implement this method to fetch tournaments based on the status
    }
}