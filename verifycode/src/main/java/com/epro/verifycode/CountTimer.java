package com.epro.verifycode;

import android.os.CountDownTimer;


import java.lang.ref.WeakReference;

public class CountTimer<T> extends CountDownTimer {

    private WeakReference<T> mActivityWeakReference;

    public boolean isSetOld() {
        return isSetOld;
    }

    public void setSetOld(boolean setOld) {
        isSetOld = setOld;
    }

    private boolean isSetOld;

    public CountTimer(long millisInFuture, long countDownInterval, T activityWeakReference) {
        super(millisInFuture, countDownInterval);
        mActivityWeakReference = new WeakReference<>(activityWeakReference);
    }



    @Override
    public void onTick(long millisUntilFinished) {
        if(mActivityWeakReference!=null && mActivityWeakReference.get()!=null) {

            if(mActivityWeakReference.get() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity)mActivityWeakReference.get();
                if(mainActivity !=null) {
                    if (isSetOld() && mainActivity.etCode1!=null) {
                        //老手机号
                        mainActivity.etCode1.setText("验证码(" + millisUntilFinished/1000 + "秒)");
                        mainActivity.etCode1.setEnabled(false);
                    } else if(!isSetOld() && mainActivity.tvCode2!=null) {
                        //新手机号
                        mainActivity.tvCode2.setText("验证码(" + millisUntilFinished/1000 + "秒)");
                        mainActivity.tvCode2.setEnabled(false);
                    }
                }
            }

        } else {
            cancel();
        }
    }

    @Override
    public void onFinish() {
        if(mActivityWeakReference!=null && mActivityWeakReference.get()!=null) {

           if(mActivityWeakReference.get() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity)mActivityWeakReference.get();
                if(mainActivity !=null) {
//
                    if(mainActivity !=null) {
                        if (isSetOld() && mainActivity.etCode1!=null) {
                            //老手机号
                            mainActivity.etCode1.setText("获取验证码");
                            mainActivity.etCode1.setEnabled(true);
                        } else if(!isSetOld() && mainActivity.tvCode2!=null) {
                            //新手机号
                            mainActivity.tvCode2.setText("获取验证码");
                            mainActivity.tvCode2.setEnabled(true);
                        }
                    }



                }
            }

        } else {
            cancel();
        }
    }
}
