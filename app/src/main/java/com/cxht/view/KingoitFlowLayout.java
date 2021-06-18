package com.cxht.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxht.bean.Item;
import com.gongan.manage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ʽ����
 * Created by HeJiaMing on 2020/10/14 16:25
 * E-Mail Address��1774690@qq.com
 */
public  class KingoitFlowLayout extends ViewGroup {
    //��¼ÿ��View��λ��
    private List <ChildPos> mChildPos = new ArrayList <ChildPos>();
    private float textSize;
    private int textColor;
    private int textColorSelector;
    private float shapeRadius;
    private int shapeLineColor;
    private int shapeBackgroundColor;
    private int shapeBackgroundColorSelector;
    private float shapeLineWidth;
    private int deleteBtnColor;
    /**
     * �Ƿ��ǿ�ɾ��ģʽ
     */
    private boolean isDeleteMode;
    /**
     * ��¼����ѡ���ŵĴ�
     */
    private List<String> mAllSelectedWords = new ArrayList<>();

    private class ChildPos {
        int left, top, right, bottom;

        public ChildPos(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }

    public KingoitFlowLayout(Context context) {
        this(context, null);
    }

    public KingoitFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initAttributes(context, attrs);
    }

    /**
     * ���յ���������췽��
     *
     * @param context  ������
     * @param attrs    xml���Լ���
     * @param defStyle Theme�ж����style
     */
    public KingoitFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * ��ʽ������������
     *
     * @param context
     * @param attrs
     */
    @SuppressLint("ResourceAsColor")
    private void initAttributes(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KingoitFlowLayout);
        textSize = typedArray.getDimension(R.styleable.KingoitFlowLayout_flowLayoutTextSize, 16);
        textColor = typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutTextColor, Color.parseColor("#FF4081"));
        textColorSelector = typedArray.getResourceId(R.styleable.KingoitFlowLayout_flowLayoutTextColorSelector, 0);
        shapeRadius = typedArray.getDimension(R.styleable.KingoitFlowLayout_flowLayoutRadius, 40f);
        shapeLineColor = typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutLineColor, Color.parseColor("#ADADAD"));
        shapeBackgroundColor = typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutBackgroundColor, Color.parseColor("#c5cae9"));
        shapeBackgroundColorSelector = typedArray.getResourceId(R.styleable.KingoitFlowLayout_flowLayoutBackgroundColorSelector, 0);
        shapeLineWidth = typedArray.getDimension(R.styleable.KingoitFlowLayout_flowLayoutLineWidth, 4f);
        deleteBtnColor = typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutDeleteBtnColor, Color.GRAY);
    }

    /**
     * ������Ⱥ͸߶�
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //��ȡ��ʽ���ֵĿ�Ⱥ�ģʽ
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //��ȡ��ʽ���ֵĸ߶Ⱥ�ģʽ
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //ʹ��wrap_content����ʽ���ֵ����տ�Ⱥ͸߶�
        int width = 0, height = 0;
        //��¼ÿһ�еĿ�Ⱥ͸߶�
        int lineWidth = 0, lineHeight = 0;
        //�õ��ڲ�Ԫ�صĸ���
        int count = getChildCount();
        mChildPos.clear();
        for (int i = 0; i < count; i++) {
            //��ȡ��Ӧ������view
            View child = getChildAt(i);
            //������view�Ŀ�͸�
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //��viewռ�ݵĿ��
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //��viewռ�ݵĸ߶�
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //����
            if (lineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                //ȡ�����п�Ϊ��ʽ���ֿ��
                width = Math.max(width, lineWidth);
                //�����иߵõ���ʽ���ָ߶�
                height += lineHeight;
                //�����п��Ϊ��һ��View�Ŀ��
                lineWidth = childWidth;
                //�����и߶�Ϊ��һ��View�ĸ߶�
                lineHeight = childHeight;
                //��¼λ��
                mChildPos.add(new ChildPos(
                        getPaddingLeft() + lp.leftMargin,
                        getPaddingTop() + height + lp.topMargin,
                        getPaddingLeft() + childWidth - lp.rightMargin,
                        getPaddingTop() + height + childHeight - lp.bottomMargin));
            } else {  //������
                //��¼λ��
                mChildPos.add(new ChildPos(
                        getPaddingLeft() + lineWidth + lp.leftMargin,
                        getPaddingTop() + height + lp.topMargin,
                        getPaddingLeft() + lineWidth + childWidth - lp.rightMargin,
                        getPaddingTop() + height + childHeight - lp.bottomMargin));
                //������View��ȵõ����п��
                lineWidth += childWidth;
                //ȡ��ǰ����View���߶���Ϊ�и߶�
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //���һ���ؼ�
            if (i == count - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        // �õ����յĿ��
        // ��ȣ������AT_MOSTģʽ����ʹ�����Ǽ���õ��Ŀ��ֵ��������ѭ����ֵ
        // �߶ȣ�ֻҪ���������ݵĸ߶ȴ��ڲ����߶ȣ���ʹ�����ݸ߶ȣ����Ӳ���ģʽ���������ʹ�ò����߶�
        int flowLayoutWidth = widthMode == MeasureSpec.AT_MOST ? width + getPaddingLeft() + getPaddingRight() : widthSize;
        int flowLayoutHeight = heightMode == MeasureSpec.AT_MOST ? height + getPaddingTop() + getPaddingBottom() : heightSize;
        //��ʵ�߶�
        realHeight = height + getPaddingTop() + getPaddingBottom();
        //�����߶�
        measuredHeight = heightSize;
        if (heightMode == MeasureSpec.EXACTLY) {
            realHeight = Math.max(measuredHeight, realHeight);
        }
        scrollable = realHeight > measuredHeight;
        // �������յĿ��
        setMeasuredDimension(flowLayoutWidth, flowLayoutHeight);
    }

    /**
     * ��ViewGroup�ܹ�֧��margin����
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * ����ÿ��View��λ��
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ChildPos pos = mChildPos.get(i);
            //����View����ߡ��ϱߡ��ұߵױ�λ��
            child.layout(pos.left, pos.top, pos.right, pos.bottom);
        }
    }

    public void addItemView(LayoutInflater inflater, String tvName) {
        //���� ItemView���������ƣ�����������
        View view = inflater.inflate(R.layout.kingoit_flow_layout, this, false);
        ImageView delete = view.findViewById(R.id.delete);
        if (isDeleteMode) {
            delete.setVisibility(VISIBLE);
        } else {
            delete.setVisibility(GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            delete.setImageTintList(ColorStateList.valueOf(deleteBtnColor));
        }
        TextView textView = view.findViewById(R.id.value);
        textView.setTextSize(textSize / getContext().getResources().getDisplayMetrics().scaledDensity);
        if (textColorSelector != 0) {
            ColorStateList csl = getResources().getColorStateList(textColorSelector);
            textView.setTextColor(csl);
        } else {
            textView.setTextColor(textColor);
        }
        textView.setPadding(20, 4, 20, 4);
        textView.setText(tvName);
        //��̬����shape
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(shapeRadius);
        drawable.setStroke((int) shapeLineWidth, shapeLineColor);
        if (shapeBackgroundColorSelector != 0) {
            ColorStateList csl = getResources().getColorStateList(shapeBackgroundColorSelector);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setColor(csl);
            }
        } else {
            drawable.setColor(shapeBackgroundColor);
        }
        textView.setBackgroundDrawable(drawable);

        //�� ItemView������ʽ����
        this.addView(view);
    }


    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        isDeleteMode = deleteMode;
    }

    //---20180815---�޸����ɻ���bug----start----
    private boolean scrollable; // �Ƿ���Թ���
    private int measuredHeight; // �����õ��ĸ߶�
    private int realHeight; // ������ʽ���ֿؼ���ʵ�ʸ߶�
    private int scrolledHeight = 0; // �Ѿ��������ĸ߶�
    private int startY; // ���λ�����ʼ��Y����λ��
    private int offsetY; // ���λ�����ƫ����
    private boolean pointerDown; // ��ACTION_MOVE�У��ӵ�һ�δ���Ϊ��ָ���£��ӵڶ��δ�����ʼ������ʽ����

    /**
     * �����¼��Ĵ��������ֿ��Թ��������ݸ߶ȴ��ڲ����߶ȣ�ʱ�������Ʋ������д���
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // ֻ�е����ֿ��Թ�����ʱ�����ݸ߶ȴ��ڲ����߶ȵ�ʱ�򣩣��Ҵ�������ģʽ���Ż�����Ʋ������д���
        if (scrollable && isInterceptedTouch) {
            int currY = (int) event.getY();
            switch (event.getAction()) {
                // ��ΪACTION_DOWN���ƿ�����Ϊ�˵�������е�ĳ����Ԫ�أ������onInterceptTouchEvent()������û�������������
                // ��ˣ�������¼��в��ܻ�ȡ��startY��Ҳ��˲Ž�startY�Ļ�ȡ�ƶ�����һ�ι�����ʱ�����
                case MotionEvent.ACTION_DOWN:
                    break;
                // ����һ�δ���ACTION_MOVE�¼�ʱ����Ϊ��ָ���£��Ժ��ACTION_MOVE�¼�����Ϊ�����¼�
                case MotionEvent.ACTION_MOVE:
                    // ��pointerDown��־λֻ����ָ�Ƿ��Ѿ�����
                    if (!pointerDown) {
                        startY = currY;
                        pointerDown = true;
                    } else {
                        offsetY = startY - currY; // �»�����0
                        // �����е����ݸ�����ָ�Ĺ���������
                        // ��scrolledHeight��¼��ǰ�Ĺ����¼��й������ĸ߶ȣ���Ϊ��һ��ÿһ�ι������ǴӲ��ֵ���˿�ʼ�ģ�
                        this.scrollTo(0, scrolledHeight + offsetY);
                    }
                    break;
                // ��ָ̧��ʱ������scrolledHeight��ֵ��
                // ����������磨���������ڲ�����˻���ڲ�����Ͷ˵�ʱ�򣩣����ù����ص����ֵı߽紦
                case MotionEvent.ACTION_UP:
                    scrolledHeight += offsetY;
                    if (scrolledHeight + offsetY < 0) {
                        this.scrollTo(0, 0);
                        scrolledHeight = 0;
                    } else if (scrolledHeight + offsetY + measuredHeight > realHeight) {
                        this.scrollTo(0, realHeight - measuredHeight);
                        scrolledHeight = realHeight - measuredHeight;
                    }
                    // ��ָ̧�����������������־λ
                    pointerDown = false;
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * �¼����أ�����ָ���»�̧���ʱ�򲻽������أ���Ϊ�����������ֻ�ǵ���˲����е�ĳ����Ԫ�أ���
     * ����ָ�ƶ���ʱ�򣬲Ž��¼����أ�
     * ��������С���������ֹ���ʱ�󴥻���
     */
    private boolean isInterceptedTouch;
    private int startYY = 0;
    private boolean pointerDownY;
    private int minDistance = 10;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int currY = (int) ev.getY();
        int offsetY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointerDownY = true;
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointerDownY) {
                    startYY = currY;
                } else {
                    offsetY = currY - startYY;
                }
                pointerDownY = false;
                intercepted = Math.abs(offsetY) > minDistance;
                break;
            case MotionEvent.ACTION_UP:
                // ��ָ̧�����������������־λ
                intercepted = false;
                break;
            default:
                break;
        }
        isInterceptedTouch = intercepted;
        return intercepted;
    }

    //---20180815---�޸����ɻ���bug----end----

    /**
     * ��ʽ������ʾ
     * Toast.makeText(FlowLayoutActivity.this, keywords, Toast.LENGTH_SHORT).show();
     *
     * @param list
     */
    public void showTag(final List<Item> list, final ItemClickListener listener) {
        removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            final Item keywords = list.get(i);
            addItemView(LayoutInflater.from(getContext()), keywords.getDisplay());
            final int finalI = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDeleteMode()) {
                        list.remove(keywords);
                        showTag(list, listener);
                    } else {
                        View child = getChildAt(finalI);
                        child.setSelected(!child.isSelected());
                        if (child.isSelected()) {
                            mAllSelectedWords.add(list.get(finalI).getDisplay());
                        } else {
                            mAllSelectedWords.remove(list.get(finalI));
                        }
                        listener.onClick(keywords.getDisplay(), mAllSelectedWords);
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        /**
         * item ����¼�
         *
         * @param currentSelectedkeywords
         * @param allSelectedKeywords
         */
        void onClick(String currentSelectedkeywords, List<String> allSelectedKeywords);
    }
}
