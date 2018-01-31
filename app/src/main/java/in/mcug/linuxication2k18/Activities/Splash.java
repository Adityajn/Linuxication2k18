package in.mcug.linuxication2k18.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.mcug.linuxication2k18.Prefs.PrefUtils;
import in.mcug.linuxication2k18.R;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Splash extends AppCompatActivity {

    @BindView(R.id.v_name) TextInputEditText vname;
    @BindView(R.id.register)  Button register;
    @BindView(R.id.name) TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        if(PrefUtils.getLoginStatus(getApplicationContext())){
            name.setText("Welcome,\n"+PrefUtils.getVolunteerName(getApplicationContext()));
            Runnable runnable = new Runnable(){
                public void run() {
                    startActivity(new Intent(getApplicationContext(),Home.class));
                }
            };
            new Handler().postDelayed(runnable, 3000);
        }
        else{
            vname.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
            name.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.register)
    public void register(){
        if(vname.getText().toString().length()<5){
            Alerter.create(this)
                    .setTitle("Please put proper name!!")
                    .setText("Please put name that must not clash with other volunteer's name.")
                    .setDuration(10000)
                    .setBackgroundColorRes(R.color.orange)
                    .enableSwipeToDismiss()
                    .show();
        }
        else{
            new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are You Sure?")
                    .setContentText("Please Make Sure Name Do Not Clash With Other Volunteer's Name!!!")
                    .setConfirmText("Confirm")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            PrefUtils.login(getApplicationContext(),vname.getText().toString());
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                    })
                    .setCancelText("Cancel")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    })
                    .show();
        }
    }
}
