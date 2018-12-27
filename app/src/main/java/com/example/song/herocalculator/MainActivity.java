package com.example.song.herocalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ItemFactory itemFactory;
    Item item;
    Item[] items = {new Item(), new Item(), new Item(), new Item(), new Item(), new Item()};
    Boolean[] state = {false, false, false, false, false, false};

    EditText searchEditText;
    TextView descriptionTextView;
    ImageButton heroImageBtn, item1ImageBtn, item2ImageBtn,item3ImageBtn,item4ImageBtn,item5ImageBtn;

    int[] heroImageId = {R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8,
            R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13,
            R.drawable.p14, R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18,
            R.drawable.p19, R.drawable.p20, R.drawable.p21, R.drawable.p22, R.drawable.p23};

    int[] itemImageId = {R.drawable.p100, R.drawable.p101, R.drawable.p102, R.drawable.p103,
            R.drawable.p104, R.drawable.p105, R.drawable.p106, R.drawable.p107, R.drawable.p108,
            R.drawable.p109, R.drawable.p110, R.drawable.p111, R.drawable.p112, R.drawable.p113,
            R.drawable.p114, R.drawable.p115,};

    public void onClick(View v) {
        ImageButton temp = (ImageButton) v;
        int index = -1;
        //Log.e("debug1", "onCLick clicked");
        switch (v.getId()) {
            case R.id.heroImageBtn:
                index = 0;
                break;
            case R.id.item1ImageBtn:
                index = 1;
                break;
            case R.id.item2ImageBtn:
                index = 2;
                break;
            case R.id.item3ImageBtn:
                index = 3;
                break;
            case R.id.item4ImageBtn:
                index = 4;
                break;
            case R.id.item5ImageBtn:
                index = 5;
                break;
            default:
                break;
        }
        if (state[index]) { //已有
            Toast.makeText(MainActivity.this, "Clear hero or item", Toast.LENGTH_SHORT).show();
            state[index] = false;
            temp.setImageResource(R.drawable.add);
        } else {
            String keyword = searchEditText.getText().toString();
            item = itemFactory.getItemByKeyword(keyword);
            if (item.id == -1) {//查询不成功
                Toast.makeText(MainActivity.this, "No such hero or item, please check your input.", Toast.LENGTH_SHORT).show();
            } else {//查询成功
                items[index] = item;
                state[index] = true;
                if (item.id < 100) {//是英雄
                    Toast.makeText(MainActivity.this, "Hero added", Toast.LENGTH_SHORT).show();
                    temp.setImageResource(heroImageId[item.id]);
                } else {
                    Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                    temp.setImageResource(itemImageId[item.id-100]);
                }
            }
        }

        Item SumItem = new Item();
        String SumDescription = "";
//        for(int i=0;i<6;i++){
//            if(state[i]){
//                SumItem.add(items[i]);
//                SumDescription+=items[i].print();
//            }
//        }

        boolean first = false;
        for(int i=0;i<6;i++){
            if(state[i]){
                if(!first){
                    first = true;
                    SumItem = items[i];
                }else {
                    SumItem.add(items[i]);
                    SumDescription+=items[i].print();
                }

            }
        }
        if(first){
            descriptionTextView.setText(SumItem.print()+"\n\n"+SumDescription);
        }else {
            descriptionTextView.setText("");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /****************************************/
        itemFactory = new ItemFactory(this);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setMovementMethod(ScrollingMovementMethod.getInstance()); // allow scroll
        heroImageBtn = (ImageButton) findViewById(R.id.heroImageBtn);
        heroImageBtn.setOnClickListener(this);
        item1ImageBtn = (ImageButton) findViewById(R.id.item1ImageBtn);
        item1ImageBtn.setOnClickListener(this);
        item2ImageBtn = (ImageButton) findViewById(R.id.item2ImageBtn);
        item2ImageBtn.setOnClickListener(this);
        item3ImageBtn = (ImageButton) findViewById(R.id.item3ImageBtn);
        item3ImageBtn.setOnClickListener(this);
        item4ImageBtn = (ImageButton) findViewById(R.id.item4ImageBtn);
        item4ImageBtn.setOnClickListener(this);
        item5ImageBtn = (ImageButton) findViewById(R.id.item5ImageBtn);
        item5ImageBtn.setOnClickListener(this);

        /****************************************/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main) {
            // Handle the camera action
        } else if (id == R.id.browse_hero_info) {
            Intent intent = new Intent(MainActivity.this, HeroActivity.class);
            startActivity(intent);
        } else if (id == R.id.browse_item_info) {
            Intent intent = new Intent(MainActivity.this, ItemActivity.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
