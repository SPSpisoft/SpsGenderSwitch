package com.spisoft.spsswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SpGenderSwitch extends RelativeLayout {
    private static final int GENRE_MALE = 1, GENRE_FEMALE = 2;
    private Context mContext;
    private View rootView;
    private long animate_duration = 600;
    private int mVal = -1;
    private ImageView iGenre;
    private TextView iText;
    private String TitleGender, TitleMale, TitleFemale;
    private int TextColor;
    OnChangeValueListener mListener;
    private Drawable Isrc_1, Isrc_2, IBackGround;
    private int SetAnimate;

    public interface OnChangeValueListener {
        void onEvent();
    }

    public void setChangeValueListener(OnChangeValueListener eventListener) {
        mListener = eventListener;
    }

    public SpGenderSwitch(Context context) {
        super(context);
        init(context,null);
    }

    public SpGenderSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SpGenderSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpGenderSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context, AttributeSet attrs){

        mContext = context;

        rootView = inflate(context, R.layout.sps_switch, this);

        iGenre = rootView.findViewById(R.id.image_sex);
        iText = rootView.findViewById(R.id.i_text);
        TitleGender = getResources().getString(R.string.title_gender);
        TitleMale = getResources().getString(R.string.title_male);
        TitleFemale = getResources().getString(R.string.title_female);
        IBackGround = getResources().getDrawable(R.drawable.i_gender);
        Isrc_1 = getResources().getDrawable(R.drawable.i_male);
        Isrc_2 = getResources().getDrawable(R.drawable.i_female);

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpGenderSwitch, 0, 0);

            iGenre.getLayoutParams().height = (int) typedArray.getDimension(R.styleable.SpGenderSwitch_SwitchSize,R.dimen.sps_lpr_sz_30);
            iGenre.getLayoutParams().width = (int) typedArray.getDimension(R.styleable.SpGenderSwitch_SwitchSize,R.dimen.sps_lpr_sz_30);

            String _TitleGender = typedArray.getString(R.styleable.SpGenderSwitch_TitleMain);
            if(_TitleGender != null) TitleGender = _TitleGender;
            String _TitleMale = typedArray.getString(R.styleable.SpGenderSwitch_TitleOption1);
            if(_TitleMale != null) TitleMale = _TitleMale;
            String _TitleFemale = typedArray.getString(R.styleable.SpGenderSwitch_TitleOption2);
            if(_TitleFemale != null) TitleFemale = _TitleFemale;
            if(!typedArray.getBoolean(R.styleable.SpGenderSwitch_TitleShow,true))
                iText.setVisibility(GONE);

            Drawable _Src_Background = typedArray.getDrawable(R.styleable.SpGenderSwitch_SrcBackground);
            if(_Src_Background != null) IBackGround = _Src_Background;
            Drawable _Src_1 = typedArray.getDrawable(R.styleable.SpGenderSwitch_SrcOption1);
            if(_Src_1 != null) Isrc_1 = _Src_1;
            Drawable _Src_2 = typedArray.getDrawable(R.styleable.SpGenderSwitch_SrcOption2);
            if(_Src_2 != null) Isrc_2 = _Src_2;
            if(typedArray.getBoolean(R.styleable.SpGenderSwitch_SetDefault,false)) mVal = 1;
            SetAnimate = typedArray.getInt(R.styleable.SpGenderSwitch_AnimateType,0);

            TextColor = typedArray.getColor(R.styleable.SpGenderSwitch_TextColor, Color.BLACK);
            typedArray.recycle();
        }

        SwitchView(mVal);

        iText.setText(TitleGender);
        iText.setTextColor(TextColor);

        iGenre.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mVal = -1;
                SwitchView(mVal);
                if(mListener!=null)
                    mListener.onEvent();
                return true;
            }
        });
        iGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch(view, SetAnimate);
            }
        });
    }

    private void Switch(View view, int i) {
        Animation animation;
        switch (i) {
            default:
                animation = new RotateAnimation(0, 160, 0, view.getHeight() + 20);
                break;

            case 1:
                float xCurrentPos = view.getLeft();
                float yCurrentPos = view.getTop();
                float yHeight = view.getHeight();
                animation = new TranslateAnimation(xCurrentPos, xCurrentPos, yCurrentPos, yCurrentPos+yHeight);
                break;

        }

        animation.setDuration(animate_duration);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iText.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iGenre.setVisibility(GONE);
                if (mVal != GENRE_MALE) {
                    mVal = GENRE_MALE;
                } else {
                    mVal = GENRE_FEMALE;
                }
                SwitchView(mVal);
                if(mListener!=null)
                    mListener.onEvent();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void Switch_(View view, int i) {
        float xCurrentPos = view.getLeft();
        float yCurrentPos = view.getTop();
        float yHeight = view.getHeight();

        Animation animation = new TranslateAnimation(xCurrentPos, xCurrentPos, yCurrentPos, yCurrentPos+yHeight);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iText.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iGenre.setVisibility(GONE);
                if (mVal != GENRE_MALE) {
                    mVal = GENRE_MALE;
                } else {
                    mVal = GENRE_FEMALE;
                }
                SwitchView(mVal);
                if(mListener!=null)
                    mListener.onEvent();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void SwitchView(int mVal) {

        iGenre.setVisibility(VISIBLE);
        switch (mVal){
            case GENRE_MALE:
                iGenre.setImageDrawable(Isrc_1);
                iText.setText(TitleMale);
                break;
            case GENRE_FEMALE:
                iGenre.setImageDrawable(Isrc_2);
                iText.setText(TitleFemale);
                break;
            default:
                iGenre.setImageDrawable(IBackGround);
                iText.setText(TitleGender);
                break;
        }
    }

    public int GetValue(){
        return mVal;
    }

    public void SetValue(int val){
        SwitchView(val);
    }

    public String GetText(){
        return iText.getText().toString();
    }
}
