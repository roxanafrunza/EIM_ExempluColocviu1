package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    TextView numberOfClickTextView;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        numberOfClickTextView = (TextView) findViewById(R.id.numberOfClicksTextView);
        okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey(Constants.NUMBER_CLICKS)) {
            numberOfClickTextView.setText(String.valueOf(intent.getIntExtra(Constants.NUMBER_CLICKS, -1)));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.okButton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }
}
