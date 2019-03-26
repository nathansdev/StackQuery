package com.nathansdev.stack.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nathansdev.stack.R;

public class TagView extends AppCompatTextView {
    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Returns single SkillsInterestItem.
     *
     * @param viewGroup item's view group.
     * @param text      text should be set.
     * @return SkillsInterest item.
     */
    public static TagView formView(ViewGroup viewGroup, String text) {
        @LayoutRes int layoutId = R.layout.layout_tag_view;
        TagView itemView = (TagView) LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);
        itemView.setText(text);
        return itemView;
    }
}
