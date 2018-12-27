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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView descriptionTextView;
    Item[] equipments = new Item[5];
    Item textViewItem; // 用来汇总
    Item item;
    Item itemHero;
    ImageButton heroImageButton;
    ImageButton item1Button;
    ImageButton item2Button;
    ImageButton item3Button;
    ImageButton item4Button;
    String description;
    ImageButton item5Button;
    EditText searchEditText;
    ItemFactory itemFactory;
    ImageButton searchButton;

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

        searchButton = (ImageButton) findViewById(R.id.searchBtn);
        heroImageButton = (ImageButton) findViewById(R.id.heroImageBtn);
        item1Button = (ImageButton) findViewById(R.id.item1ImageBtn);
        item2Button = (ImageButton) findViewById(R.id.item2ImageBtn);
        item3Button = (ImageButton) findViewById(R.id.item3ImageBtn);
        item4Button = (ImageButton) findViewById(R.id.item4ImageBtn);
        item5Button = (ImageButton) findViewById(R.id.item5ImageBtn);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        itemFactory = new ItemFactory(this);
        descriptionTextView.setMovementMethod(ScrollingMovementMethod.getInstance()); // allow scroll
        textViewItem = new Item();
        item = new Item();
        for (int i = 0; i < 5; i++) {
            equipments[i] = new Item();
        }

        description = "";
        itemHero = new Item();

        searchButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                int[] drawableIds = {R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3,
                        R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8,
                        R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13,
                        R.drawable.p14, R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18,
                        R.drawable.p19, R.drawable.p20, R.drawable.p21, R.drawable.p22, R.drawable.p23};

                int[] drawableItemIds = {R.drawable.p100, R.drawable.p101, R.drawable.p102, R.drawable.p103,
                        R.drawable.p104, R.drawable.p105, R.drawable.p106, R.drawable.p107, R.drawable.p108,
                        R.drawable.p109, R.drawable.p110, R.drawable.p111, R.drawable.p112, R.drawable.p113,
                        R.drawable.p114, R.drawable.p115,};

                String text = searchEditText.getText().toString();
                item = itemFactory.getItemByKeyword(text);
                if (item.itemType == type.HERO && item.id != -1) {
                    Toast.makeText(MainActivity.this, "hero添加", Toast.LENGTH_LONG).show();
                    itemHero.id = item.id;
                    itemHero.buff = item.buff;
                    itemHero.description = item.description;
                    itemHero.name = item.name;
                    itemHero.picturePath = item.picturePath;
                    //String s = itemHero.picturePath.substring
                    //       (1,itemHero.picturePath.length()-1);
                    heroImageButton.setImageResource(drawableIds[item.id]);
                } else {
                    for (int i = 0; i < 5; i++) {
                        if (equipments[i].id == -1) {
                            Toast.makeText(MainActivity.this, "第" + (i + 1) + "件装备", Toast.LENGTH_LONG).show();
                            equipments[i].itemType = item.itemType;
                            equipments[i].picturePath = item.picturePath;
                            switch (i) {
                                case 0:
                                    item1Button.setImageResource(drawableItemIds[item.id - 100]);
                                    break;
                                case 1:
                                    item2Button.setImageResource(drawableItemIds[item.id - 100]);
                                    break;
                                case 2:
                                    item3Button.setImageResource(drawableItemIds[item.id - 100]);
                                    break;
                                case 3:
                                    item4Button.setImageResource(drawableItemIds[item.id - 100]);
                                    break;
                                case 4:
                                    item5Button.setImageResource(drawableItemIds[item.id - 100]);
                                    break;
                            }

                            equipments[i].name = item.name;
                            equipments[i].id = item.id;
                            equipments[i].buff = item.buff;
                            equipments[i].description = item.description;
                            textViewItem.add(equipments[i]);
                            break;

                        }
                        if (i == 4) {
                            Toast.makeText(MainActivity.this, "英雄已神装！！！不能再添加", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                /*
                for(int i=0;i<5;i++){
                    textViewItem.add(equipments[i]);
                }
                */

                if (itemHero.id != -1) {
                    textViewItem.add(itemHero);
                    description = itemHero.description + "\n\n";
                }
                for (int i = 0; i < 5; i++) {
                    if (equipments[i].id != -1) {
                        description = description + equipments[i].name + "\n" + equipments[i].description + "\n";
                    }
                }


                descriptionTextView.setText(
                        "英雄名字：" + itemHero.name + "\n"
                                + "AD:" + textViewItem.buff.buff[0] + "\n"
                                + "AP:" + textViewItem.buff.buff[1] + "\n"
                                + "ADD:" + textViewItem.buff.buff[2] + "\n"
                                + "APD:" + textViewItem.buff.buff[3] + "\n"
                                + "MANA:" + textViewItem.buff.buff[4] + "\n"
                                + "BLOOD:" + textViewItem.buff.buff[5] + "\n"
                                + "PRICE:" + textViewItem.buff.buff[6] + "\n"
                                + "DECRIPTION:" + description + "\n"
                );
                searchEditText.setText(null);


            }
        });


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
