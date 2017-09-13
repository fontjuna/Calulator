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
import noh.seung.hwa.calculator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalcFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    public static final String TAG = CalcFragment.class.getSimpleName();

    private TextView mResultTextView;
    private TextView mPreviuosTextView;
    private TextView mInputTextView;
    private ScrollView mScrollView;
    private String mInput;
    private String mPreviuos;
    private String mResult;
    private DecimalFormat df = new DecimalFormat("#,##0.######");
    private InputMethodManager imm;


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
        return inflater.inflate(R.layout.fragment_calc, container, false);
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

        view.findViewById(R.id.button_go).setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String input, oneChar;
        switch (view.getId()) {
            case R.id.button_go: {
                try {
                    input = mInput.replace("×", "*");
                    input = input.replace("÷", "/");
                    input = input.replace(",", "");
                    if (input.isEmpty()) {
                        input = "0";//mInputTextView.getHint().toString();
                    }
                    double val = Double.parseDouble(Calculation.Calculate(input));
                    displayResults(df.format(val));
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
}
