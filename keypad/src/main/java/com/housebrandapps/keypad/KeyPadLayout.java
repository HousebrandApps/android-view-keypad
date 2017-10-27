package com.housebrandapps.keypad;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class KeyPadLayout extends LinearLayout implements KeyPadViewModel.KeyPadView {

    private static final int KEYPAD_DEFAULT_DISPLAYS = 4;
    private static final int KEYPAD_DEFAULT_COLUMNS = 3;

    private KeyPadViewModel viewModel;
    private DisplayLayout displayLayout;
    private KeypadOkayClickedListener okayClickedListener;

    public KeyPadLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public KeyPadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @BindingAdapter({"onOkayClicked"})
    public static void onOkayClicked(KeyPadLayout view, KeypadOkayClickedListener listener) {
        view.okayClickedListener = listener;
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.KeyPadLayout);
        initParams();

        int total = ta.getInteger(R.styleable.KeyPadLayout_total_displays, KEYPAD_DEFAULT_DISPLAYS);

        viewModel = new KeyPadViewModel(this, total);

        initPadDisplay(ta, total);
        initButtonsList(ta);

        ta.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewModel = null;
        okayClickedListener = null;
    }

    private void initPadDisplay(TypedArray ta, int total) {
        displayLayout = new DisplayLayout(
                getContext(),
                total, ta
        );
        addView(displayLayout);
    }

    private void initButtonsList(TypedArray ta) {
        RecyclerView keyPad = new RecyclerView(getContext());
        keyPad.setLayoutManager(new GridLayoutManager(getContext(), KEYPAD_DEFAULT_COLUMNS));

        int padding = ta.getDimensionPixelSize(R.styleable.KeyPadLayout_button_item_padding, getResources().getDimensionPixelSize(R.dimen.key_pad_padding));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(padding);
        keyPad.addItemDecoration(itemDecoration);

        String[] buttons = getResources().getStringArray(R.array.buttons);
        String clear = ta.getString(R.styleable.KeyPadLayout_button_item_clear_name);
        String ok = ta.getString(R.styleable.KeyPadLayout_button_item_ok_name);

        if (clear != null)
            buttons[KeyPadViewModel.KEY_CLEAR] = clear;
        if (ok != null)
            buttons[KeyPadViewModel.KEY_OKAY] = ok;

        KeyPadAdapter adapter = new KeyPadAdapter(getContext(), ta, buttons);
        adapter.setClickListener(new KeyPadAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewModel.buttonPressOn(position);
            }
        });
        keyPad.setAdapter(adapter);
        addView(keyPad);
    }

    private void initParams() {
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void updateDisplays(int pos, String text) {
        displayLayout.entry[pos].setText(text);
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    @Override
    public void okayClicked() {
        if (okayClickedListener != null) {
            okayClickedListener.onClick(this, viewModel.getValue());
        }
    }

    //Pin keypad entry buttons
    private static class KeyPadAdapter extends RecyclerView.Adapter<KeyPadAdapter.KeyPadViewHolder> {

        private Context context;
        private ItemClickListener mClickListener;
        private final String[] buttonLabels;
        private int background;
        private int innerPadding;

        KeyPadAdapter(Context context, TypedArray ta, String... labels) {
            this.context = context;
            this.buttonLabels = labels;
            this.background = ta.getResourceId(R.styleable.KeyPadLayout_button_background, 0);
            this.innerPadding = ta.getDimensionPixelSize(R.styleable.KeyPadLayout_button_inner_padding,
                    context.getResources().getDimensionPixelSize(R.dimen.key_pad_padding));
        }

        @Override
        public KeyPadViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Button button = new Button(context);
            if (background != 0)
                button.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), background));
            button.setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
            return new KeyPadViewHolder(button);
        }

        @Override
        public void onBindViewHolder(KeyPadViewHolder keyPadViewHolder, int i) {
            ((Button) keyPadViewHolder.itemView).setText(buttonLabels[i]);
        }

        @Override
        public int getItemCount() {
            return buttonLabels.length;
        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        public interface ItemClickListener {
            void onItemClick(int position);
        }

        class KeyPadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            KeyPadViewHolder(Button itemView) {
                super(itemView);
                itemView.setGravity(Gravity.CENTER);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mClickListener != null)
                    mClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    private class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset / 2, mItemOffset, mItemOffset / 2);
        }
    }

    //Pin Displays:
    private class DisplayLayout extends LinearLayout {

        private final EditText entry[];

        public DisplayLayout(Context context, int total, TypedArray ta) {
            super(context);
            entry = new EditText[total];

            int padding = ta.getDimensionPixelSize(R.styleable.KeyPadLayout_display_item_padding, getResources().getDimensionPixelSize(R.dimen.key_pad_padding));
            int textSize = ta.getDimensionPixelSize(R.styleable.KeyPadLayout_display_text_size, getResources().getDimensionPixelSize(R.dimen.key_pad_text_size));
            int backgroundId = ta.getResourceId(R.styleable.KeyPadLayout_display_background, 0);
            boolean hideEntries = ta.getBoolean(R.styleable.KeyPadLayout_display_item_hide, true);

            int paddingHalf = (int) (padding / 2f);

            setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            setOrientation(LinearLayout.HORIZONTAL);

            setPadding(0, paddingHalf, 0, paddingHalf);

            LinearLayout.LayoutParams layoutParams;

            for (int i = 0; i < entry.length; i++) {
                layoutParams = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT,
                        1f);

                entry[i] = new EditText(getContext());
                if (hideEntries) {
                    entry[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                entry[i].setFocusable(false);
                entry[i].setTextIsSelectable(false);
                entry[i].setTextSize(textSize);
                if (backgroundId != 0)
                    entry[i].setBackground(ContextCompat.getDrawable(getContext(), backgroundId));
                entry[i].setGravity(Gravity.CENTER);
                layoutParams.setMarginStart(padding);
                layoutParams.setMarginEnd(padding);

                addView(entry[i], layoutParams);
            }
        }
    }

    public interface KeypadOkayClickedListener {
        void onClick(KeyPadLayout view, String value);
    }
}
