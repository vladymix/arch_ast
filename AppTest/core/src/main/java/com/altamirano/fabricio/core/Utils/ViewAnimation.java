package com.altamirano.fabricio.core.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ViewAnimation {
    public static void fadeOut(View view) {
        fadeOut(view, null);
    }

    public static void fadeOut(View view, final AnimListener animListener) {
        view.setAlpha(1.0f);
        view.animate().setDuration(500).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (animListener != null) {
                    animListener.onFinish();
                }
                super.onAnimationEnd(animator);
            }
        }).alpha(0);
    }

    public interface AnimListener {
        void onFinish();
    }

    public static void expand(View view) {
        view.startAnimation(expandAction(view));
    }

    public static void collapse(View view) {
        view.startAnimation(collapseAcction(view));
    }

    private static Animation expandAction(final View view) {
        view.measure(-1, -2);
        final int measuredHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);
        Animation c08302 = new Animation() {
            public boolean willChangeBounds() {
                return true;
            }

            protected void applyTransformation(float f, Transformation transformation) {
                view.getLayoutParams().height = f == 1.0f ? measuredHeight : (int)(((float) measuredHeight) * f);
                view.requestLayout();
            }
        };
        c08302.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
       // view.startAnimation(c08302);
        return c08302;
    }

    private static Animation collapseAcction(final View view) {
        final int measuredHeight = view.getMeasuredHeight();
        Animation c08313 = new Animation() {
            public boolean willChangeBounds() {
                return true;
            }

            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 1.0f) {
                    view.setVisibility(View.GONE);
                    return;
                }
                view.getLayoutParams().height = measuredHeight - ((int) (((float) measuredHeight) * f));
                view.requestLayout();
            }
        };
        c08313.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        //view.startAnimation(c08313);
        return c08313;
    }

    public static void fadeIn(View view) {
        fadeIn(view, null);
    }

    public static void fadeIn(final View view, final AnimListener animListener) {
        view.setVisibility(View.GONE);
        view.setAlpha(0.0f);
        view.animate().setDuration(200).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.VISIBLE);
                if (animListener != null) {
                    animListener.onFinish();
                }
                super.onAnimationEnd(animator);
            }
        }).alpha(1.0f);
    }

    public static void showIn(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
        view.setTranslationY((float) view.getHeight());
        view.animate().setDuration(200).translationY(0.0f).setListener(new AnimatorAdp()).alpha(1.0f).start();
    }

    public static void showOut(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1.0f);
        view.setTranslationY(0.0f);
        view.animate().setDuration(200).translationY((float) view.getHeight()).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                super.onAnimationEnd(animator);
            }
        }).alpha(0.0f).start();
    }

    /* renamed from: com.material.components.utils.ViewAnimation$8 */
    static class AnimatorAdp extends AnimatorListenerAdapter {
        AnimatorAdp() {
        }

        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
        }
    }

    public static void expand(View view, final AnimListener animListener) {
        Animation expandAction = expandAction(view);
        expandAction.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                animListener.onFinish();
            }
        });
        view.startAnimation(expandAction);
    }


    public static void changeBackgroundColor(final View view, int color){
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(300);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setIntValues(Color.WHITE, color);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });

        animator.start();

    }
}
