package com.example.ben.atomkey;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 5;
    private static final int ROW_A_SIZE = 4;
    private static final int ROW_B_SIZE = 6;
    private static final int ROW_C_SIZE = 7;
    private static final int ROW_D_SIZE = 6;
    private static final int ROW_E_SIZE = 4;

    private static final int KEY_SIZE = 200;

    private static final double ROW_A_LEFT_OFFSET = 1.0; // Expressed as a factor of KEY_SIZE
    private static final double ROW_B_LEFT_OFFSET = 0.5;
    private static final double ROW_C_LEFT_OFFSET = 0;
    private static final double ROW_D_LEFT_OFFSET = 0.5;
    private static final double ROW_E_LEFT_OFFSET = 2.0;

    private TextView[] textViewsA;
    private TextView[] textViewsB;
    private TextView[] textViewsC;
    private TextView[] textViewsD;
    private TextView[] textViewsE;
    private ScrollView vScrollView;
    private HorizontalScrollView hScrollView;
    private LinearLayout linearLayoutRowA;
    private LinearLayout linearLayoutRowB;
    private LinearLayout linearLayoutRowC;
    private LinearLayout linearLayoutRowD;
    private LinearLayout linearLayoutRowE;
    private LinearLayout linearLayoutKeyboard;

    private TextView currentHighlightedKey;
    private int currentRow;
    private int currentColumn;
    private EditText field;

    private Button enterTextButton;
    private Button deleteButton;


    private TextView scrollXText;
    private TextView scrollYText;
    private TextView keyLengthsText;
    private TextView columnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            private float mx, my, curX, curY;
            private boolean started = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                curX = event.getX();
                curY = event.getY();
                int dx = (int) (mx - curX);
                int dy = (int) (my - curY);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (started) {
                            vScrollView.scrollBy(0, dy);
                            hScrollView.scrollBy(dx, 0);
                        } else {
                            started = true;
                        }
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        vScrollView.scrollBy(0, dy);
                        hScrollView.scrollBy(dx, 0);
                        started = false;
                        break;
                }
                return true;
            }
        };
        vScrollView.setOnTouchListener(touchListener);
        hScrollView.setOnTouchListener(touchListener);
        View.OnScrollChangeListener scrollListener = new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                updateHighlightedKey();
            }
        };
        vScrollView.setOnScrollChangeListener(scrollListener);
        hScrollView.setOnScrollChangeListener(scrollListener);
        enterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentHighlightedKey != null)
                    field.setText(field.getText().toString() + currentHighlightedKey.getTag());
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field.setText(field.getText().toString().substring(0, field.getText().length() - 1));
            }
        });
        field.setClickable(false);
        field.setFocusable(false);
        enterTextButton.requestFocus();

        setOffsets();
        setTextViewSize(KEY_SIZE);
        setBackgrounds();
        linearLayoutKeyboard.setPadding(300, 300, 300, 300);
        currentRow = -1;
        currentColumn = -1;
        updateHighlightedKey();
    }

    private void updateHighlightedKey() {
        TextView newHighlightedKey = getViewUnderCenter();
        if (newHighlightedKey != currentHighlightedKey)
        {
            if (newHighlightedKey != null)
                newHighlightedKey.setBackgroundResource(
                        currentRow == NUM_ROWS - 1
                                ? R.drawable.background_top_bottom_highlighted
                                : R.drawable.background_top_highlighted
                );
            if (currentHighlightedKey != null)
                currentHighlightedKey.setBackgroundResource(
                        currentRow == NUM_ROWS - 1
                                ? R.drawable.background_top_bottom_normal
                                : R.drawable.background_top_normal
                );
            currentHighlightedKey = newHighlightedKey;
        }
    }

    private TextView getViewUnderCenter()
    {
        int centerScrollY = vScrollView.getScrollY() + vScrollView.getHeight() / 2 - linearLayoutKeyboard.getPaddingLeft(); // all padding should be the same
        int row = centerScrollY < 0 || centerScrollY > NUM_ROWS * KEY_SIZE ? -1 : centerScrollY / KEY_SIZE;
        TextView[] viewArrayRow = null;
        int centerScrollX = (hScrollView.getScrollX() - linearLayoutKeyboard.getPaddingLeft()) + hScrollView.getWidth() / 2;
        double keyLengths = (centerScrollX / (double) KEY_SIZE);
        int column = -1;
        switch(row)
        {
            case 0:
                if (keyLengths > ROW_A_LEFT_OFFSET && keyLengths < ROW_A_LEFT_OFFSET + ROW_A_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_A_LEFT_OFFSET);
                viewArrayRow = textViewsA;
                break;
            case 1:
                if (keyLengths > ROW_B_LEFT_OFFSET && keyLengths < ROW_B_LEFT_OFFSET + ROW_B_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_B_LEFT_OFFSET);
                viewArrayRow = textViewsB;
                break;
            case 2:
                if (keyLengths > ROW_C_LEFT_OFFSET && keyLengths < ROW_C_LEFT_OFFSET + ROW_C_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_C_LEFT_OFFSET);
                viewArrayRow = textViewsC;
                break;
            case 3:
                if (keyLengths > ROW_D_LEFT_OFFSET && keyLengths < ROW_D_LEFT_OFFSET + ROW_D_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_D_LEFT_OFFSET);
                viewArrayRow = textViewsD;
                break;
            case 4:
                if (keyLengths > ROW_E_LEFT_OFFSET && keyLengths < ROW_E_LEFT_OFFSET + ROW_E_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_E_LEFT_OFFSET);
                viewArrayRow = textViewsE;
                break;
        }
        scrollXText.setText("scrollX: " + centerScrollX);
        scrollYText.setText("scrollY: " + centerScrollY);
        keyLengthsText.setText("key lengths: " + keyLengths);
        columnText.setText("column: " + column);
        currentColumn = column;
        currentRow = row;
        if (viewArrayRow != null && (column > -1 && column < viewArrayRow.length))
            return viewArrayRow[column];
        else
            return null;
    }

    private void findViews() {
        textViewsA = new TextView[ROW_A_SIZE];
        textViewsB = new TextView[ROW_B_SIZE];
        textViewsC = new TextView[ROW_C_SIZE];
        textViewsD = new TextView[ROW_D_SIZE];
        textViewsE = new TextView[ROW_E_SIZE];
        hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
        vScrollView = (ScrollView) findViewById(R.id.vScrollView);
        linearLayoutRowA = (LinearLayout) findViewById(R.id.RowA);
        linearLayoutRowB = (LinearLayout) findViewById(R.id.RowB);
        linearLayoutRowC = (LinearLayout) findViewById(R.id.RowC);
        linearLayoutRowD = (LinearLayout) findViewById(R.id.RowD);
        linearLayoutRowE = (LinearLayout) findViewById(R.id.RowE);
        linearLayoutKeyboard = (LinearLayout) findViewById(R.id.keyboard);
        textViewsA[0] = (TextView) findViewById(R.id.A1);
        textViewsA[1] = (TextView) findViewById(R.id.A2);
        textViewsA[2] = (TextView) findViewById(R.id.A3);
        textViewsA[3] = (TextView) findViewById(R.id.A4);
        textViewsB[0] = (TextView) findViewById(R.id.B1);
        textViewsB[1] = (TextView) findViewById(R.id.B2);
        textViewsB[2] = (TextView) findViewById(R.id.B3);
        textViewsB[3] = (TextView) findViewById(R.id.B4);
        textViewsB[4] = (TextView) findViewById(R.id.B5);
        textViewsB[5] = (TextView) findViewById(R.id.B6);
        textViewsC[0] = (TextView) findViewById(R.id.C1);
        textViewsC[1] = (TextView) findViewById(R.id.C2);
        textViewsC[2] = (TextView) findViewById(R.id.C3);
        textViewsC[3] = (TextView) findViewById(R.id.C4);
        textViewsC[4] = (TextView) findViewById(R.id.C5);
        textViewsC[5] = (TextView) findViewById(R.id.C6);
        textViewsC[6] = (TextView) findViewById(R.id.C7);
        textViewsD[0] = (TextView) findViewById(R.id.D1);
        textViewsD[1] = (TextView) findViewById(R.id.D2);
        textViewsD[2] = (TextView) findViewById(R.id.D3);
        textViewsD[3] = (TextView) findViewById(R.id.D4);
        textViewsD[4] = (TextView) findViewById(R.id.D5);
        textViewsD[5] = (TextView) findViewById(R.id.D6);
        textViewsE[0] = (TextView) findViewById(R.id.E1);
        textViewsE[1] = (TextView) findViewById(R.id.E2);
        textViewsE[2] = (TextView) findViewById(R.id.E3);
        textViewsE[3] = (TextView) findViewById(R.id.E4);
        field = (EditText) findViewById(R.id.field);
        enterTextButton = (Button) findViewById(R.id.inputButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        scrollXText = (TextView) findViewById(R.id.scrollX);
        scrollYText = (TextView) findViewById(R.id.scrollY);
        columnText = (TextView) findViewById(R.id.column);
        keyLengthsText = (TextView) findViewById(R.id.keyLengthsText);

    }

    private void setOffsets()
    {
        linearLayoutRowA.setPadding((int) Math.round(ROW_A_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        linearLayoutRowB.setPadding((int) Math.round(ROW_B_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        linearLayoutRowC.setPadding((int) Math.round(ROW_C_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        linearLayoutRowD.setPadding((int) Math.round(ROW_D_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        linearLayoutRowE.setPadding((int) Math.round(ROW_E_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
    }

    private void setTextViewSize(int size)
    {
        for (int i = 0; i < ROW_A_SIZE; i++)
        {
            textViewsA[i].getLayoutParams().width = size;
            textViewsA[i].getLayoutParams().height = size;
        }
        for (int i = 0; i < ROW_B_SIZE; i++)
        {
            textViewsB[i].getLayoutParams().width = size;
            textViewsB[i].getLayoutParams().height = size;
        }
        for (int i = 0; i < ROW_C_SIZE; i++)
        {
            textViewsC[i].getLayoutParams().width = size;
            textViewsC[i].getLayoutParams().height = size;
        }
        for (int i = 0; i < ROW_D_SIZE; i++)
        {
            textViewsD[i].getLayoutParams().width = size;
            textViewsD[i].getLayoutParams().height = size;
        }
        for (int i = 0; i < ROW_E_SIZE; i++)
        {
            textViewsE[i].getLayoutParams().width = size;
            textViewsE[i].getLayoutParams().height = size;
        }
    }

    private void setBackgrounds() {
        for (int i = 0; i < ROW_A_SIZE; i++)
        {
            textViewsA[i].setBackgroundResource(R.drawable.background_top_normal);
        }
        for (int i = 0; i < ROW_B_SIZE; i++)
        {
            textViewsB[i].setBackgroundResource(R.drawable.background_top_normal);
        }
        for (int i = 0; i < ROW_C_SIZE; i++)
        {
            textViewsC[i].setBackgroundResource(R.drawable.background_top_normal);
        }
        for (int i = 0; i < ROW_D_SIZE; i++)
        {
            textViewsD[i].setBackgroundResource(R.drawable.background_top_bottom_normal);
        }
        for (int i = 0; i < ROW_E_SIZE; i++)
        {
            textViewsE[i].setBackgroundResource(R.drawable.background_top_bottom_normal);
        }
    }
}
