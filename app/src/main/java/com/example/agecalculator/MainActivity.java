/**
 * Age Calculator app
 * Date : 02.10.19
 * Last Modified: 05.10.19
 */

package com.example.agecalculator;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, selectBirthday, show_age, next_birthday;
    Animation cloveranim, frombottom, blink_anim;
    TextView selectdate;
    Button calculate;
    DatePickerDialog.OnDateSetListener pickdate;
    String birthdate, presentdate;

    TextView years, months,days;
    TextView next_birth_day, next_birth_months;
    boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        blink_anim = AnimationUtils.loadAnimation(this, R.anim.blink_anim);
        cloveranim = AnimationUtils.loadAnimation(this, R.anim.cloveranim);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        selectBirthday = (LinearLayout) findViewById(R.id.selectBirthday);
        show_age = (LinearLayout) findViewById(R.id.show_age);
        next_birthday = (LinearLayout) findViewById(R.id.next_birthday);
        calculate = (Button) findViewById(R.id.calculate);

        selectdate = (TextView) findViewById(R.id.selectdate);
        selectdate.setText("Select date");

        bgapp.animate().translationY(-1550).setDuration(800).setStartDelay(1100);
        clover.animate().alpha(0).setDuration(800).setStartDelay(1100);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(1100);
        texthome.startAnimation(frombottom);
        selectBirthday.startAnimation(frombottom);
        calculate.startAnimation(frombottom);

        years = (TextView) findViewById(R.id.years);
        months = (TextView) findViewById(R.id.months);
        days = (TextView) findViewById(R.id.day);

        next_birth_months = (TextView) findViewById(R.id.next_birth_months);
        next_birth_day = (TextView) findViewById(R.id.next_birth_day);

        show_age.setVisibility(View.INVISIBLE);
        next_birthday.setVisibility(View.INVISIBLE);

        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStart = false;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                presentdate = day + "/" + (month+1) + "/" + year;

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                        R.style.MySpinnerDatePickerStyle,pickdate,
                        year, month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        pickdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                birthdate = dayOfMonth + "/" + month + "/" + year;
                String date = dayOfMonth + "/" + returnMonth(month) + "/" + year;
                selectdate.setText(date);
            }
        };

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstStart){
                    Toast.makeText(MainActivity.this, "Please select date first.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date date1 = null;
                    Date date2 = null;

                    int resultyears = 0;
                    try {
                        System.out.println(birthdate);
                        System.out.println(presentdate);
                        date1 = simpleDateFormat.parse(birthdate);
                        date2 = simpleDateFormat.parse(presentdate);

                        long startdate = date1.getTime();
                        long enddate = date2.getTime();

                        if(startdate <= enddate){
                            Period period = new Period(startdate, enddate, PeriodType.yearMonthDay());

                            resultyears = period.getYears();
                            int resultmonths = period.getMonths();
                            int resultdays = period.getDays();

                            years.setText("" + resultyears);
                            months.setText("" + resultmonths);
                            days.setText("" + resultdays);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Can't select any date from future.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date1);

                    cal.add(Calendar.YEAR, resultyears + 1);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Date date3 = null;
                    try {
                        date3 = simpleDateFormat.parse(day + "/" + (month+1) + "/" + year);
                        long startdate = date2.getTime();
                        long enddate = date3.getTime();

                        if(startdate <= enddate){
                            Period period = new Period(startdate, enddate, PeriodType.yearMonthDay());

                            int resultmonths = period.getMonths();
                            int resultdays = period.getDays();

                            next_birth_months.setText("" + resultmonths);
                            next_birth_day.setText("" + resultdays);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    show_age.setVisibility(View.VISIBLE);
                    next_birthday.setVisibility(View.VISIBLE);
                    show_age.startAnimation(frombottom);
                    next_birthday.startAnimation(frombottom);
                }
            }
        });
    }

    String returnMonth(int month){
        if(month == 1) return "Jan";
        if(month == 2) return "Feb";
        if(month == 3) return "Mar";
        if(month == 4) return "Apr";
        if(month == 5) return "May";
        if(month == 6) return "Jun";
        if(month == 7) return "Jul";
        if(month == 8) return "Aug";
        if(month == 9) return "Sep";
        if(month == 10) return "Oct";
        if(month == 11) return "Nov";
        if(month == 12) return "Dec";
        return null;
    }
}
