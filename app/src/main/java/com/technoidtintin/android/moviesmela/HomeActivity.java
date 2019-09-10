package com.technoidtintin.android.moviesmela;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.technoidtintin.android.moviesmela.Model.FavlistItem;
import com.technoidtintin.android.moviesmela.Model.MovieItem;

public class HomeActivity extends AppCompatActivity implements PopularFragment.OnClickListener, TopFragment.OnTopItemClickListener, FavFragment.OnFavItemClickListener {

    PopularFragment popularFragment = new PopularFragment();
    TopFragment topFragment = new TopFragment();
    FavFragment favFragment = new FavFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment active = popularFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_popular:
                    fragmentManager.beginTransaction().hide(active).show(popularFragment).commit();
                    active = popularFragment;
                    return true;
                case R.id.navigation_top_rated:
                    fragmentManager.beginTransaction().hide(active).show(topFragment).commit();
                    active = topFragment;
                    return true;
                case R.id.navigation_favorite:
                    fragmentManager.beginTransaction().hide(active).show(favFragment).commit();
                    active = favFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, favFragment, "3").hide(favFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, topFragment, "2").hide(topFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, popularFragment, "1").commit();


    }

    @Override
    public void onClick(MovieItem movieItem) {
        Intent intent = new Intent(HomeActivity.this, MovieDetailsActivity.class);
        intent.putExtra("movie", movieItem);
        startActivity(intent);
    }

    @Override
    public void onTopItemClick(MovieItem movieItem) {
        Intent intent = new Intent(HomeActivity.this, MovieDetailsActivity.class);
        intent.putExtra("movie", movieItem);
        startActivity(intent);
    }

    @Override
    public void OnFaveItemClick(FavlistItem favlistItem) {

        Intent intent = new Intent(HomeActivity.this, MovieDetailsActivity.class);
        intent.putExtra("fav", favlistItem);
        startActivity(intent);
    }
}
