package com.bwie.ontutch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Yuan yuan;
    private int screenWidth;
    private int screenHeight;
    float lastDistance = -1;
    float x0 = 0;
    float y0 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yuan = (Yuan) findViewById(R.id.yuan);
        // 获得屏幕宽高 不让出界
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 200;
        //添加点击触摸事件
        yuan.setOnTouchListener(new View.OnTouchListener() {
            private float x;
            private float y;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    //按下
                    case MotionEvent.ACTION_DOWN:
                        x = event.getRawX();
                        y = event.getRawY();
                        break;
                    //移动
                    case MotionEvent.ACTION_MOVE:
                        // 得到触摸点的个数
                        int count = event.getPointerCount();
                        if (count > 1) {
                            // 获得两点的坐标差
                            float distanceX = event.getX(0) - event.getX(1);
                            float distanceY = event.getY(0) - event.getY(1);
                            // 获得两点之间的距离
                            float betweenDistance = (float) Math.sqrt(distanceX
                                    * distanceX + distanceY * distanceY);
                            if (betweenDistance < 1) {//没有成功
                                lastDistance = betweenDistance;
                            }else if ((betweenDistance - lastDistance) > 5){
                                // 放大
                                lastDistance = betweenDistance;
                                // 获得布局参数
                                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) yuan
                                        .getLayoutParams();
                                params.width = (int) (yuan.getWidth() * 1.1f);
                                params.height = (int) (yuan.getHeight() * 1.1f);
                                yuan.setLayoutParams(params);
                            }else if ((lastDistance - betweenDistance) > 5){
                                // 缩小
                                lastDistance = betweenDistance;
                                // 获得布局参数
                                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) yuan
                                        .getLayoutParams();
                                params.width = (int) (yuan.getWidth() * 0.9f);
                                params.height = (int) (yuan.getHeight() * 0.9f);
                                yuan.setLayoutParams(params);
                            }
                        }else if (count==1){
                            // 移动距离
                            float rawX = event.getRawX() - x;
                            float rawy = event.getRawY() - y;
                            // 定义新的
                            int left = (int) (yuan.getLeft() + rawX);
                            int top = (int) (yuan.getTop() + rawy);
                            int right = (int) (yuan.getRight() + rawX);
                            int bottom = (int) (yuan.getBottom() + rawy);
                            // 设置不能出界
                            if (left < 0) {
                                left = 0;
                                right = left + view.getWidth();

                            }

                            if (right > screenWidth) {
                                right = screenWidth;
                                left = right - view.getWidth();
                            }

                            if (top < 0) {
                                top = 0;
                                bottom = top + view.getHeight();

                                return true;
                            }

                            if (bottom > screenHeight) {
                                bottom = screenHeight;
                                top = bottom - view.getHeight();
                            }
                            // 赋值
                            yuan.layout(left, top, right, bottom);
                            // 改成新的按下作标
                            x = event.getRawX();
                            y = event.getRawY();

                        }
                        break;
                }

                return true;
            }
        });
    }
}
