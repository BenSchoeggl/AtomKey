package com.example.ben.atomkey;

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
    private static final int ROW_D_SIZE = 7;
    private static final int ROW_E_SIZE = 5;

    private static final int KEY_SIZE = 200;

    private static final double ROW_A_LEFT_OFFSET = 1.0; // Expressed as a factor of KEY_SIZE
    private static final double ROW_B_LEFT_OFFSET = 0.5;
    private static final double ROW_C_LEFT_OFFSET = 0;
    private static final double ROW_D_LEFT_OFFSET = 0.5;
    private static final double ROW_E_LEFT_OFFSET = 2.0;

    private TextView[][] textViews;
    private ScrollView vScrollView;
    private HorizontalScrollView hScrollView;
    private LinearLayout linearLayoutRowA;
    private LinearLayout linearLayoutRowB;
    private LinearLayout linearLayoutRowC;
    private LinearLayout linearLayoutRowD;
    private LinearLayout linearLayoutRowE;
    private LinearLayout letterKeyboard;

    private TextView currentHighlightedKey;
    private int currentRow;
    private int currentColumn;
    private EditText field;
    private boolean isUpperCase;

    private Button enterTextButton;
    private Button deleteButton;

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
                processEnterEvent();
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
        letterKeyboard.setPadding(300, 300, 300, 300);
        currentRow = -1;
        currentColumn = -1;
        setCase(false);
        updateHighlightedKey();
    }

    private void processEnterEvent() {
        if (currentHighlightedKey != null) {
            String tag = currentHighlightedKey.getTag().toString();
            if (tag.length() == 1) {
                field.setText(field.getText().toString() +
                        (isUpperCase ? tag.toUpperCase() : tag.toLowerCase()));
                if (isUpperCase)
                    setCase(false);
            }
            else if (tag.equalsIgnoreCase("shift"))
            {
                setCase(!isUpperCase);
            }
            else if (tag.equalsIgnoreCase("sym"))
            {
                // do symbols stuff
            }
        }
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
        int centerScrollY = vScrollView.getScrollY() + vScrollView.getHeight() / 2 - letterKeyboard.getPaddingLeft(); // all padding should be the same
        int row = centerScrollY < 0 || centerScrollY > NUM_ROWS * KEY_SIZE ? -1 : centerScrollY / KEY_SIZE;
        int centerScrollX = (hScrollView.getScrollX() - letterKeyboard.getPaddingLeft()) + hScrollView.getWidth() / 2;
        double keyLengths = (centerScrollX / (double) KEY_SIZE);
        int column = -1;
        switch(row)
        {
            case 0:
                if (keyLengths > ROW_A_LEFT_OFFSET && keyLengths < ROW_A_LEFT_OFFSET + ROW_A_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_A_LEFT_OFFSET);
                break;
            case 1:
                if (keyLengths > ROW_B_LEFT_OFFSET && keyLengths < ROW_B_LEFT_OFFSET + ROW_B_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_B_LEFT_OFFSET);
                break;
            case 2:
                if (keyLengths > ROW_C_LEFT_OFFSET && keyLengths < ROW_C_LEFT_OFFSET + ROW_C_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_C_LEFT_OFFSET);
                break;
            case 3:
                if (keyLengths > ROW_D_LEFT_OFFSET && keyLengths < ROW_D_LEFT_OFFSET + ROW_D_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_D_LEFT_OFFSET);
                break;
            case 4:
                if (keyLengths > ROW_E_LEFT_OFFSET && keyLengths < ROW_E_LEFT_OFFSET + ROW_E_SIZE)
                    column = (int) Math.floor(keyLengths - ROW_E_LEFT_OFFSET);
                break;
        }

        currentColumn = column;
        currentRow = row;
        if (row > -1 && row < NUM_ROWS && column > -1 && column < textViews[row].length)
            return textViews[row][column];
        else
            return null;
    }

    private void findViews() {
        textViews = new TextView[NUM_ROWS][];
        textViews[0] = new TextView[ROW_A_SIZE];
        textViews[1] = new TextView[ROW_B_SIZE];
        textViews[2] = new TextView[ROW_C_SIZE];
        textViews[3] = new TextView[ROW_D_SIZE];
        textViews[4] = new TextView[ROW_E_SIZE];
        hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
        vScrollView = (ScrollView) findViewById(R.id.vScrollView);
        linearLayoutRowA = (LinearLayout) findViewById(R.id.RowA);
        linearLayoutRowB = (LinearLayout) findViewById(R.id.RowB);
        linearLayoutRowC = (LinearLayout) findViewById(R.id.RowC);
        linearLayoutRowD = (LinearLayout) findViewById(R.id.RowD);
        linearLayoutRowE = (LinearLayout) findViewById(R.id.RowE);
        letterKeyboard = (LinearLayout) findViewById(R.id.keyboard);
        textViews[0][0] = (TextView) findViewById(R.id.A1);
        textViews[0][1] = (TextView) findViewById(R.id.A2);
        textViews[0][2] = (TextView) findViewById(R.id.A3);
        textViews[0][3] = (TextView) findViewById(R.id.A4);
        textViews[1][0] = (TextView) findViewById(R.id.B1);
        textViews[1][1] = (TextView) findViewById(R.id.B2);
        textViews[1][2] = (TextView) findViewById(R.id.B3);
        textViews[1][3] = (TextView) findViewById(R.id.B4);
        textViews[1][4] = (TextView) findViewById(R.id.B5);
        textViews[1][5] = (TextView) findViewById(R.id.B6);
        textViews[2][0] = (TextView) findViewById(R.id.C1);
        textViews[2][1] = (TextView) findViewById(R.id.C2);
        textViews[2][2] = (TextView) findViewById(R.id.C3);
        textViews[2][3] = (TextView) findViewById(R.id.C4);
        textViews[2][4] = (TextView) findViewById(R.id.C5);
        textViews[2][5] = (TextView) findViewById(R.id.C6);
        textViews[2][6] = (TextView) findViewById(R.id.C7);
        textViews[3][0] = (TextView) findViewById(R.id.D1);
        textViews[3][1] = (TextView) findViewById(R.id.D2);
        textViews[3][2] = (TextView) findViewById(R.id.D3);
        textViews[3][3] = (TextView) findViewById(R.id.D4);
        textViews[3][4] = (TextView) findViewById(R.id.D5);
        textViews[3][5] = (TextView) findViewById(R.id.D6);
        textViews[3][6] = (TextView) findViewById(R.id.D7);
        textViews[4][0] = (TextView) findViewById(R.id.E1);
        textViews[4][1] = (TextView) findViewById(R.id.E2);
        textViews[4][2] = (TextView) findViewById(R.id.E3);
        textViews[4][3] = (TextView) findViewById(R.id.E4);
        textViews[4][4] = (TextView) findViewById(R.id.E5);
        field = (EditText) findViewById(R.id.field);
        enterTextButton = (Button) findViewById(R.id.inputButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

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
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < textViews[i].length; j++)
            {
                textViews[i][j].getLayoutParams().width = size;
                textViews[i][j].getLayoutParams().height = size;
            }
        }
    }

    private void setBackgrounds() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < textViews[i].length; j++) {
                int id = i == NUM_ROWS - 1 ? R.drawable.background_top_normal : R.drawable.background_top_bottom_normal;
                textViews[i][j].setBackgroundResource(
                        i == NUM_ROWS - 1
                                ? R.drawable.background_top_normal
                                : R.drawable.background_top_bottom_normal
                );
            }
        }
    }

    private void setCase(boolean upper)
    {
        for (int i = 0; i < textViews.length; i++) {
            for (int j = 0; j < textViews[i].length; j++) {
                String tag = textViews[i][j].getTag().toString();
                if (tag.length() == 1) {
                    if (upper)
                        textViews[i][j].setText(tag.toUpperCase());
                    else
                        textViews[i][j].setText(tag.toLowerCase());
                }
            }
        }
        isUpperCase = upper;
    }
}
