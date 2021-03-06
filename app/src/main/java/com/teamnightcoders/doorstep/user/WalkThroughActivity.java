package com.teamnightcoders.doorstep.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnightcoders.doorstep.user.Adapters.SlideAdapter;

public class WalkThroughActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private TextView[] dots;
    private int currentPage;
    private Button next_btn, back_btn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        viewPager = findViewById(R.id.view_pager);
        dotLayout = findViewById(R.id.dot_layout);
        next_btn = findViewById(R.id.next_btn);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setVisibility(View.GONE);
        int[] slideImages = {
               // R.raw.hello,
              //  R.raw.chat_anim2,
              //  R.raw.event_anim,
              //  R.raw.events
        };
        String[] SlideHeading = {
                "Welcome", "Chat", "Events", "Noticeboard"
        };
        String[] slide_contents = {
                "",
                "Chat with your Friends , loved once And Teachers",
                "Share Your events to your friends \n To make better Community. and Friendship",
                "See notices published by the College staff"
        };
        SlideAdapter slideAdapter = new SlideAdapter(this, SlideHeading, slide_contents, slideImages);
        viewPager.setAdapter(slideAdapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
        next_btn.setText("Next");

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(next_btn.getText(), "Finish"))
                    viewPager.setCurrentItem(currentPage + 1);
                else {
                    startActivity(new Intent(WalkThroughActivity.this, LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position) {
        dots = new TextView[4];
        dotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setPadding(5, 0, 5, 0);
            dots[i].setTextColor(getResources().getColor(R.color.blue_dark));

            dotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.blue_dark));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currentPage = i;
            if (i == 0) {
                next_btn.setText("Next");
                back_btn.setVisibility(View.GONE);
                back_btn.setAnimation(AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.fade_out));
            } else if (i == dots.length - 1) {
                next_btn.setText("Finish");
                back_btn.setVisibility(View.VISIBLE);
            } else {
                next_btn.setText("Next");
                if (back_btn.getVisibility() == View.GONE) {
                    back_btn.setVisibility(View.VISIBLE);
                    back_btn.setAnimation(AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.fade_in));
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
