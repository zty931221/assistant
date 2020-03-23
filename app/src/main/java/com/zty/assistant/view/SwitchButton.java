package com.zty.assistant.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zty.assistant.R;
import com.zty.assistant.utils.MyToast;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/18  9:25
 * desc   :
 * version: 1.0
 */
public class SwitchButton extends View {
    private static final String TAG = SwitchButton.class.getSimpleName();

    private boolean isChoose = false;// 记录当前按钮是否打开,true为打开,flase为关闭
    private boolean isChecked;
    private boolean onSlip = false;// 记录用户是否在滑动的变量
    private float down_x, now_x;// 按下时的x,当前的x
    private Rect btn_off, btn_on;// 打开和关闭状态下,游标的Rect .
    private boolean isChangeOn = false;
    private boolean isInterceptOn = false;
    private OnChangedListener onChangedListener;
    private Bitmap bg_on, bg_off, slip_btn;

    private final int DOUBLE_TAP_TIMEOUT = 200;
    private MotionEvent mCurrentDownEvent;
    private MotionEvent mPreviousUpEvent;


    public SwitchButton(Context context) {
        super(context);
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {// 初始化
        bg_on = BitmapFactory.decodeResource(getResources(), R.drawable.sb_open);
        bg_off = BitmapFactory.decodeResource(getResources(), R.drawable.sb_close);
        slip_btn = BitmapFactory.decodeResource(getResources(), R.drawable.sb);
        btn_off = new Rect(0, (bg_off.getHeight() - slip_btn.getHeight()) / 2, slip_btn.getWidth(),
                slip_btn.getHeight());
        btn_on = new Rect(bg_off.getWidth() - slip_btn.getWidth(),
                (bg_off.getHeight() - slip_btn.getHeight()) / 2, bg_off.getWidth(),
                slip_btn.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bg_on.getWidth(), bg_on.getHeight());
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {// 绘图函数

        super.onDraw(canvas);

        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x;
        float y;
        // 滑动到前半段与后半段的背景不同,在此做判断
        if(now_x < (bg_on.getWidth() / 2)) {
            // 画出关闭时的背景
            canvas.drawBitmap(bg_off, matrix, paint);
        } else {
            // 画出打开时的背景
            canvas.drawBitmap(bg_on, matrix, paint);
        }
        // 是否是在滑动状态
        if(onSlip) {
            if(now_x >= bg_on.getWidth()) {// 是否划出指定范围,不能让游标跑到外头,必须做这个判断
                x = bg_on.getWidth() - slip_btn.getWidth() / 2;// 减去游标1/2的长度...
            } else if(now_x < 0) {
                x = 0;
            } else {
                x = now_x - slip_btn.getWidth() / 2;
            }
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {// 非滑动状态
            if(isChoose) {// 根据现在的开关状态设置画游标的位置
                x = btn_on.left;
                canvas.drawBitmap(bg_on, matrix, paint);// 初始状态为true时应该画出打开状态图片
            } else {
                x = btn_off.left;
            }
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        if(isChecked) {
            canvas.drawBitmap(bg_on, matrix, paint);
            x = btn_on.left;
            isChecked = !isChecked;
        }

        // 对游标位置进行异常判断...
        if(x < 0) {
            x = 0;
        } else if(x > bg_on.getWidth() - slip_btn.getWidth()) {
            x = bg_on.getWidth() - slip_btn.getWidth();
        }
        y = (bg_on.getHeight() - slip_btn.getHeight()) >> 1;
        // 画出游标.
        canvas.drawBitmap(slip_btn, x, y, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean old = isChoose;
        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                now_x = event.getX();
                break;
            case MotionEvent.ACTION_DOWN:
                if (mPreviousUpEvent != null && mCurrentDownEvent != null && isConsideredDoubleTap(mCurrentDownEvent,mPreviousUpEvent,event)){
//                    Toast.makeText(getContext(),"操作频繁请稍后再试",Toast.LENGTH_LONG).show();
                    MyToast.getInstance().showLong("操作频繁请稍后再试");
                    return false;
                }
                mCurrentDownEvent = MotionEvent.obtain(event);
                if(event.getX() > bg_on.getWidth() || event.getY() > bg_on.getHeight()) {
                    return false;
                }
                onSlip = true;
                down_x = event.getX();
                now_x = down_x;
                break;
            // 移到控件外部
            case MotionEvent.ACTION_CANCEL:
                onSlip = false;
                boolean choose = isChoose;

                if(now_x >= (bg_on.getWidth() / 2)) {
                    now_x = bg_on.getWidth() - slip_btn.getWidth() / 2;
                    isChoose = true;
                } else {
                    now_x = now_x - slip_btn.getWidth() / 2;
                    isChoose = false;
                }

                // 如果设置了监听器,就调用其方法..
                if(isChangeOn && (choose != isChoose)) {
                    onChangedListener.onChanged(this, isChoose);
                }
                break;
            case MotionEvent.ACTION_UP:
                mPreviousUpEvent = MotionEvent.obtain(event);
                onSlip = false;
                //获取当前选中状态
                boolean lastChoose = isChoose;

                if(event.getX() >= (bg_on.getWidth() / 2)) {
                    now_x = bg_on.getWidth() - slip_btn.getWidth() / 2;
                    isChoose = true;
                } else {
                    now_x = now_x - slip_btn.getWidth() / 2;
                    isChoose = false;
                }
                // 相等表示点击状态未切换，之后切换状态
                if(lastChoose == isChoose) {
                    if(event.getX() >= (bg_on.getWidth() / 2)) {
                        now_x = 0;
                        isChoose = false;
                    } else {
                        now_x = bg_on.getWidth() - slip_btn.getWidth() / 2;
                        isChoose = true;
                    }
                }
                // 如果设置了监听器,就调用其方法..
                if(isChangeOn) {
                    onChangedListener.onChanged(this, isChoose);
                }
                break;
            default:
                break;
        }
        if(!old && isInterceptOn) {
            isChoose = false;
        } else {
            invalidate();// 重画控件
        }
        return true;
    }

    private boolean isConsideredDoubleTap(MotionEvent firstDown,
                                          MotionEvent firstUp, MotionEvent secondDown) {
        if (secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
            return false;
        }
        int deltaX = (int) firstUp.getX() - (int) secondDown.getX();
        int deltaY = (int) firstUp.getY() - (int) secondDown.getY();
        return deltaX * deltaX + deltaY * deltaY < 10000;
    }

    public void setOnChangedListener(OnChangedListener listener) {// 设置监听器,当状态修改的时候
        isChangeOn = true;
        onChangedListener = listener;
    }

    public interface OnChangedListener {
        void onChanged(View v, boolean checkState);
    }

    public void setCheck(boolean isChecked) {
        this.isChecked = isChecked;
        isChoose = isChecked;
        if(!isChecked) {
            now_x = 0;
        }
        invalidate();
    }

    public boolean isChoose() {
        return this.isChoose;
    }

//    public boolean getCheck() {
//        return this.isChecked;
//    }

    public void setInterceptState(boolean isIntercept) {// 设置监听器,是否在重画钱拦截事件,状态由false变true时 拦截事件
        isInterceptOn = isIntercept;
        // onInterceptListener = listener;
    }
}
