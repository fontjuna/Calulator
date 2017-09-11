package fragments;


import android.os.Bundle;
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

    private ScrollView mScrollView;
    private String mInput;
    private String mPreviuos;
    private String mResult;
    private DecimalFormat df = new DecimalFormat("#,##0.######");
    private InputMethodManager imm;

    private static final int CIVIL_DISTANCE = R.id.button_distance;
    private static final int CIVIL_IH = R.id.button_ih;
    private static final int CIVIL_DEEP = R.id.button_deep;
    private static final int CIVIL_START = R.id.button_start;
    private static final int CIVIL_END = R.id.button_end;
    private static final int CIVIL_LENGTH = R.id.button_length;

    private int mCurentButton = 0;

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

    public void restoreResult() {
//        imm.hideSoftInputFromWindow(mResultTextView.getWindowToken(), 0);
//        restoreCurrentData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInput = "";
        mPreviuos = "";
        mResult = "";
        mInputTextView = (TextView) view.findViewById(R.id.input_text_view);
        mPreviuosTextView = (TextView) view.findViewById(R.id.previuos_text_view);
        mResultTextView = (TextView) view.findViewById(R.id.result_text_view);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_win);
        mInputTextView.setText(mInput);
        mPreviuosTextView.setText(mPreviuos);
        mResultTextView.setText(mResult);

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
        view.findViewById(R.id.button_twozero).setOnClickListener(this);
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
        view.findViewById(R.id.button_ih).setOnClickListener(this);
        view.findViewById(R.id.button_deep).setOnClickListener(this);
        view.findViewById(R.id.button_start).setOnClickListener(this);
        view.findViewById(R.id.button_end).setOnClickListener(this);
        view.findViewById(R.id.button_length).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String input, oneChar;
        int where = view.getId();
        switch (where) {
            case CIVIL_DEEP:
            case CIVIL_DISTANCE:
            case CIVIL_END:
            case CIVIL_IH:
            case CIVIL_LENGTH:
            case CIVIL_START:
            case R.id.button_go: {
                try {
                    input = mInput.replace("×", "*");
                    input = input.replace("÷", "/");
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
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
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
            case R.id.button_pow: {
                mInput += "^";
                mInputTextView.setText(mInput);
                break;
            }
            default: {
                oneChar = ((TextView) view).getText().toString();
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
    }

    private void civilEntry(int where, double val) {
        switch (where) {
            case CIVIL_DISTANCE:
                mDistanceText.setText(String.format("%,.3f",val));
                break;
            case CIVIL_IH:
                mIhText.setText(String.format("%,.3f",val));
                break;
            case CIVIL_DEEP:
                mDeepText.setText(String.format("%,.0f",val));
                break;
            case CIVIL_START:
                mStartText.setText(String.format("%,.3f",val));
                break;
            case CIVIL_END:
                mEndText.setText(String.format("%,.3f",val));
                break;
            case CIVIL_LENGTH:
                mLengthText.setText(String.format("%,.3f",val));
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
        double distance = Double.parseDouble(mDistanceText.getText().toString());
        double ih = Double.parseDouble(mIhText.getText().toString());
        double deep = Double.parseDouble(mDeepText.getText().toString());
        double end = Double.parseDouble(mEndText.getText().toString());
        double start = Double.parseDouble(mStartText.getText().toString());
        double length = Double.parseDouble(mLengthText.getText().toString());
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
        return Pattern.matches("^[×÷+-]*$", oneChar);
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
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
