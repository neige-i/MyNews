package neige_i.mynews.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import neige_i.mynews.R;

/**
 * This RecyclerView ItemDecoration separates each item of the list with a divider.
 * This divider does not fill the item's width.
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Divider used to separate the RecyclerView's items.
     */
    private final Drawable mDivider;

    // -----------------------------------     CONSTRUCTORS     ------------------------------------

    public DividerDecoration(Context context) {
        // Initialize the divider
        TypedArray styledAttributes = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Resources r = parent.getContext().getResources();
        int left = (int) (parent.getPaddingLeft() // To set the left bound, add the parent padding...
                + r.getDimension(R.dimen.item_padding) // ... and the itemView padding...
                + r.getDimension(R.dimen.image_width) // ... and the ImageView (thumbnail) width...
                + r.getDimension(R.dimen.image_margin_end)); // ... and marginEnd
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            int top = child.getBottom() + ((RecyclerView.LayoutParams) child.getLayoutParams()).bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
