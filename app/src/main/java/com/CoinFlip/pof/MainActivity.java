package com.CoinFlip.pof;




import static com.CoinFlip.pof.R.color.black;
import static com.CoinFlip.pof.R.color.material_slider_inactive_tick_marks_color;
import static com.CoinFlip.pof.R.color.white;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {




    ViewGroup transitionsContainer;  //FOR TRANSITION ANIM (tools)
    
    
    
    RelativeLayout mainToolsLayout;

    RelativeLayout LToolsBlack;

    RelativeLayout LToolsSlide;


    Button mainLaunch;
    Button mainToolsButton;
    Button mainColorButton;


    //LAYOUT ANIM

    RelativeLayout LAnimLaunchLight;
    RelativeLayout LAnimLaunchDark;




    //Light
    RelativeLayout mainLayout;
    ImageView pileHLight;
    ImageView faceBLight;
    ImageView pileBLight;
    ImageView faceHLight;

    ImageView pileWinLight;
    ImageView faceWinLight;

    ImageView mainImgColor;

    ImageView imgToolsLight;



    //Dark
    RelativeLayout mainLayoutDark;
    ImageView pileHDark;
    ImageView faceBDark;
    ImageView pileBDark;
    ImageView faceHDark;

    ImageView pileWinDark;
    ImageView faceWinDark;

    ImageView imgToolsDark;


    TextView textPileWin;
    TextView textFaceWin;
    TextView toolTitle;



    MediaPlayer bong1;
    MediaPlayer bong2;
    MediaPlayer bong3;

    Switch switchMusic;

    TextView privacy;

    boolean pushed;

    boolean running;

    boolean opened;

    int intWin; //entier : 0=rien ; 1=pile; 2=face

    int intmode; //0=light ; 1=dark

    boolean colorSwitchClicked;

    //SHARED PREFERENCES





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setTheme(R.style.Theme_AppCompat_NoActionBar);

        setContentView(R.layout.activity_main);



        transitionsContainer = findViewById(R.id.LayoutMainAll);


        mainToolsLayout = findViewById(R.id.mainToolsLayout);
        LToolsBlack = findViewById(R.id.LToolsBlack);
        LToolsSlide = findViewById(R.id.LToolsSlide);



        mainLaunch = findViewById(R.id.mainLaunch);
        mainToolsButton = findViewById(R.id.mainToolsButton);
        mainColorButton = findViewById(R.id.mainColorButton);


        //LAYOUT ANIM


        LAnimLaunchLight = findViewById(R.id.LAnimLaunchLight);
        LAnimLaunchDark = findViewById(R.id.LAnimLaunchDark);



        //Light
        mainLayout = findViewById(R.id.mainLayout);
        pileHLight = findViewById(R.id.pileHLight);
        faceBLight = findViewById(R.id.faceBLight);
        pileBLight = findViewById(R.id.pileBLight);
        faceHLight = findViewById(R.id.faceHLight);
        pileWinLight = findViewById(R.id.pileWinLight);
        faceWinLight = findViewById(R.id.faceWinLight);

        mainImgColor =findViewById(R.id.mainImgColor);

        imgToolsLight = findViewById(R.id.imgToolsLight);

        //Dark
        mainLayoutDark = findViewById(R.id.mainLayoutDark);
        pileHDark = findViewById(R.id.pileHDark);
        faceBDark = findViewById(R.id.faceBDark);
        pileBDark = findViewById(R.id.pileBDark);
        faceHDark = findViewById(R.id.faceHDark);
        pileWinDark = findViewById(R.id.pileWinDark);
        faceWinDark = findViewById(R.id.faceWinDark);

        imgToolsDark = findViewById(R.id.imgToolsDark);



        textPileWin = findViewById(R.id.textPileWin);
        textFaceWin = findViewById(R.id.textFaceWin);
        toolTitle = findViewById(R.id.toolsTitle);




        bong1 = MediaPlayer.create(this,R.raw.bong1);
        bong2 = MediaPlayer.create(this,R.raw.bong2);
        bong3 = MediaPlayer.create(this,R.raw.bong3);

        switchMusic = findViewById(R.id.switchMusic);

        privacy = findViewById(R.id.privacy);


        pushed = false;
        running = false;
        opened = false;

        intWin=0;
        intmode = 0;



        //VARIABLES DIFFERENTS LAYOUTS

        final ImageView[] pileH = {pileHLight};
        final ImageView[] pileB = {pileBLight};
        final ImageView[] faceH = {faceHLight};
        final ImageView[] faceB = {faceBLight};
        final ImageView[] pileWin = {pileWinLight};
        final ImageView[] faceWin = {faceWinLight};
        final RelativeLayout[] layoutAnim = {LAnimLaunchLight};



        ////////////////////////////////////////////SHARED PREFERENCES

        SharedPreferences sharedPreferences = getSharedPreferences("sharedVariables",MODE_PRIVATE);

        //SI DARK MODE : ON CHANGE COMME UN SWITCH

        int test = sharedPreferences.getInt("intMode",0);

        if(test == 1){

            mainLayout.setVisibility(RelativeLayout.GONE);
            mainLayoutDark.setVisibility(RelativeLayout.VISIBLE);

            if (intWin == 0) {
                pileHDark.setVisibility(ImageView.VISIBLE);
                faceBDark.setVisibility(ImageView.VISIBLE);
                faceWinDark.setVisibility(ImageView.INVISIBLE);
                pileWinDark.setVisibility(ImageView.INVISIBLE);
            } else if (intWin == 1) {
                pileHDark.setVisibility(ImageView.INVISIBLE);
                faceBDark.setVisibility(ImageView.INVISIBLE);
                faceWinDark.setVisibility(ImageView.INVISIBLE);
                pileWinDark.setVisibility(ImageView.VISIBLE);
            } else {
                pileHDark.setVisibility(ImageView.INVISIBLE);
                faceBDark.setVisibility(ImageView.INVISIBLE);
                faceWinDark.setVisibility(ImageView.VISIBLE);
                pileWinDark.setVisibility(ImageView.INVISIBLE);
            }


            // Echange des variables de jeux

            pileH[0] = pileHDark;
            pileB[0] = pileBDark;
            faceH[0] = faceHDark;
            faceB[0] = faceBDark;
            pileWin[0] = pileWinDark;
            faceWin[0] = faceWinDark;
            layoutAnim[0] = LAnimLaunchDark;

            textFaceWin.setTextColor(getResources().getColor(white));
            textPileWin.setTextColor(getResources().getColor(white));


            mainImgColor.setImageResource(R.drawable.pile_win_dark);


            intmode=1;

        }

        boolean check = sharedPreferences.getBoolean("musicCheck",true);
        if(!check){
            switchMusic.setChecked(false);
        }






        ////////////////////////////////////////////////////////////////////SHARED PREFERENCES


        colorSwitchClicked = false;
        mainColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(running==false){

                //PASSAGE MODE SOMBRE

                if(intmode==0) {

                    mainLayout.setVisibility(RelativeLayout.GONE);
                    Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
                    mainLayoutDark.startAnimation(startAnimation);
                    mainLayoutDark.setVisibility(RelativeLayout.VISIBLE);

                    if (intWin == 0) {
                        pileHDark.setVisibility(ImageView.VISIBLE);
                        faceBDark.setVisibility(ImageView.VISIBLE);
                        faceWinDark.setVisibility(ImageView.INVISIBLE);
                        pileWinDark.setVisibility(ImageView.INVISIBLE);
                    } else if (intWin == 1) {
                        pileHDark.setVisibility(ImageView.INVISIBLE);
                        faceBDark.setVisibility(ImageView.INVISIBLE);
                        faceWinDark.setVisibility(ImageView.INVISIBLE);
                        pileWinDark.setVisibility(ImageView.VISIBLE);
                    } else {
                        pileHDark.setVisibility(ImageView.INVISIBLE);
                        faceBDark.setVisibility(ImageView.INVISIBLE);
                        faceWinDark.setVisibility(ImageView.VISIBLE);
                        pileWinDark.setVisibility(ImageView.INVISIBLE);
                    }


                    // Echange des variables de jeux

                    pileH[0] = pileHDark;
                    pileB[0] = pileBDark;
                    faceH[0] = faceHDark;
                    faceB[0] = faceBDark;
                    pileWin[0] = pileWinDark;
                    faceWin[0] = faceWinDark;
                    layoutAnim[0] = LAnimLaunchDark;

                    textFaceWin.setTextColor(getResources().getColor(white));
                    textPileWin.setTextColor(getResources().getColor(white));


                    mainImgColor.setImageResource(R.drawable.pile_win_dark);


                    intmode=1;


                    //SAVE MODE

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedVariables",MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("intMode",intmode);
                    editor.apply();


                }

                //PASSAGE LIGHT MODE
                else if(intmode==1) {

                    mainLayoutDark.setVisibility(RelativeLayout.GONE);
                    Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
                    mainLayout.startAnimation(startAnimation);
                    mainLayout.setVisibility(RelativeLayout.VISIBLE);

                    if (intWin == 0) {
                        pileHLight.setVisibility(ImageView.VISIBLE);
                        faceBLight.setVisibility(ImageView.VISIBLE);
                        faceWinLight.setVisibility(ImageView.INVISIBLE);
                        pileWinLight.setVisibility(ImageView.INVISIBLE);
                    } else if (intWin == 1) {
                        pileHLight.setVisibility(ImageView.INVISIBLE);
                        faceBLight.setVisibility(ImageView.INVISIBLE);
                        faceWinLight.setVisibility(ImageView.INVISIBLE);
                        pileWinLight.setVisibility(ImageView.VISIBLE);
                    } else {
                        pileHLight.setVisibility(ImageView.INVISIBLE);
                        faceBLight.setVisibility(ImageView.INVISIBLE);
                        faceWinLight.setVisibility(ImageView.VISIBLE);
                        pileWinLight.setVisibility(ImageView.INVISIBLE);
                    }


                    pileH[0] = pileHLight;
                    pileB[0] = pileBLight;
                    faceH[0] = faceHLight;
                    faceB[0] = faceBLight;
                    pileWin[0] = pileWinLight;
                    faceWin[0] = faceWinLight;
                    layoutAnim[0] = LAnimLaunchLight;


                    textFaceWin.setTextColor(getResources().getColor(black));
                    textPileWin.setTextColor(getResources().getColor(black));

                    mainImgColor.setImageResource(R.drawable.pile7);

                    intmode = 0;


                    //SAVE MODE

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedVariables",MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("intMode",intmode);
                    editor.apply();

                }

                }

            }
        });



        mainToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                if(!opened) {

                    opened = true;

                    Animation AnimOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate45deg_left);

                    if(intmode==0) {
                        imgToolsLight.startAnimation(AnimOpen);
                    }
                    else if(intmode==1){
                        imgToolsDark.startAnimation(AnimOpen);
                    }


                    LToolsBlack.setVisibility(RelativeLayout.VISIBLE);

                    Transition slide = new Slide(Gravity.BOTTOM);
                    slide.addTarget(mainToolsLayout);
                    slide.setDuration(80);
                    TransitionManager.beginDelayedTransition(transitionsContainer,slide);


                    mainToolsLayout.setVisibility(RelativeLayout.VISIBLE);
                    LToolsSlide.setVisibility(RelativeLayout.VISIBLE);

                    toolTitle.setVisibility(TextView.VISIBLE);

                    mainLaunch.setVisibility(Button.GONE);

                    LToolsBlack.setVisibility(RelativeLayout.VISIBLE);

                    LToolsSlide.setVisibility(RelativeLayout.VISIBLE);



                }

                else { // SI PARAMETRES ET OUVERT ALORS ON LE FERME EN RE CLIQUANT DESSUS
                    closeTools();

                }

            }
        });



        LToolsBlack.setOnClickListener(new View.OnClickListener() { //fermer tools en appuyant n'importe ou
            @Override
            public void onClick(View view) {
                if(opened){
                    closeTools();
                }
            }
        });



        mainToolsLayout.setOnClickListener(new View.OnClickListener() { //ON NE FAIS RIEN (pour empecher de fermer en cliquant sur le Layout tools)
            public void onClick(View view) {

            }
        });

        LToolsSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        LToolsSlide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(opened){

                    onTouchEvent(motionEvent);
                }
                return false;
            }
        });


        mainLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(running == false) {
                    running=true;
                    if (pushed) {
                        unPushed(pileH[0], pileB[0], faceH[0], faceB[0], pileWin[0], faceWin[0]);

                        new CountDownTimer(150, 150) {

                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {

                                game(pileH[0], pileB[0], faceH[0], faceB[0], pileWin[0], faceWin[0],layoutAnim[0]);

                            }
                        }.start();

                    }
                    else{

                        game(pileH[0], pileB[0], faceH[0], faceB[0], pileWin[0], faceWin[0],layoutAnim[0]);

                    }
                }
            }
        });


        //save music change for close app
        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences sharedPreferences = getSharedPreferences("sharedVariables",MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("musicCheck",b);
                editor.apply();


            }
        });


        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pages.flycricket.io/coin-flip-1/privacy.html"));
                startActivity(browserIntent);
            }
        });


    }



    float firstX_point, firstY_point;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                firstX_point = event.getRawX();
                firstY_point = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:

                float finalX = event.getRawX();
                float finalY = event.getRawY();
                int distanceX = (int) (finalX - firstX_point);
                int distanceY = (int) (finalY - firstY_point);

                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    if ((firstX_point < finalX)) {
                        //Log.d("Test", "Left to Right swipe performed");

                    } else {
                        //Log.d("Test", "Right to Left swipe performed");

                    }
                }else{
                    if ((firstY_point < finalY)) {
                        //Log.d("Test", "Up to Down swipe performed");
                        closeTools();
                    } else {
                        //Log.d("Test", "Down to Up swipe performed");

                    }
                }


                break;
        }

        return true;
    }

    void pileLaunch(ImageView pileH, ImageView pileB, ImageView faceH, ImageView faceB, ImageView pileWin,ImageView faceWin){

        pileB.setVisibility(ImageView.VISIBLE);
        faceB.setVisibility(ImageView.GONE);

        new CountDownTimer(150, 10) {

            public void onTick(long millisUntilFinished) {
                pileB.setTranslationX(pileB.getTranslationX()+2);
                pileB.setTranslationY(pileB.getTranslationY()+2);

                pileH.setTranslationX(pileH.getTranslationX()-2);
                pileH.setTranslationY(pileH.getTranslationY()-2);
            }
            public void onFinish() {


                new CountDownTimer(150, 10) {
                    public void onTick(long millisUntilFinished) {
                        pileB.setTranslationX(pileB.getTranslationX()-4);
                        pileB.setTranslationY(pileB.getTranslationY()-4);

                        pileH.setTranslationX(pileH.getTranslationX()+4);
                        pileH.setTranslationY(pileH.getTranslationY()+4);


                        pileH.setTranslationX(pileH.getTranslationX()-1);
                        pileB.setTranslationX(pileB.getTranslationX()+1);
                    }
                    public void onFinish() {
                        randomSound(); //SOUND EFFECT

                        pileB.setTranslationX(pileB.getTranslationX()+1);
                        pileH.setTranslationX(pileH.getTranslationX()-1);


                        pileB.setVisibility(ImageView.INVISIBLE);
                        pileH.setVisibility(ImageView.INVISIBLE);
                        pileWin.setVisibility(ImageView.VISIBLE);
                        intWin = 1;

                    }
                }.start();
            }
        }.start();
    }


    void faceLaunch(ImageView pileH, ImageView pileB, ImageView faceH, ImageView faceB, ImageView pileWin,ImageView faceWin){

        pileH.setVisibility(ImageView.GONE);
        faceH.setVisibility(ImageView.VISIBLE);

        new CountDownTimer(150, 10) {
            public void onTick(long millisUntilFinished) {

                faceH.setTranslationX(faceH.getTranslationX()-2);
                faceH.setTranslationY(faceH.getTranslationY()-2);

                faceB.setTranslationX(faceB.getTranslationX()+2);
                faceB.setTranslationY(faceB.getTranslationY()+2);
            }
            public void onFinish() {


                new CountDownTimer(150, 10) {
                    public void onTick(long millisUntilFinished) {

                        faceH.setTranslationX(faceH.getTranslationX()+4);
                        faceH.setTranslationY(faceH.getTranslationY()+4);
                        faceH.setTranslationX(faceH.getTranslationX()-1);

                        faceB.setTranslationX(faceB.getTranslationX()-4);
                        faceB.setTranslationY(faceB.getTranslationY()-4);
                        faceB.setTranslationX(faceB.getTranslationX()+1);
                    }
                    public void onFinish() {

                        randomSound(); //SOUND EFFECT

                        faceH.setTranslationX(faceH.getTranslationX()-1);
                        faceB.setTranslationX(faceB.getTranslationX()+1);

                        faceB.setVisibility(ImageView.INVISIBLE);
                        faceH.setVisibility(ImageView.INVISIBLE);
                        faceWin.setVisibility(ImageView.VISIBLE);
                        intWin = 2;

                    }
                }.start();
            }
        }.start();
    }




    void unPushed(ImageView pileH, ImageView pileB, ImageView faceH, ImageView faceB, ImageView pileWin,ImageView faceWin){

        pileH.setTranslationX(22);
        faceB.setTranslationX(-22);

        pileH.setTranslationY(32);
        faceB.setTranslationY(-32);


        faceB.setVisibility(ImageView.VISIBLE);
        pileH.setVisibility(ImageView.VISIBLE);
        textFaceWin.setVisibility(TextView.INVISIBLE);
        textPileWin.setVisibility(TextView.INVISIBLE);

        faceWin.setVisibility(ImageView.GONE);
        pileWin.setVisibility(ImageView.GONE);



        new CountDownTimer(150, 10) {

            public void onTick(long millisUntilFinished) {
                faceB.setTranslationX(faceB.getTranslationX()+4);
                faceB.setTranslationY(faceB.getTranslationY()+4);

                pileH.setTranslationX(pileH.getTranslationX()-4);
                pileH.setTranslationY(pileH.getTranslationY()-4);


                pileH.setTranslationX(pileH.getTranslationX()+1);
                faceB.setTranslationX(faceB.getTranslationX()-1);
            }
            public void onFinish() {

                faceH.setTranslationX(0);
                faceH.setTranslationY(0);

                faceB.setTranslationX(0);
                faceB.setTranslationY(0);

                pileH.setTranslationX(0);
                pileH.setTranslationY(0);

                pileB.setTranslationX(0);
                pileB.setTranslationY(0);
            }
        }.start();


    }



    void game(ImageView pileH, ImageView pileB, ImageView faceH, ImageView faceB, ImageView pileWin,ImageView faceWin,RelativeLayout layoutAnim){

        Random random = new Random();
        int nb;
        nb = random.nextInt(2);

        if(nb == 0){

            new CountDownTimer(1500, 1500) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    pileLaunch(pileH,pileB,faceH,faceB,pileWin,faceWin);

                    new CountDownTimer(300, 300) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            textPileWin.setVisibility(TextView.VISIBLE);

                            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
                            textPileWin.startAnimation(startAnimation);

                            running = false;

                        }
                    }.start();


                }
            }.start();

        }



        else if(nb == 1){

            new CountDownTimer(1500, 1500) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    faceLaunch(pileH,pileB,faceH,faceB,pileWin,faceWin);


                    new CountDownTimer(300, 300) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            textFaceWin.setVisibility(TextView.VISIBLE);

                            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
                            textFaceWin.startAnimation(startAnimation);

                            running = false;

                        }
                    }.start();
                }
            }.start();

        }

        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        layoutAnim.startAnimation(rotate);

        pushed = true;

    }




    void closeTools(){

        if(opened) {
            Animation AnimClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate45deg_right);

            if (intmode == 0) {
                imgToolsLight.startAnimation(AnimClose);
            } else if (intmode == 1) {
                imgToolsDark.startAnimation(AnimClose);
            }

            Transition slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(mainToolsLayout);
            slide.setDuration(50);
            slide.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    LToolsBlack.setVisibility(RelativeLayout.GONE);

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
            TransitionManager.beginDelayedTransition(transitionsContainer, slide);


            mainToolsLayout.setVisibility(RelativeLayout.GONE);


            mainLaunch.setVisibility(Button.VISIBLE);


            //Switch Gone
            toolTitle.setVisibility(TextView.GONE);


            LToolsSlide.setVisibility(RelativeLayout.GONE);


            LToolsSlide.setVisibility(RelativeLayout.GONE);


            opened = false;
        }
    }





    void randomSound(){

        boolean music = switchMusic.isChecked();

        if(music){


        Random random = new Random();
        int nb;
        nb = random.nextInt(2);

        if(nb == 0){
            bong1.start();
        }
        else if(nb == 1){
            bong2.start();
        }
        /*else if(nb == 2) {
            bong3.start();
        }*/

        }
    }


}