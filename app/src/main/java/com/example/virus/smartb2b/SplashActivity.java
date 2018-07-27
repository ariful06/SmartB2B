package com.example.virus.smartb2b;

import android.content.Intent;
import com.daimajia.androidanimations.library.Techniques;
import com.example.virus.smartb2b.activity.MainActivity;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity  extends AwesomeSplash {
    //DO NOT OVERRIDE onCreate()!
    //if you need to start some services do it in initSplash()!
    @Override
    public void initSplash(ConfigSplash configSplash) {
        /* you don't have to override every property */
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1500); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP
        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default
        //Customize Logo
        configSplash.setLogoSplash(R.drawable.meal); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1500); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Wave); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)
        //Customize Path
        configSplash.setPathSplash(Constants.JUICE_GLASS); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(1500);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorText); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(2500);
        configSplash.setPathSplashFillColor(R.color.colorText); //path object filling color

        //Customize Title
        configSplash.setTitleSplash("Smart Restaurant"); //change your app name here
        configSplash.setTitleTextColor(R.color.colorText);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(1500);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configSplash.setTitleFont("fonts/Bleeding_Cowboys.ttf"); //provide string to your font located in assets/fonts/
    }
    @Override
    public void animationsFinished() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}