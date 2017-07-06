package com.example.oleh.rssreader;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PCWorldActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Html.fromHtml("<font color='#000000'>PC World </font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pcworl1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A6C73F")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#849e32"));
        }
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        setContentView(R.layout.activity_pcworld);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        ReadPCWorlRss readRss=new ReadPCWorlRss(this,recyclerView);
        readRss.execute();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_back,R.anim.slide_out_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hamburger,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_abc_news:
                Intent intent = new Intent(this,ABCNewsActivity.class);
                this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                return true;
            case R.id.action_nyt:
                Intent intent1 = new Intent(this,NYTActivity.class);
                this.startActivity(intent1);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                return true;
            case R.id.action_techtimes:
                Intent intent2 = new Intent(this,TechTimesActivity.class);
                this.startActivity(intent2);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                return true;
            case R.id.action_sciencemag:
                Intent intent3 = new Intent(this,MainActivity.class);
                this.startActivity(intent3);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
