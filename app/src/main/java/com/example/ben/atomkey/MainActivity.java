package com.example.ben.atomkey;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.DropBoxManager;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlSerializer;
import android.util.Xml;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String[] PHRASE_SET = {
            "my watch fell in the water",
            "the prevailing wind",
            "never too rich",
            "breathing is difficult",
            "I can see the rings",
            "physics and chemistry",
            "my bank account is full",
            "elections brought trouble",
            "we are having spaghetti",
            "time to go shopping",
            "a problem with the engine",
            "elephants are large",
            "my favorite place to visit",
            "three two one zero",
            "my favorite subject",
            "circumstances are poor",
            "watch out for low objects",
            "if at first you fail",
            "please provide your date",
            "we run the risk of failure",
            "prayer in schools offends",
            "he is just like everyone",
            "a great disturbance",
            "love means many things",
            "you must be getting old",
            "the world is a stage",
            "can I skate with sister",
            "neither a borrower",
            "one heck of a question",
            "a question to answer",
            "beware the ides of March",
            "double trouble",
            "the power of denial",
            "I agree with you",
            "do not say anything",
            "play it again Sam",
            "the force is with you",
            "you are not a Jedi yet",
            "an offer you cannot refuse",
            "are you talking to me",
            "yes you are very smart",
            "all work and no play",
            "hair gel is very greasy",
            "Valium in bulk size",
            "the facts get in the way",
            "the dreamers of dreams",
            "did you have a good time",
            "space is a high priority",
            "you are wonderful",
            "do not squander your time",
            "do not drink too much",
            "take a coffee break",
            "popularity is desirable",
            "my music is better than his",
            "starlight and dewdrop",
            "the living is easy",
            "fish are jumping",
            "the cotton is high",
            "drove my chevy",
            "the levee was dry",
            "I took the rover",
            "movie about a professor",
            "come and see our new car",
            "coming up with sound bites",
            "I am going to a lesson",
            "the opposing team",
            "soon we will return",
            "I am wearing a tie",
            "the quick brown fox jumped",
            "all together in one pile",
            "wear a jeweled crown",
            "there will be some fog",
            "I am allergic to bees",
            "he is still on our team",
            "the dow jones index",
            "my preferred treat",
            "the king sends you away",
            "we are subjects who obey",
            "mom made her a turtleneck",
            "goldilocks and the bears",
            "we went grocery shopping",
            "the assignment is due",
            "what you want is there",
            "for your eyes only",
            "a quarter of a century",
            "the store will close",
            "head shoulders knees",
            "vanilla ice cream",
            "frequently asked questions",
            "round robin scheduling",
            "information super highway",
            "my favorite web browser",
            "the laser printer jams",
            "all good boys get fudge",
            "the second largest country",
            "call for more details",
            "just in time to party",
            "have a good weekend",
            "video camera zoom lens",
            "monkey see monkey do",
            "that is very unfortunate",
            "the back yard of our house",
            "this is a very good idea",
            "reading week is here",
            "our fax number has changed",
            "thank you for your help",
            "no exchange without a bill",
            "the early bird",
            "buckle up for safety",
            "this is too much",
            "protect your environment",
            "world population grows",
            "the library is closed",
            "Mary had a little lamb",
            "teaching services help",
            "we accept personal checks",
            "this is a non profit",
            "user friendly interface",
            "healthy food is good",
            "hands on experience",
            "this watch is expensive",
            "the postal service is slow",
            "communicate through email",
            "the capitol of our nation",
            "travel at light speed",
            "I do not agree",
            "gas bills are monthly",
            "earthquakes are dangerous",
            "life is but a dream",
            "take it to recycling",
            "sent this by mail",
            "fall is my favorite season",
            "a fox is a smart animal",
            "the kids are very excited",
            "parking lot is full",
            "my bike has a flat tire",
            "do not walk too quickly",
            "a duck quacks for food",
            "limited warranty",
            "the four seasons arrive",
            "the sun rises in the east",
            "it is very windy today",
            "do not worry about this",
            "dashing through the snow",
            "want to join us for lunch",
            "stay away from strangers",
            "accompanied by an adult",
            "see you later alligator",
            "make my day you sucker",
            "I can play better now",
            "she wears too much makeup",
            "my bare face in the wind",
            "batman wears a cape",
            "I hate baking pies",
            "Lydia wants to go home",
            "win first prize",
            "Freud wrote of the ego",
            "I do not care",
            "always cover the bases",
            "nobody cares anymore",
            "can we play cards tonight",
            "get rid of that",
            "I watched blazing saddles",
            "the sum of the parts",
            "they love to yap",
            "peek out the window",
            "be home before midnight",
            "he played the movie pimp",
            "I skimmed your proposal",
            "he was cold in a shirt",
            "no more war no more blood",
            "toss the ball around",
            "I will meet you at noon",
            "I want to hold your hand",
            "the children are playing",
            "superman never died",
            "I listened to the tape",
            "he is shouting loudly",
            "correct your diction",
            "most golfers love the game",
            "he cooled off but she left",
            "my dog sheds his hair",
            "join us on the patio",
            "these cookies are amazing",
            "I can still smell you",
            "the dog will bite you",
            "a most ridiculous thing",
            "where did you get that",
            "what a lovely red jacket",
            "do you like to shop",
            "I spilled coffee",
            "the largest of the oceans",
            "shall we play cards",
            "some athletes use drugs",
            "my mother bakes cookies",
            "do a good deed",
            "there is someone coming",
            "flashing red light",
            "sprawling subdivisions",
            "where did my glasses go",
            "on the way to the cottage",
            "a lot of chlorine",
            "do not drink the water",
            "my car always breaks",
            "Santa Claus got stuck",
            "public transit is fast",
            "zero in on the facts",
            "make up a few phrases",
            "my fingers are very cold",
            "rain rain go away",
            "bad for the environment",
            "universities are mighty",
            "the price of gas is high",
            "the winner of the race",
            "we drive on parkways",
            "we park in driveways",
            "go out for some pizza",
            "effort is everything",
            "where is my little dog",
            "if you were not so stupid",
            "not so fast buddy",
            "do you like to go camping",
            "this person is a disaster",
            "the national imagination",
            "universally understood",
            "listen to hours of opera",
            "an occasional chocolate",
            "victims deserve redress",
            "protesters block traffic",
            "the acceptance speech",
            "hike hard to the summit",
            "a little encouragement",
            "penalty for being late",
            "the pen is mightier",
            "exceed the maximum speed",
            "in sharp contrast",
            "this leather jacket",
            "consequences of the wrong",
            "this mission statement",
            "you will lose your voice",
            "every apple from my tree",
            "are you sure",
            "the fourth edition",
            "this system of taxation",
            "beautiful paintings",
            "almost as long as a yard",
            "we missed your birthday",
            "coalition governments",
            "destruction of the forest",
            "I like to play tennis",
            "acutely aware of her looks",
            "you want to eat your cake",
            "machinery is complicated",
            "a glance in her direction",
            "I just cannot decide",
            "please follow the rules",
            "an airport is very busy",
            "mystery of Loch Ness",
            "is there any indication",
            "the chamber decides",
            "this phenomenon will occur",
            "obligations must be met",
            "valid until the year ends",
            "file all complaints",
            "tickets are expensive",
            "a picture is unlike words",
            "this camera is nice",
            "it looks like a shack",
            "the dog buried the bone",
            "the daring young man",
            "this equation is tricky",
            "express delivery",
            "I will put on my glasses",
            "a touchdown at the end",
            "the treasury department",
            "I question that response",
            "well connected with people",
            "the bathroom is clean",
            "the generation gap widens",
            "chemical spills are bad",
            "prepare for the exam",
            "interesting observation",
            "registered transaction",
            "your etiquette needs work",
            "we better investigate",
            "stability of the nation",
            "home electronics system",
            "our silver anniversary",
            "the presidential suite",
            "the punishment should fit",
            "sharp cheese stays sharp",
            "the registration period",
            "you have my sympathy",
            "the objective of exercise",
            "historic meetings are few",
            "very reluctant to enter",
            "good at science and math",
            "six daughters and a son",
            "a disgusting thing to say",
            "sign the withdrawal slip",
            "the relations are strained",
            "the minimum amount of time",
            "a very traditional dress",
            "the national aspirations",
            "medieval times were hard",
            "the security force",
            "the winners and the losers",
            "the voters turfed him out",
            "pay off a mortgage",
            "the collapse of the Empire",
            "did you see that explosion",
            "keep receipts for expenses",
            "the assault took months",
            "order your priorities",
            "traveling requires fuel",
            "longer than a soccer field",
            "a good joke gets a laugh",
            "the union will strike",
            "never ignore religion",
            "interactions between men",
            "where did you get the idea",
            "it should be sunny",
            "a psychiatrist will help",
            "you should visit a doctor",
            "you must appoint me",
            "the fax machine is broken",
            "players know the rules",
            "a dog is a friend",
            "would you like to come",
            "February has an extra day",
            "do not feel too bad",
            "this library has books",
            "construction is difficult",
            "he called seven times",
            "that is an odd question",
            "a feeling of exasperation",
            "we must double our effort",
            "no kissing in the library",
            "the agreement has problems",
            "vote with your conscience",
            "my favorite sport",
            "sad to hear that news",
            "the gun discharged",
            "one of the poorest nations",
            "the algorithm is ugly",
            "the presentation was awful",
            "the land is owned",
            "burglars never prosper",
            "the fire blazed all day",
            "if diplomacy does not work",
            "please keep this quiet",
            "the rationale behind it",
            "the cat has a temper",
            "our housekeeper works hard",
            "her majesty visited here",
            "handicapped persons",
            "these barracks are big",
            "sing the gospel and blues",
            "he underwent surgery",
            "learn the ropes",
            "peering through a hole",
            "rapidly running late",
            "that is difficult",
            "give me one spoonful",
            "two or three cups",
            "just like it says",
            "companies reveal a merger",
            "electric cars need fuel",
            "the plug does not fit",
            "drugs should be avoided",
            "the most beautiful sunset",
            "we dine out on weekends",
            "get aboard the ship",
            "the water was monitored",
            "he watched in astonishment",
            "a big scratch",
            "salesmen make their quota",
            "saving the child was heroic",
            "granite is the hardest",
            "bring the offenders",
            "every Saturday they come",
            "careless driving is fined",
            "microscopes look closely",
            "a coupon for a free sample",
            "fine in moderation",
            "a subject to really enjoy",
            "important for politics",
            "the sticker is validated",
            "the fire raged for a month",
            "you never take precautions",
            "we have enough witnesses",
            "labor unions organize",
            "people blow their horn",
            "publish the correction",
            "I like baroque music",
            "the proprietor was busy",
            "be discreet in the meeting",
            "meet tomorrow in the tram",
            "suburbs spraw everywhere",
            "shivering keeps us warm",
            "the dolphins leap",
            "try to enjoy your leave",
            "the ventilation system",
            "the dinosaurs are extinct",
            "an inefficient way to heat",
            "the bus was very crowded",
            "an injustice is committed",
            "the coronation was fun",
            "look in the syllabus",
            "the rectangular objects",
            "the prescription drugs",
            "the insulation is working",
            "discover the treasure",
            "increase in our lifetime",
            "the cream rises to the top",
            "the high waves swamp us",
            "the treasurer keeps books",
            "completely sold out",
            "the location of the crime",
            "the chancellor was boring",
            "the accident scene",
            "a benign tumor is safe",
            "please take a bath",
            "pay rent on the first",
            "for murder you get life",
            "a much higher risk",
            "quit while you are ahead",
            "knee bone and thigh bone",
            "safe to walk the streets",
            "the wallet was found",
            "one hour for questions",
            "you think you need a raise",
            "they watched the movie",
            "good jobs for in education",
            "jumping out of the water",
            "the trains are always late",
            "sit at the front",
            "do you prefer a window",
            "the food at this restaurant",
            "Canada has ten provinces",
            "the elevator door is stuck",
            "raindrops fall on my head",
            "spill coffee on the carpet",
            "an excellent way",
            "with each step forward",
            "faster than a bullet",
            "wishful thinking is fine",
            "nothing wrong with style",
            "arguing with the boss",
            "taking the train is faster",
            "what goes up",
            "be persistent to win",
            "presidents drive nice cars",
            "the stock exchange dipped",
            "do not be so silly",
            "that is a very nasty cut",
            "the oil runs dry",
            "learn to walk then run",
            "insurance is important",
            "traveling to conferences",
            "do you get nervous",
            "the roads are slippery",
            "tickets can be challenged",
            "apartments are expensive",
            "find a nearby parking spot",
            "handle guns with care",
            "the doctor ordered it",
            "a rattle snake has poison",
            "willow trees are in water",
            "I cannot believe I ate it",
            "the biggest hamburger I saw",
            "gamblers lose their shirts",
            "exercise is good",
            "irregular verbs are hard",
            "they might find you rude",
            "a lie makes the nose grow",
            "a large nose means you lie",
            "lie detector tests",
            "do not lie in court or else",
            "most judges are very honest",
            "only an idiot lies in court",
            "the important news",
            "please try to be home",
            "the doors are locked",
            "dormitory doors are locked",
            "staying through the night",
            "you are a capitalist pig",
            "motivational seminars",
            "questioning the wisdom",
            "the rejection letters",
            "the first time he tried",
            "the referendum was passed",
            "a steep learning curve",
            "good stimulus and response",
            "everybody loses in battle",
            "put the garbage in a mine",
            "the employee recruitment",
            "experience is hard to find",
            "everyone wants to win",
            "the picket line"
    };

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

    private static final int KEY_SIZE = 75;

    private static final double ROW_A_LEFT_OFFSET = 1.0; // Expressed as a factor of KEY_SIZE
    private static final double ROW_B_LEFT_OFFSET = 0.5;
    private static final double ROW_C_LEFT_OFFSET = 0;
    private static final double ROW_D_LEFT_OFFSET = 0.5;
    private static final double ROW_E_LEFT_OFFSET = 2.0;

    private static final int SCROLL_VIEW_PADDING = 200;

    private TextView[][] textViews;
    private TextView[][] symTextViews;
    private ScrollView vScrollView;
    private HorizontalScrollView hScrollView;
    private LinearLayout letterKeyboard;
    private LinearLayout symbolKeyboard;
    private TextView currentHighlightedKey;
    private TextView phraseTextView;
    private Button testButton;
    private Button nextButton;
    private NumberPicker testNumberPicker;

    private int currentRow;
    private int currentColumn;
    private EditText field;
    private boolean isUpperCase;

    private Button enterTextButton;
    private Button deleteButton;

    private XmlSerializer serializer;
    private StringWriter xmlWriter;
    private List<String> trialStrings;
    private List<EntryData> trialDataList;
    private int trialIndex;

    private TextView shifftKey;

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
        hScrollView.setOnTouchListener(touchListener);
        vScrollView.setOnTouchListener(touchListener);
        vScrollView.setHorizontalScrollBarEnabled(false);
        vScrollView.setVerticalScrollBarEnabled(false);
        hScrollView.setHorizontalScrollBarEnabled(false);
        hScrollView.setVerticalScrollBarEnabled(false);
        enterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processEnterEvent();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = field.getText().toString();
                if (!text.isEmpty())
                    field.setText(text.substring(0, field.getText().length() - 1));
                if (isTestRunning()) {
                    addTestEntry((char) 8); // backspace value
                }
            }
        });
//        field.setClickable(false);
//        field.setFocusable(false);
//        enterTextButton.requestFocus();
        testNumberPicker.setMinValue(1);
        testNumberPicker.setMaxValue(PHRASE_SET.length);
        testNumberPicker.setWrapSelectorWheel(false);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTestRunning())
                    startTest(testNumberPicker.getValue());
                else
                    endTest(false);
            }
        });
        serializer = Xml.newSerializer();
        trialIndex = -1;
        letterKeyboard.requestFocus();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTrial();
                if (trialIndex < trialStrings.size())
                    startTrial();
                else
                    endTest(true);
            }
        });

        setOffsets();
        setTextViewSize(KEY_SIZE);
        setBackgrounds();
        letterKeyboard.setPadding(SCROLL_VIEW_PADDING, SCROLL_VIEW_PADDING, SCROLL_VIEW_PADDING, SCROLL_VIEW_PADDING);
        symbolKeyboard.setPadding(SCROLL_VIEW_PADDING, SCROLL_VIEW_PADDING, SCROLL_VIEW_PADDING, SCROLL_VIEW_PADDING);
        currentRow = -1;
        currentColumn = -1;
        setCase(false);
        showLetterKeyboard(true);
        updateHighlightedKey();
        updateTestViews();
        field.setClickable(false);
        field.setFocusable(false);
        testNumberPicker.setFocusable(false);
        testNumberPicker.setClickable(false);
        testNumberPicker.clearFocus();
        letterKeyboard.setFocusable(true);
        letterKeyboard.setFocusableInTouchMode(true);
        letterKeyboard.requestFocus();
    }

    private void updateTestButton() {

    }

    private void startTest(int trials) {
        field.setText("");
        trialStrings = new ArrayList<String>();
        Random r = new Random();
        Set<Integer> usedIndexes = new HashSet<Integer>();
        for (int i = 0; i < trials; i++) {
            int idx = r.nextInt(PHRASE_SET.length);
            while (usedIndexes.contains(idx)) {
                idx = r.nextInt(PHRASE_SET.length);
            }
            usedIndexes.add(idx);
            trialStrings.add(PHRASE_SET[idx]);

            // for test
//            trialStrings.add("abc");
        }
        trialIndex = 0;
        updateTestViews();

        xmlWriter = new StringWriter();
        try {
            serializer.setOutput(xmlWriter);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "TextTest");
            serializer.attribute("", "version", "2.7.2");
            serializer.attribute("", "trials", "" + trials);
            serializer.attribute("", "ticks", "" + System.nanoTime());
            serializer.attribute("", "seconds", "" + System.currentTimeMillis() / 1000.0);
            Date current = Calendar.getInstance().getTime();
            serializer.attribute("", "date", new SimpleDateFormat("EEEE, MMMM d, yyyy HH:mm:ss aaa", Locale.ENGLISH).format(current.getTime()));
        } catch (IOException e)
        {
            Log.d("xml parser", e.toString());
        }
        startTrial();

    }

    private void endTest(boolean success) {
        trialIndex = -1;
        updateTestViews();
        field.setText("");
        if (success) {
            try {
                serializer.endTag("", "TextTest");
                serializer.endDocument();
                testButton.setEnabled(true);
                copyXml();
            } catch (IOException e) {
                Log.d("xml parser", e.toString());
            }
        }
    }

    private void copyXml() {
        final String xml = xmlWriter.toString().replace("&amp;#x8;", "&#x8;");
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE))
                .setPrimaryClip(ClipData.newPlainText("xml", xmlWriter.toString()));
        Log.d("xml", xml);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener yesListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent email = new Intent(Intent.ACTION_SEND);
                getIntent().setType("text");
                email.putExtra(Intent.EXTRA_EMAIL, "brendan.d.powers@gmail.com");
                email.putExtra(Intent.EXTRA_SUBJECT, "AtomKey test result");
                email.putExtra(Intent.EXTRA_TEXT, xml);
                dialog.dismiss();
                startActivity(Intent.createChooser(email, "Send Email"));
            }
        };
        DialogInterface.OnClickListener noListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        builder.setMessage("The test has finished and the XML has been copied to the clipboard. Do you also want to send it in an email?")
               .setPositiveButton("Yes", yesListener)
               .setNegativeButton("No", noListener);
        builder.show();
    }

    private void recordTrialXML() {
        try {
            serializer.startTag("", "Trial");
            serializer.attribute("", "number", "" + (trialIndex + 1));
            serializer.attribute("", "entries", "" + trialDataList.size());
            serializer.startTag("", "Presented");
            serializer.text(trialStrings.get(trialIndex));
            serializer.endTag("", "Presented");
            for (int i = 0; i < trialDataList.size(); i++) {
                EntryData data = trialDataList.get(i);
                serializer.startTag("", "Entry");
                serializer.attribute("", "char", data.character == (char) 8 ? "&#x8;" : "" + data.character);
                serializer.attribute("", "value", "" + ((int) data.character));
                serializer.attribute("", "ticks", "" + data.ticks);
                serializer.attribute("", "seconds", "" + data.ticks / (double) 1e+9);
                serializer.endTag("", "Entry");
            }
            serializer.startTag("", "Transcribed");
            serializer.text(field.getText().toString());
            serializer.endTag("", "Transcribed");
            serializer.endTag("", "Trial");
        } catch (IOException e) {
            Log.d("xml parser", e.toString());
        }
    }

    private void startTrial() {
        updateTestViews();
        trialDataList = new ArrayList<EntryData>();
//        try {
//            updateTestViews();
//            trialDataList = new ArrayList<EntryData>();
//            serializer.startTag("", "Trial");
//            serializer.attribute("", "number", "" + trialIndex + 1);
//            serializer.attribute("", "entries", "" + trialStrings.get(trialIndex).length());
//            serializer.startTag("", "Presented");
//            serializer.text(trialStrings.get(trialIndex));
//            serializer.endTag("", "Presented");
//        } catch (IOException e) {
//            Log.d("xml parser", e.toString());
//        }
    }

    private void endTrial() {
        recordTrialXML();
        updateTestViews();
        field.setText("");
        trialIndex++;
    }

    private void updateTestViews() {
        if (trialIndex > -1 && trialStrings != null && trialIndex < trialStrings.size()) {
            findViewById(R.id.testTextLayout).setVisibility(View.VISIBLE);
            phraseTextView.setText(trialStrings.get(trialIndex));
            nextButton.setVisibility(View.VISIBLE);
            testButton.setText("END TEST");
            testNumberPicker.setEnabled(false);
        } else {
            findViewById(R.id.testTextLayout).setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
            testButton.setText("START TEST");
            testNumberPicker.setEnabled(true);
        }
    }

    private void addTestEntry(char character) {
        EntryData data = new EntryData();
        data.character = character;
        data.ticks = System.nanoTime();
        trialDataList.add(data);
//        try {
//            serializer.startTag("", "Entry");
//            serializer.attribute("", "char", "" + character);
//            serializer.attribute("", "value", "" + ((int) character));
//            serializer.attribute("", "ticks", "" + System.nanoTime());
//            serializer.attribute("", "seconds", "" + System.currentTimeMillis() / 1000.0);
//            serializer.endTag("", "Entry");
//        } catch (IOException e) {
//            Log.d("xml parser", e.toString());
//        }
    }

    private void processEnterEvent() {
        if (currentHighlightedKey != null) {
            String tag = currentHighlightedKey.getTag().toString();
            if (tag.length() == 1) {
                field.setText(field.getText().toString() +
                        (isUpperCase ? tag.toUpperCase() : tag.toLowerCase()));
                if (isUpperCase)
                    setCase(false);
                if (isTestRunning()) {
                    addTestEntry(tag.charAt(0));
                }
            }
            else if (tag.equalsIgnoreCase("shift"))
            {
                setCase(!isUpperCase);
            }
            else if (tag.equalsIgnoreCase("sym"))
            {
//                hScrollView.scrollTo(0, 0);
//                vScrollView.scrollTo(0, 0);
//                showLetterKeyboard(false);
            }
        }
    }

    private boolean isTestRunning() {
        return trialIndex != -1;
    }

    private void updateHighlightedKey() {
        TextView newHighlightedKey = getViewUnderCenter();
        if (newHighlightedKey != currentHighlightedKey)
        {
            if (newHighlightedKey != null)
                if (newHighlightedKey != shifftKey) {
                    newHighlightedKey.setBackgroundResource(
                            currentRow == NUM_ROWS - 1
                                    ? R.drawable.background_top_bottom_highlighted
                                    : R.drawable.background_top_highlighted
                    );
                } else {
                    newHighlightedKey.setBackgroundResource(R.mipmap.ic_shift_highlighted);
                }
            if (currentHighlightedKey != null) {
                if (currentHighlightedKey != shifftKey) {
                    currentHighlightedKey.setBackgroundResource(
                            currentRow == NUM_ROWS - 1
                                    ? R.drawable.background_top_bottom_normal
                                    : R.drawable.background_top_normal
                    );
                } else {
                    currentHighlightedKey.setBackgroundResource(R.drawable.ic_shift_normal);
                }
            }
            currentHighlightedKey = newHighlightedKey;
        }
    }

    private TextView getViewUnderCenter()
    {
        if (isLetterKeyboardVisible()) {
            int centerScrollY = vScrollView.getScrollY() + (findViewById(R.id.frame).getHeight() / 2) - letterKeyboard.getPaddingTop(); // all padding should be the same
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
        phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        testButton = (Button) findViewById(R.id.startTestButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        testNumberPicker = (NumberPicker) findViewById(R.id.testNumberPicker);
        shifftKey = textViews[NUM_ROWS - 2][textViews[NUM_ROWS - 2].length - 1];
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
        // shift key
        shifftKey.setBackgroundResource(R.drawable.ic_shift_normal);
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
