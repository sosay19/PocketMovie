package eu.epfc.pocketmovie;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity   {


    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
//        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter  = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentRecentMovies(),"RECENT MOVIES");
        adapter.AddFragment(new FragmentPocket(),"POCKET");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getApplicationContext(), R.color.white),
                ContextCompat.getColor(getApplicationContext(), R.color.white)
        );


    }





}