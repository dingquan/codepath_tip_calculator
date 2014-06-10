package com.example.tipcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TIP_PERCENT = "tipPercent";
	private EditText etBillAmt;
	private Float billAmt;
	private Integer tipPercent;
	private TextView totalTip;
	private TextView finalTotal;
	private SeekBar tipSlider;
	private TextView tvTipPercent;
	
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prefs = this.getSharedPreferences("com.codepath.tipcalculator", Context.MODE_PRIVATE);
		etBillAmt = (EditText)findViewById(R.id.etBillAmt);
		//set change listener so that it will calculate the tips when text has changed
		etBillAmt.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s){
				calculateTip();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
		totalTip = (TextView)findViewById(R.id.tvTipAmt);
		finalTotal = (TextView)findViewById(R.id.tvFinal);
		tvTipPercent = (TextView)findViewById(R.id.tvTipPercent);
		tipSlider = (SeekBar)findViewById(R.id.sbTipScale);
		//read the tip percentage from the user preference
		readTipPercentage();
		//setup the change listener to recalculate the tip percentage when user moves the slider
		tipSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tipPercent = seekBar.getProgress();
				tvTipPercent.setText(tipPercent.toString());
				calculateTip();
			}
		});
	}
	
	private void readTipPercentage() {
		tipPercent = prefs.getInt(TIP_PERCENT, 15);
		tvTipPercent.setText(tipPercent.toString());
		tipSlider.setProgress(tipPercent);
	}

	public void calculateTip()
	{
		String billAmtSt = etBillAmt.getText().toString();
		if (billAmtSt.isEmpty()) //return if no bill amount has been entered.
			return;
		billAmt = Float.valueOf(billAmtSt);
		Float totalTipAmt = billAmt * tipPercent / 100.0f;
		totalTip.setText(String.format("%.2f", totalTipAmt));
		Float finalTotalAmt = billAmt + totalTipAmt;
		finalTotal.setText(String.format("%.2f", finalTotalAmt));
	}
	
	@Override
	public void onDestroy(){
		//save the tipPercent
		prefs.edit().putInt(TIP_PERCENT, tipPercent).commit();
		super.onDestroy();
	}
	
	@Override
	public void onStop(){
		//save the tipPercent
		prefs.edit().putInt(TIP_PERCENT, tipPercent).commit();
		super.onStop();
	}
}
