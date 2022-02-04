package com.ass.mcoerctest.utilities;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ass.mcoerctest.R;


public class Animations {

    public static void slide_down(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void slide_up(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }


    public static void fade_in(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void fade_out(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.fade_out);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void bounce(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.bounce);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }


    public static void blink(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.blink);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void zoom_in(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void zoom_out(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.zoom_out);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void entry(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.entry);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void exit(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.exit);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void slide_left(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_left);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }

    public static void slide_right(Context ctx, View view) {

        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_right);
        if (animation != null) {
            animation.reset();
            if (view != null) {
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }
    }
}
