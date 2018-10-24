package com.github.barteksc.pdfviewer.scroll;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.R;
import com.xw.repo.BubbleSeekBar;

public class DefaultScrollHandle extends RelativeLayout implements ScrollHandle,BubbleSeekBar.OnProgressChangedListener{

    protected Context context;
    private boolean inverted;
    private PDFView pdfView;
    private BubbleSeekBar bubbleSeekBar;
    private ImageView back;
    private int lastPage;

    private View layoutView;
    private View topView;
    private View bottomView;
    private ImageView outlineButton;

    private Handler handler = new Handler();
    private Runnable hidePageScrollerRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public DefaultScrollHandle(Context context) {
        this(context, false);
    }

    public DefaultScrollHandle(Context context, boolean inverted) {
        super(context);
        this.context = context;
        this.inverted = inverted;
        setVisibility(INVISIBLE);
    }

    @Override
    public void setupLayout(final PDFView pdfView) {

        int align;
        // determine handler position, default is right (when scrolling vertically) or bottom (when scrolling horizontally)
        if (pdfView.isSwipeVertical()) {
            if (inverted) { // left
                align = ALIGN_PARENT_LEFT;
            } else { // right
                align = ALIGN_PARENT_RIGHT;
            }
        } else {
            if (inverted) { // top
                align = ALIGN_PARENT_TOP;
            } else { // bottom
                align = ALIGN_PARENT_BOTTOM;
            }
        }

        layoutView = View.inflate(context ,R.layout.operator_layout , null) ;


        topView = layoutView.findViewById(R.id.top_operator) ;
        topView.setVisibility(INVISIBLE);
        back = topView.findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottom(false);
                topView.setVisibility(INVISIBLE);
                setVisibility(INVISIBLE);
                pdfView.setPaintEditModel(false);
            }
        });

        bottomView = layoutView.findViewById(R.id.bottom_container) ;
        bottomView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("q", ""+v.getId());
                return true;
            }
        });
        bottomView.setVisibility(INVISIBLE);

        bubbleSeekBar = bottomView.findViewById(R.id.bsb);
        bubbleSeekBar.getConfigBuilder().min(1).touchToSeek().max(pdfView.getPageCount()).build();
        bubbleSeekBar.setOnProgressChangedListener(this);

        outlineButton = bottomView.findViewById(R.id.outline_button);
        outlineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottom(false);
                topView.setVisibility(VISIBLE);
                pdfView.setPaintEditModel(true);
            }
        });

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
       // lp.addRule(align);

        LayoutParams bvlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bvlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        addView(layoutView, bvlp);

       /* LayoutParams tvlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        addView(topView,tvlp)*/;

        pdfView.addView(this,lp);
        this.pdfView = pdfView;
    }

    @Override
    public void destroyLayout() {
        pdfView.removeView(this);
    }

    @Override
    public void setScroll(float position) {
        if (shown()) {
           // hide();
        } else {
            handler.removeCallbacks(hidePageScrollerRunnable);
        }
    }

    @Override
    public void hideDelayed() {
        handler.postDelayed(hidePageScrollerRunnable, 10);
    }

    @Override
    public void setPageNum(int pageNum) {
        String text = String.valueOf(pageNum);
        lastPage = Integer.parseInt(text);

    }

    @Override
    public boolean shown() {
        return getVisibility() == VISIBLE;
    }

    @Override
    public void show() {
        showBottom();
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        hideBottom(true);
    }


    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        if (!isPDFViewReady() || lastPage == progress) {//
            return;
        }

        pdfView.resetZoom();
        pdfView.jumpTo(progress-1,true);

    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
       // pdfView.performPageSnap();
    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

    }

    private boolean isPDFViewReady() {
        return pdfView != null && pdfView.getPageCount() > 0 && !pdfView.documentFitsView();
    }


    private void showBottom(){
        Animation animation = new TranslateAnimation(0, 0, bottomView.getHeight(), 0);
        animation.setDuration(200);
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        bottomView.startAnimation(animation);//开始动画

        bubbleSeekBar.getConfigBuilder().progress(lastPage).alwaysShowBubbleDelay(200).build();
        bottomView.setVisibility(VISIBLE);
    }

    private void hideBottom(final boolean needInvisible){
        Animation animation = new TranslateAnimation(0, 0, 0, bottomView.getHeight());
        animation.setDuration(200);
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                bottomView.setVisibility(GONE);
                if(needInvisible){
                    setVisibility(INVISIBLE);
                }
            }
        });
        bottomView.startAnimation(animation);//开始动画
    }

}
