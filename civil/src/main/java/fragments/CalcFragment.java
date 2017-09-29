package fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import backing.Calculation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import noh.seung.hwa.civil.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalcFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    public static final String TAG = CalcFragment.class.getSimpleName();
    @BindView(R.id.length_text)
    TextView mLengthText;
    @BindView(R.id.ih_text)
    TextView mIhText;
    @BindView(R.id.start_text)
    TextView mStartText;
    @BindView(R.id.end_text)
    TextView mEndText;
    @BindView(R.id.deep_text)
    TextView mDeepText;
    @BindView(R.id.distance_text)
    TextView mDistanceText;
    @BindView(R.id.staff_text)
    TextView mStaffText;
    @BindView(R.id.level_text)
    TextView mLevelText;
    @BindView(R.id.ground_text)
    TextView mGroundText;
    @BindView(R.id.result_text_view)
    TextView mResultTextView;
    @BindView(R.id.scroll_win)
    ScrollView mScrollWin;
    @BindView(R.id.previuos_text_view)
    TextView mPreviuosTextView;
    @BindView(R.id.input_text_view)
    TextView mInputTextView;
    Unbinder unbinder;

    private String mInput;
    private String mPreviuos;
    private String mResult;
    private DecimalFormat df = new DecimalFormat("#,##0.######");
    private InputMethodManager imm;

    private static final int CIVIL_LENGTH = R.id.button_length;
    private static final int CIVIL_IH = R.id.button_ih;
    private static final int CIVIL_START = R.id.button_start;
    private static final int CIVIL_END = R.id.button_end;
    private static final int CIVIL_DEEP = R.id.button_deep;
    private static final int CIVIL_DISTANCE = R.id.button_distance;

    private static final String SAVE_LENGTH = "SAVE_LENGTH";
    private static final String SAVE_IH = "SAVE_IH";
    private static final String SAVE_START = "SAVE_START";
    private static final String SAVE_END = "SAVE_END";
    private static final String SAVE_DEEP = "SAVE_DEEP";
    private static final String SAVE_DISTANCE = "SAVE_DISTANCE";
    private static final String SAVE_HISTORY = "SAVE_HISTORY";
    private static final String SAVE_PREVIUOS = "SAVE_PREVIUOS";
    private static final String SAVE_INPUT = "SAVE_INPUT";

    public CalcFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calc, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mInput = "";
//        mPreviuos = "";
//        mResult = "";
//        mInputTextView = (TextView) view.findViewById(R.id.input_text_view);
//        mPreviuosTextView = (TextView) view.findViewById(R.id.previuos_text_view);
//        mResultTextView = (TextView) view.findViewById(R.id.result_text_view);
//        mScrollView = (ScrollView) view.findViewById(R.id.scroll_win);
//        mInputTextView.setText(mInput);
//        mPreviuosTextView.setText(mPreviuos);
//        mResultTextView.setText(mResult);
        restoreCurrentData();

        view.findViewById(R.id.button_0).setOnClickListener(this);
        view.findViewById(R.id.button_1).setOnClickListener(this);
        view.findViewById(R.id.button_2).setOnClickListener(this);
        view.findViewById(R.id.button_3).setOnClickListener(this);
        view.findViewById(R.id.button_4).setOnClickListener(this);
        view.findViewById(R.id.button_5).setOnClickListener(this);
        view.findViewById(R.id.button_6).setOnClickListener(this);
        view.findViewById(R.id.button_7).setOnClickListener(this);
        view.findViewById(R.id.button_8).setOnClickListener(this);
        view.findViewById(R.id.button_9).setOnClickListener(this);
        view.findViewById(R.id.button_dot).setOnClickListener(this);

        view.findViewById(R.id.button_sqrt).setOnClickListener(this);
        view.findViewById(R.id.button_pow).setOnClickListener(this);
//        view.findViewById(R.id.button_twozero).setOnClickListener(this);
        view.findViewById(R.id.button_clear).setOnClickListener(this);
        view.findViewById(R.id.button_plus).setOnClickListener(this);
        view.findViewById(R.id.button_minus).setOnClickListener(this);
        view.findViewById(R.id.button_divide).setOnClickListener(this);
        view.findViewById(R.id.button_by).setOnClickListener(this);
        view.findViewById(R.id.button_left).setOnClickListener(this);
        view.findViewById(R.id.button_right).setOnClickListener(this);

        view.findViewById(R.id.button_del).setOnClickListener(this);
        view.findViewById(R.id.button_ac).setOnClickListener(this);
        view.findViewById(R.id.button_go).setOnClickListener(this);

        view.findViewById(R.id.button_distance).setOnClickListener(this);

        view.findViewById(R.id.button_length).setOnLongClickListener(this);
        view.findViewById(R.id.button_ih).setOnLongClickListener(this);
        view.findViewById(R.id.button_deep).setOnLongClickListener(this);
        view.findViewById(R.id.button_start).setOnLongClickListener(this);
        view.findViewById(R.id.button_end).setOnLongClickListener(this);

        view.findViewById(R.id.button_reset).setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String input, oneChar;
        int where = view.getId();
        switch (where) {
            case CIVIL_LENGTH:
            case CIVIL_IH:
            case CIVIL_END:
            case CIVIL_START:
            case CIVIL_DEEP:
                break;
            case CIVIL_DISTANCE:
            case R.id.button_go: {
                try {
                    input = mInput.replace("×", "*");
                    input = input.replace("÷", "/");
                    input = input.replace(",", "");
                    if (input.isEmpty()) {
                        input = "0";//mInputTextView.getHint().toString();
                    }
                    double val = Double.parseDouble(Calculation.Calculate(input));
                    switch (where) {
                        case R.id.button_go:
                            displayResults(df.format(val));
                            break;
                        default:
                            civilEntry(where, val);
                            break;
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.invalid_expression, Toast.LENGTH_SHORT).show();
                    displayResults(getString(R.string.result_error));
                }
//                saveCurrentData();
                mScrollWin.fullScroll(ScrollView.FOCUS_DOWN);
                break;
            }
            case R.id.button_del: {
                mInput = mInput.length() > 0 ? mInput.substring(0, mInput.length() - 1) : "";
                mInput = mInput.isEmpty() ? "" : mInput;
                mInputTextView.setText(mInput);
                mInputTextView.setHint("0");
                break;
            }
            case R.id.button_ac: {
                mResult = "";
                mResultTextView.setText(mResult);
                mPreviuos = "";
                mPreviuosTextView.setText(mPreviuos);
                mInput = "";
                mInputTextView.setText(mInput);
                mInputTextView.setHint(getString(R.string.expression));
                break;
            }
            case R.id.button_clear: {
                mInput = "";
                mInputTextView.setText(mInput);
                mInputTextView.setHint("0");
                break;
            }
//            case R.id.button_pow: {
//                mInput += "^";
//                mInputTextView.setText(mInput);
//                break;
//            }
            default: {
                if (view.getId() == R.id.button_pow) {
                    oneChar = "^";
                } else {
                    oneChar = ((TextView) view).getText().toString();
                }
                if (mInput.isEmpty() && isOperator(oneChar)) {
                    if (!mInputTextView.getHint().toString().equals(getString(R.string.expression))) {
                        mInput = mInputTextView.getHint().toString();
                    }
                }
                mInput += oneChar;
                mInputTextView.setText(mInput);
                break;
            }
        }
        saveCurrentData();
    }

    private void civilEntry(int where, double val) {
        switch (where) {
            case CIVIL_DISTANCE:
                mDistanceText.setText(String.format("%,.3f", val));
                break;
            case CIVIL_IH:
                mIhText.setText(String.format("%,.3f", val));
                break;
            case CIVIL_DEEP:
                mDeepText.setText(String.format("%,.0f", val));
                break;
            case CIVIL_START:
                mStartText.setText(String.format("%,.3f", val));
                break;
            case CIVIL_END:
                mEndText.setText(String.format("%,.3f", val));
                break;
            case CIVIL_LENGTH:
                mLengthText.setText(String.format("%,.3f", val));
                break;
        }
        mInput = "";
        mInputTextView.setText(mInput);
        displayCivil();
    }

    private void displayCivil() {
        double hight; // 기계고 - 시점관저고
        double gradient; //구배
        double delta; // 거리만큼 레벨차
        double distance = Double.parseDouble(mDistanceText.getText().toString().replace(",", ""));
        double ih = Double.parseDouble(mIhText.getText().toString().replace(",", ""));
        double deep = Double.parseDouble(mDeepText.getText().toString().replace(",", ""));
        double end = Double.parseDouble(mEndText.getText().toString().replace(",", ""));
        double start = Double.parseDouble(mStartText.getText().toString().replace(",", ""));
        double length = Double.parseDouble(mLengthText.getText().toString().replace(",", ""));
        double staff;
        double level;
        double ground;

        try {
            gradient = (start - end) / length;
            hight = ih - start;
            delta = distance * gradient;
            level = start - delta;
            ground = level - (deep / 1000);
            staff = hight + delta + (deep / 1000);
            mStaffText.setText(String.format("%,.3f", staff));
            mLevelText.setText(String.format("%,.3f", level));
            mGroundText.setText(String.format("%,.3f", ground));
        } catch (Exception e) {

        }
    }

    private boolean isOperator(String oneChar) {
        return Pattern.matches("^[×÷+^-]*$", oneChar);
    }

    private void displayResults(String val) {
        mResult = mResult + mPreviuos;//.trim();
        if (!mResult.isEmpty() && mResult.charAt(mResult.length() - 1) != 13) {
            mResult += "\n";
        }
        mResultTextView.setText(mResult);
        mPreviuos = mInput + " = " + val;
        mPreviuosTextView.setText(mPreviuos);
        mInput = "";
        mInputTextView.setText(mInput);
        mInputTextView.setHint(val.equals(getString(R.string.result_error)) ? "0" : val);
    }

    @Override
    public boolean onLongClick(View view) {
        String input;
        int where = view.getId();
        switch (where) {
            case CIVIL_LENGTH:
            case CIVIL_IH:
            case CIVIL_END:
            case CIVIL_START:
            case CIVIL_DEEP:
                try {
                    input = mInput.replace("×", "*");
                    input = input.replace("÷", "/");
                    input = input.replace(",", "");
                    if (input.isEmpty()) {
                        input = "0";//mInputTextView.getHint().toString();
                    }
                    double val = Double.parseDouble(Calculation.Calculate(input));
                    civilEntry(where, val);
                    break;
                } catch (Exception e) {
                }
                break;

//            case CIVIL_DISTANCE:
            case R.id.button_reset:
                clearCivil();
                break;
        }
        saveCurrentData();
        return true;
    }

    private void clearCivil() {
        mLengthText.setText("0");
        mIhText.setText("0");
        mStartText.setText("0");
        mEndText.setText("0");
        mDeepText.setText("0");
        mDistanceText.setText("0");
        mStaffText.setText("0");
        mLevelText.setText("0");
        mGroundText.setText("0");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void restoreResult() {
        imm.hideSoftInputFromWindow(mResultTextView.getWindowToken(), 0);
        restoreCurrentData();
    }

    private void saveCurrentData() {
        SharedPreferences message = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = message.edit();
        editor.putString(SAVE_LENGTH, mLengthText.getText().toString());
        editor.putString(SAVE_IH, mIhText.getText().toString());
        editor.putString(SAVE_START, mStartText.getText().toString());
        editor.putString(SAVE_END, mEndText.getText().toString());
        editor.putString(SAVE_DEEP, mDeepText.getText().toString());
        editor.putString(SAVE_DISTANCE, mDistanceText.getText().toString());
        editor.putString(SAVE_HISTORY, mResultTextView.getText().toString());
        editor.putString(SAVE_PREVIUOS, mPreviuosTextView.getText().toString());
        editor.putString(SAVE_INPUT, mInputTextView.getText().toString());
        editor.apply();
    }

    private void restoreCurrentData() {
        SharedPreferences message = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mResult = message.getString(SAVE_HISTORY, "");
        mResultTextView.setText(mResult);
        mPreviuos = message.getString(SAVE_PREVIUOS, "");
        mPreviuosTextView.setText(mPreviuos);
        mInput = message.getString(SAVE_INPUT, "");
        mInputTextView.setText(mInput);
        mLengthText.setText(message.getString(SAVE_LENGTH, "0"));
        mIhText.setText(message.getString(SAVE_IH, "0"));
        mStartText.setText(message.getString(SAVE_START, "0"));
        mEndText.setText(message.getString(SAVE_END, "0"));
        mDeepText.setText(message.getString(SAVE_DEEP, "0"));
        mDistanceText.setText(message.getString(SAVE_DISTANCE, "0"));
        displayCivil();
    }
}
