package com.cyrillrx.android.demo.cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyrillrx.android.demo.PopLayout;
import com.cyrillrx.android.demo.R;

/**
 * @author Cyril Leroux
 *         Created 14/12/2014.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public enum ScrollType {HORIZONTAL, VERTICAL, GRID}

    private String[] mDataSet;
    private ScrollType mScrollType;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;

        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public CardAdapter(String[] dataSet, ScrollType scrollType) {
        mDataSet = dataSet;
        mScrollType = scrollType;
    }

    public CardAdapter(String[] dataSet) {
        this(dataSet, ScrollType.VERTICAL);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemRes(), parent, false);
        //TODO Set the view's size, margins, paddings and layout parameters here
        return new ViewHolder((CardView) v);
    }

    private int getItemRes() {
        switch (mScrollType) {

            case HORIZONTAL:
                return R.layout.card_item_h;

            case VERTICAL:
                return R.layout.card_item_v;

            // Grid
            default:
                return R.layout.card_item;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView) viewHolder.mCardView.findViewById(R.id.info_text)).setText(mDataSet[position]);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if (v.getContext() instanceof CardGridActivity) {
                    final CardGridActivity activity = (CardGridActivity) v.getContext();
                    final PopLayout popLayout = activity.showPopup(0,0);
                    v.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                activity.hidePopup();
                                return true;
                            } else {
                                popLayout.onTouchEvent(event);
                            }
                            return false;
                        }
                    });
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() { return mDataSet.length; }
}
