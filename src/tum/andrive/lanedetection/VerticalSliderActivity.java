package tum.andrive.lanedetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class VerticalSliderActivity extends Activity {

private Button forwardButton;
private TextView sliderText;
private VerticalSeekBar verticalSeebar;

@Override
public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_selection);
        
        sliderText = (TextView) findViewById(R.id.verticalSeekbarText);
        forwardButton = (Button)findViewById(R.id.forwardButton);
        verticalSeebar = (VerticalSeekBar)findViewById(R.id.verticalSeekbar);

        forwardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), LaneDetector.class); 
			    intent.putExtra("houghvalue", sliderText.getText().toString());
			    startActivity(intent);
			}
 
		});


   verticalSeebar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

   @Override
   public void onStopTrackingTouch(SeekBar seekBar) {

   }

   @Override
   public void onStartTrackingTouch(SeekBar seekBar) {

   }

   @Override
   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    sliderText.setText(""+progress);

   }


  });   
        

}   


}
