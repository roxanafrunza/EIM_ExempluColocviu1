package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {
    Button pressMeButton;
    Button secondaryButton;
    Button pressMeTooButton;
    EditText pressMeEditText;
    EditText pressMeTooEditText;

    private int serviceStatus = Constants.SERVICE_STOPPED;
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        pressMeButton = (Button) findViewById(R.id.pressMeButton);
        pressMeButton.setOnClickListener(pressMeListener);
        pressMeEditText = (EditText) findViewById(R.id.leftEditText);

        pressMeTooButton = (Button) findViewById(R.id.pressMeTooButton);
        pressMeTooButton.setOnClickListener(pressMeTooListener);
        pressMeTooEditText = (EditText) findViewById(R.id.rightEditText);

        secondaryButton = (Button) findViewById(R.id.navigateButton);
        secondaryButton.setOnClickListener(secondaryButtonListener);


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.LEFT_EDIT_TEXT)) {
                pressMeEditText.setText(savedInstanceState.getString(Constants.LEFT_EDIT_TEXT));
            }
            if (savedInstanceState.containsKey(Constants.LEFT_EDIT_TEXT)) {
                pressMeTooEditText.setText(savedInstanceState.getString(Constants.RIGHT_EDIT_TEXT));
            }
        }

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    PressMeButtonListener pressMeListener = new PressMeButtonListener();
    private class PressMeButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int leftNumberOfClicks = Integer.parseInt(pressMeEditText.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(pressMeTooEditText.getText().toString());

            int initialValue = Integer.parseInt(pressMeEditText.getText().toString());
            initialValue++;
            pressMeEditText.setText(String.valueOf(initialValue));

            if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", leftNumberOfClicks);
                intent.putExtra("secondNumber", rightNumberOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }
    PressMeTooButtonListener pressMeTooListener = new PressMeTooButtonListener();
    private class PressMeTooButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int leftNumberOfClicks = Integer.parseInt(pressMeEditText.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(pressMeTooEditText.getText().toString());

            int initialValue = Integer.parseInt(pressMeTooEditText.getText().toString());
            initialValue++;
            pressMeTooEditText.setText(String.valueOf((initialValue)));

            if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", leftNumberOfClicks);
                intent.putExtra("secondNumber", rightNumberOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    SecondaryButtonListener secondaryButtonListener = new SecondaryButtonListener();
    private class SecondaryButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            int numberOfClicks = Integer.parseInt(pressMeEditText.getText().toString()) +
                    Integer.parseInt(pressMeTooEditText.getText().toString());
            intent.putExtra(Constants.NUMBER_CLICKS, numberOfClicks);
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(Constants.LEFT_EDIT_TEXT,  pressMeEditText .getText().toString());
        savedInstanceState.putString(Constants.RIGHT_EDIT_TEXT, pressMeTooEditText.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getString(Constants.LEFT_EDIT_TEXT) != null) {
            pressMeEditText.setText(savedInstanceState.getString(Constants.LEFT_EDIT_TEXT));
        }
        if (savedInstanceState.getString(Constants.RIGHT_EDIT_TEXT) != null) {
            pressMeTooEditText.setText(savedInstanceState.getString(Constants.RIGHT_EDIT_TEXT));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
