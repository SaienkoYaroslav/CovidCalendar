package ua.com.app.saienko_yaroslav;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvPickDate, tvResultDate, tvDays;
    private DatePicker datePicker;
    private EditText etUserInput;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    private GregorianCalendar calendar;
    private int period = 270;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onDatePickerChanger();
    }

    void init() {
        tvPickDate = findViewById(R.id.tv_date);
        tvResultDate = findViewById(R.id.tv_result);
        datePicker = findViewById(R.id.date_picker);
        etUserInput = findViewById(R.id.et_user_input);
        tvDays = findViewById(R.id.day);
        etUserInput.addTextChangedListener(textWatcher);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int userInput = toInteger(etUserInput.getText().toString(), 0);
            tvDays.setText(days(userInput));
        }
    };

    void onDatePickerChanger() {
        datePicker.init(2021, 0, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar = new GregorianCalendar(view.getYear(), view.getMonth(), view.getDayOfMonth());
                DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                tvPickDate.setText("Видано: " + df.format(calendar.getTime()));
                period = toInteger(etUserInput.getText().toString(), 0);
                calendar.add(Calendar.DAY_OF_MONTH, period);
                tvResultDate.setText("Дійсний до: " + df.format(calendar.getTime()));
            }
        });
    }

    String days(int firstN) {
        Locale locale = new Locale("ukr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                config, getBaseContext().getResources().getDisplayMetrics());

        return getResources().getQuantityString(R.plurals.days, firstN);
    }

    public static Integer toInteger(final String str, final Integer defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etUserInput.removeTextChangedListener(textWatcher);
        datePicker.removeAllViews();
    }
}