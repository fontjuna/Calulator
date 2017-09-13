package noh.seung.hwa.civil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class TrenchActivity extends AppCompatActivity {
    private static final String TAG = "TrenchActivity";
//    public static final String INFORMATION = ""
//+ "▣ 터파기\n\n" +
//"숫자를 입력하고 다음의 설명하는 [버튼]을 길게 누르면 해당 값이 입력됩니다." +
//"입력값은 수식을 넣어도 계산되어 들어 갑니다.\n[리셋]버튼은 길게 누르면 터파기 값을 초기화 시킵니다.\n\n" +
//"단위는 전부 (m)이나 [관저고-터파기차]는 (mm)입니다.\n\n" +
//"[연장] : 터파기 연장(시점에서 종점까지의 거리)\n\n" +
//"[기계고] : 터파기 준비가 끝난 후의 레벨의 기계고\n\n" +
//"[시점관저고] : 시점의 관저고\n\n" +
//"[종점관저고] : 종점의 관저고\n\n" +
//"[관저고 - 터파기차] : (mm) 관종, 관경 및 기초에 따른 관저고와 터파기고와의 높이 차\n\n" +
//"▣ 위의 내용을 입력 후 [0 + 거리]를 입력하면 나머지 값을 계산해서 보여 줍니다.\n\n" +
//"[0+거리] : 시점으로 부터 계산할 위치까지의 거리\n\n" +
//"스타프값 : 계산 위치의 읽을 스타프값\n\n" +
//"관저고 : 계산 위치의 관저고\n\n" +
//"터파기고 : 계산 위치의 터파기고\n\n" +
//"▣ 수식 입력 후 [=]을 누르면 계산기 역할입니다.\n" +
//"예) 34 [기계고] -> 기계고에 34 세팅\n" +
//"예) 34 + 15 = -> 계산결과 49 출력\n"
            ;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trench);

        MobileAds.initialize(this, "ca-app-pub-3056892491225323/8837680993");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.help, null, false);
        ((TextView) view.findViewById(R.id.help_text)).setText(R.string.help_message);//.setText(R.string.help_message);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        view.findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return true;
    }
}
