package com.example.ben.atomkey;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 5;
    private static final int ROW_A_SIZE = 4;
    private static final int ROW_B_SIZE = 6;
    private static final int ROW_C_SIZE = 7;
    private static final int ROW_D_SIZE = 6;
    private static final int ROW_E_SIZE = 4;

    private static final int KEY_SIZE = 150;

    private static final double ROW_A_LEFT_OFFSET = 1.5; // Expressed as a factor of KEY_SIZE
    private static final double ROW_B_LEFT_OFFSET = 0.5;
    private static final double ROW_C_LEFT_OFFSET = 0;
    private static final double ROW_D_LEFT_OFFSET = 0.5;
    private static final double ROW_E_LEFT_OFFSET = 2.5;

    private TextView[] textViewsA;
    private TextView[] textViewsB;
    private TextView[] textViewsC;
    private TextView[] textViewsD;
    private TextView[] textViewsE;
    private LinearLayout linearLayoutRowA;
    private LinearLayout linearLayoutRowB;
    private LinearLayout linearLayoutRowC;
    private LinearLayout linearLayoutRowD;
    private LinearLayout linearLayoutRowE;
    private LinearLayout linearLayoutKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        linearLayoutKeyboard.setMinimumWidth(ROW_C_SIZE * KEY_SIZE);
        linearLayoutKeyboard.setMinimumHeight(NUM_ROWS * KEY_SIZE);
        setOffsets();
        setTextViewSize(KEY_SIZE);
        setBackgroundColor(Color.BLUE);
        setOffsets();
        linearLayoutKeyboard.requestLayout();
        Log.d("size", "A1 size: " + textViewsA[0].getWidth() + "w, " + textViewsA[0].getHeight() + "h");
        Log.d("size", "something else");
    }

    private void findViews() {
        textViewsA = new TextView[ROW_A_SIZE];
        textViewsB = new TextView[ROW_B_SIZE];
        textViewsC = new TextView[ROW_C_SIZE];
        textViewsD = new TextView[ROW_D_SIZE];
        textViewsE = new TextView[ROW_E_SIZE];
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
            textViewsA[i].setWidth(size);
            textViewsA[i].setHeight(size);
        }
        for (int i = 0; i < ROW_B_SIZE; i++)
        {
            textViewsB[i].setWidth(size);
            textViewsB[i].setHeight(size);
        }
        for (int i = 0; i < ROW_C_SIZE; i++)
        {
            textViewsC[i].setWidth(size);
            textViewsC[i].setHeight(size);
        }
        for (int i = 0; i < ROW_D_SIZE; i++)
        {
            textViewsD[i].setWidth(size);
            textViewsD[i].setHeight(size);
        }
        for (int i = 0; i < ROW_E_SIZE; i++)
        {
            textViewsE[i].setWidth(size);
            textViewsE[i].setHeight(size);
        }
    }

//    private void setBorder() {
//        for (int i = 0; i < ROW_A_SIZE; i++)
//        {
//            textViewsA[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
//        }
//        for (int i = 0; i < ROW_B_SIZE; i++)
//        {
//            textViewsB[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
//        }
//        for (int i = 0; i < ROW_C_SIZE; i++)
//        {
//            textViewsC[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
//        }
//        for (int i = 0; i < ROW_D_SIZE; i++)
//        {
//            textViewsD[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
//        }
//        for (int i = 0; i < ROW_E_SIZE; i++)
//        {
//            textViewsE[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
//        }
//
//
////        for (int i = 0; i < ROW_A_SIZE - 1; i++)
////        {
////            textViewsA[i].setCompoundDrawables(
////                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.border),
////                    null,
////                    null,
////                    null
////            );
////        }
////        textViewsA[ROW_A_SIZE - 1].setCompoundDrawables(
////                ContextCompat.getDrawable(getApplicationContext(), R.drawable.border),
////                null,
////                ContextCompat.getDrawable(getApplicationContext(), R.drawable.border),
////                null
////        );
//    }

    private void setBackgroundColor(int color) {
        for (int i = 0; i < ROW_A_SIZE; i++)
        {
            color = i % 2 == 0 ? Color.RED : Color.BLUE;
            textViewsA[i].setBackgroundColor(color);
        }
        for (int i = 0; i < ROW_B_SIZE; i++)
        {
            color = i % 2 == 0 ? Color.RED : Color.BLUE;
            textViewsB[i].setBackgroundColor(color);
        }
        for (int i = 0; i < ROW_C_SIZE; i++)
        {
            color = i % 2 == 0 ? Color.RED : Color.BLUE;
            textViewsC[i].setBackgroundColor(color);
        }
        for (int i = 0; i < ROW_D_SIZE; i++)
        {
            color = i % 2 == 0 ? Color.RED : Color.BLUE;
            textViewsD[i].setBackgroundColor(color);
        }
        for (int i = 0; i < ROW_E_SIZE; i++)
        {
            color = i % 2 == 0 ? Color.RED : Color.BLUE;
            textViewsE[i].setBackgroundColor(color);
        }
    }
}
