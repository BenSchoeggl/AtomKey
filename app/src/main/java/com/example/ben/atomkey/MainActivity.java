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
    private static final int SYM_NUM_ROWS = 4;
    private static final int SYM_ROW_A_SIZE = 3;
    private static final int SYM_ROW_B_SIZE = 3;
    private static final int SYM_ROW_C_SIZE = 3;
    private static final int SYM_ROW_D_SIZE = 3;

    private static final int KEY_SIZE = 150;

    private static final double ROW_A_LEFT_OFFSET = 1.0; // Expressed as a factor of KEY_SIZE
    private static final double ROW_B_LEFT_OFFSET = 0.5;
    private static final double ROW_C_LEFT_OFFSET = 0;
    private static final double ROW_D_LEFT_OFFSET = 0.5;
    private static final double ROW_E_LEFT_OFFSET = 2.0;

    private TextView[][] textViews;
    private TextView[][] symTextViews;
    private ScrollView vScrollView;
    private HorizontalScrollView hScrollView;
    private LinearLayout letterKeyboard;
    private LinearLayout symbolKeyboard;

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
                updateHighlightedKey();
                return true;
            }
        };
        vScrollView.setOnTouchListener(touchListener);
        hScrollView.setOnTouchListener(touchListener);
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
        symbolKeyboard.setPadding(300, 300, 300, 300);
        currentRow = -1;
        currentColumn = -1;
        setCase(false);
        showLetterKeyboard(true);
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
                hScrollView.scrollTo(0, 0);
                vScrollView.scrollTo(0, 0);
                showLetterKeyboard(false);
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
        if (isLetterKeyboardVisible()) {
            int centerScrollY = vScrollView.getScrollY() + vScrollView.getHeight() / 2 - letterKeyboard.getPaddingLeft(); // all padding should be the same
            int row = centerScrollY < 0 || centerScrollY > NUM_ROWS * KEY_SIZE ? -1 : centerScrollY / KEY_SIZE;
            int centerScrollX = (hScrollView.getScrollX() - letterKeyboard.getPaddingLeft()) + hScrollView.getWidth() / 2;
            double keyLengths = (centerScrollX / (double) KEY_SIZE);
            int column = -1;
            switch (row) {
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
        } else {
            int centerScrollY = vScrollView.getScrollY() + vScrollView.getHeight() / 2 - symbolKeyboard.getPaddingTop();
            int row = centerScrollY < 0 || centerScrollY > SYM_NUM_ROWS * KEY_SIZE ? -1 : centerScrollY / KEY_SIZE;
            int centerScrollX = hScrollView.getScrollX() + hScrollView.getWidth() / 2 - symbolKeyboard.getPaddingLeft();
            int column = centerScrollX < 0 || centerScrollX > SYM_NUM_ROWS * KEY_SIZE ? -1 : centerScrollY / KEY_SIZE;
            currentColumn = column;
            currentRow = row;
            if (row > -1 && row < SYM_NUM_ROWS && column > -1 && column < symTextViews[row].length) {
                TextView [] temp = symTextViews[row];
                return temp[column];
            }
            else
                return null;
        }
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
        letterKeyboard = (LinearLayout) findViewById(R.id.letterKeyboard);
        symbolKeyboard = (LinearLayout) findViewById(R.id.symbolKeyboard);
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
        symTextViews = new TextView[SYM_NUM_ROWS][];
        symTextViews[0] = new TextView[SYM_ROW_A_SIZE];
        symTextViews[1] = new TextView[SYM_ROW_B_SIZE];
        symTextViews[2] = new TextView[SYM_ROW_C_SIZE];
        symTextViews[3] = new TextView[SYM_ROW_D_SIZE];
        symTextViews[0][0] = (TextView) findViewById(R.id.A1_SYM);
        symTextViews[0][1] = (TextView) findViewById(R.id.A2_SYM);
        symTextViews[0][2] = (TextView) findViewById(R.id.A3_SYM);
        symTextViews[1][0] = (TextView) findViewById(R.id.B1_SYM);
        symTextViews[1][1] = (TextView) findViewById(R.id.B2_SYM);
        symTextViews[1][2] = (TextView) findViewById(R.id.B3_SYM);
        symTextViews[2][0] = (TextView) findViewById(R.id.C1_SYM);
        symTextViews[2][1] = (TextView) findViewById(R.id.C2_SYM);
        symTextViews[2][2] = (TextView) findViewById(R.id.C3_SYM);
        symTextViews[3][0] = (TextView) findViewById(R.id.D1_SYM);
        symTextViews[3][1] = (TextView) findViewById(R.id.D2_SYM);
        symTextViews[3][2] = (TextView) findViewById(R.id.D3_SYM);
        field = (EditText) findViewById(R.id.field);
        enterTextButton = (Button) findViewById(R.id.inputButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

    }

    private void setOffsets()
    {
        findViewById(R.id.RowA).setPadding((int) Math.round(ROW_A_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        findViewById(R.id.RowB).setPadding((int) Math.round(ROW_B_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        findViewById(R.id.RowC).setPadding((int) Math.round(ROW_C_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        findViewById(R.id.RowD).setPadding((int) Math.round(ROW_D_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
        findViewById(R.id.RowE).setPadding((int) Math.round(ROW_E_LEFT_OFFSET * KEY_SIZE), 0, 0, 0);
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
                textViews[i][j].setBackgroundResource(
                        i != NUM_ROWS - 1
                                ? R.drawable.background_top_normal
                                : R.drawable.background_top_bottom_normal
                );
            }
        }
        for (int i = 0; i < SYM_NUM_ROWS; i++) {
            for (int j = 0; j < symTextViews[i].length; j++) {
                symTextViews[i][j].setBackgroundResource(
                        i != SYM_NUM_ROWS - 1
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
                if (tag.length() == 1 && !tag.equals(" ")) {
                    if (upper)
                        textViews[i][j].setText(tag.toUpperCase());
                    else
                        textViews[i][j].setText(tag.toLowerCase());
                }
            }
        }
        isUpperCase = upper;
    }

    private boolean isLetterKeyboardVisible()
    {
        return letterKeyboard.getVisibility() == View.VISIBLE;
    }

    private void showLetterKeyboard(boolean letter) {
        if (letter) {
            letterKeyboard.setVisibility(View.VISIBLE);
            symbolKeyboard.setVisibility(View.GONE);
        }
        else
        {
            letterKeyboard.setVisibility(View.GONE);
            symbolKeyboard.setVisibility(View.VISIBLE);
        }
    }
}
