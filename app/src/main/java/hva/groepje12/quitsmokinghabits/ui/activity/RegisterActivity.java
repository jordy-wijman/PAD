package hva.groepje12.quitsmokinghabits.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import hva.groepje12.quitsmokinghabits.R;
import hva.groepje12.quitsmokinghabits.api.OnLoopJEvent;
import hva.groepje12.quitsmokinghabits.api.Task;
import hva.groepje12.quitsmokinghabits.model.Alarm;
import hva.groepje12.quitsmokinghabits.model.Goal;
import hva.groepje12.quitsmokinghabits.model.Profile;
import hva.groepje12.quitsmokinghabits.service.DataHolder;
import hva.groepje12.quitsmokinghabits.service.GPSTracker;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText firstNameEditText, lastNameEditText, birthDateEditText, cigarettesPerDay, cigarettesPerPack, pricePerPack, stopDateEditText;
    Button createAccountButton, increaseCigPerDay, decreaseCigPerDay, increaseCigPerPack, decreaseCigPerPack, increasePricePerPack, decreasePricePerPack;
    static String clicked;
    Calendar birthDate, stopDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        birthDateEditText = (EditText) findViewById(R.id.edit_text_birth_date);
        firstNameEditText = (EditText) findViewById(R.id.edit_text_first_name);
        lastNameEditText = (EditText) findViewById(R.id.edit_text_last_name);
        cigarettesPerDay = (EditText) findViewById(R.id.cigarettesPerDay);
        cigarettesPerPack = (EditText) findViewById(R.id.cigarettesInPack);
        pricePerPack = (EditText) findViewById(R.id.pricePerPack);
        stopDateEditText = (EditText) findViewById(R.id.stopDate);

        increaseCigPerDay = (Button) findViewById(R.id.cigPerDayIncrease);
        decreaseCigPerDay = (Button) findViewById(R.id.cigPerDayDecrease);
        increaseCigPerPack = (Button) findViewById(R.id.cigPerPackIncrease);
        decreaseCigPerPack = (Button) findViewById(R.id.cigPerPackDecrease);
        increasePricePerPack = (Button) findViewById(R.id.pricePerPackIncrease);
        decreasePricePerPack = (Button) findViewById(R.id.pricePerPackDecrease);

        increaseCigPerDay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = Integer.parseInt(cigarettesPerDay.getText().toString()) + 1;
                    cigarettesPerDay.setText(Integer.toString(i));
                } catch (Exception e) {

                }
            }
        });

        decreaseCigPerDay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = Integer.parseInt(cigarettesPerDay.getText().toString()) - 1;
                    if (i == -1) {
                        i = 0;

                    }
                    cigarettesPerDay.setText(Integer.toString(i));
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e + "",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        increaseCigPerPack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = Integer.parseInt(cigarettesPerPack.getText().toString()) + 1;
                    cigarettesPerPack.setText(Integer.toString(i));
                } catch (Exception e) {

                }
            }
        });


        decreaseCigPerPack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = Integer.parseInt(cigarettesPerPack.getText().toString()) - 1;
                    if (i == -1) {
                        i = 0;

                    }
                    cigarettesPerPack.setText(Integer.toString(i));
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e + "",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //TODO: Decimal places
        increasePricePerPack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double i = Double.parseDouble(pricePerPack.getText().toString()) + 1.0;
                    pricePerPack.setText(i + "");
                } catch (Exception e) {

                }
            }
        });

        decreasePricePerPack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double i = Double.parseDouble(pricePerPack.getText().toString()) - 1.0;
                    if (i < 0.0) {
                        i = 0.0;

                    }
                    pricePerPack.setText(i + "");
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e + "",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        createAccountButton = (Button) findViewById(R.id.button_create_account);
        createAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        Profile profile = DataHolder.getCurrentProfile(this);

        birthDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePicker(v);
                    clicked = "birthDate";
                }
            }
        });

        stopDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePicker(v);
                    clicked = "stopDate";
                }
            }
        });

        if (profile.getFirstName() != null) {
            firstNameEditText.setText(profile.getFirstName());
            lastNameEditText.setText(profile.getLastName());
            cigarettesPerDay.setText(Integer.toString(profile.getCigarettesPerDay()));
            cigarettesPerPack.setText(Integer.toString(profile.getCigarettesPerPack()));
            pricePerPack.setText(Double.toString(profile.getPricePerPack()));

            clicked = "birthDate";
            setDate(profile.getBirthDate());
            clicked = "stopDate";
            setDate(profile.getStopDate());
            createAccountButton.setText("Opslaan");
        }


    }

    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        if (clicked.equals("stopDate")) {
            stopDate = calendar;
            stopDateEditText.setText(dateFormat.format(calendar.getTime()));
        } else {
            birthDate = calendar;
            birthDateEditText.setText(dateFormat.format(calendar.getTime()));
        }

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }


    private void registerUser() {

        String firstName = this.firstNameEditText.getText().toString();

        String lastName = this.lastNameEditText.getText().toString();
        String birthDateString = this.birthDateEditText.getText().toString();
        int cigarettesPerDayValue = Integer.parseInt(cigarettesPerDay.getText().toString());
        int cigarettesPerPackValue = Integer.parseInt(cigarettesPerPack.getText().toString());
        double pricePerPackValue = Double.parseDouble(pricePerPack.getText().toString());



        if (firstName.isEmpty() || firstName.length() > 20) {
            this.firstNameEditText.setError("Voornaam leeg of te lang!");
            return;
        }

        if (lastName.isEmpty() || lastName.length() > 20) {
            this.lastNameEditText.setError("Achternaam leeg of te lang!");
            return;
        }

        if (birthDateString.isEmpty() || birthDate == null || birthDate.after(Calendar.getInstance())) {
            this.birthDateEditText.setError("Vul een geldige geboortedatum in!");
            return;
        }

        if (stopDate == null || stopDate.before(Calendar.getInstance())) {
            stopDateEditText.setError("De stop datum moet in de toekomst liggen!");
            return;
        }

        Profile profile = DataHolder.getCurrentProfile(this);

        final boolean firstTime = profile.getFirstName() == null;
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setBirthDate(birthDate);
        profile.setStopDate(stopDate);
        profile.setCigarettesPerDay(cigarettesPerDayValue);
        profile.setCigarettesPerPack(cigarettesPerPackValue);
        profile.setPricePerPack(pricePerPackValue);
        profile.setGender(Profile.Gender.female);


        //Save the profile with a profile manager into the storage
        DataHolder.saveProfileToPreferences(this, profile);

        //Attempt to call api to register the profile
        RequestParams params = DataHolder.getProfileManager(this).getParams();

        Task registerProfileTask = new Task(new OnLoopJEvent() {
            @Override
            public void taskCompleted(JSONObject results) {
                Profile newProfile = DataHolder.getCurrentProfile(RegisterActivity.this);

                try {
                    JSONObject response = results.getJSONObject("response");
                    JSONObject dbProfile = response.getJSONObject("profile");
                    JSONArray alarms = dbProfile.getJSONArray("alarms");
                    JSONArray goals = dbProfile.getJSONArray("saving_goals");

                    ArrayList<Alarm> alarmList = new ArrayList<>();
                    ArrayList<Goal> goalList = new ArrayList<>();

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                    for (int i = 0; i < alarms.length(); i++) {
                        Alarm alarm = gson.fromJson(alarms.get(i).toString(), Alarm.class);
                        alarmList.add(alarm);
                    }

                    for (int i = 0; i < goals.length(); i++) {
                        Goal goal = gson.fromJson(goals.get(i).toString(), Goal.class);
                        goalList.add(goal);
                    }

                    newProfile.setAlarms(alarmList);
                    newProfile.setGoals(goalList);
                    newProfile.setMoneySaved(dbProfile.getDouble("saved_amount"));
                    newProfile.setId(dbProfile.getInt("id"));
                    DataHolder.saveProfileToPreferences(RegisterActivity.this, newProfile);

                    GPSTracker gpsTracker = DataHolder.getGpsTracker(getBaseContext());
                    if (!gpsTracker.isRunning()) {
                        gpsTracker.start();
                    }
                } catch (JSONException exception) {
                    Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (firstTime) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Welkom bij Quit Smoking Habits")
                            .setMessage("Deze app helpt je met het stoppen met roken door je af te leiden op de momenten dat je normaal rookt. Dit wordt gedaan op een tijd of locatie deze kun je instellen bij het tabje \"Momenten\". Voeg ook meteen je eerste spaardoel toe bij het tabje \"Doelen\".")
                            .setNeutralButton("Laten we beginnen!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(RegisterActivity.this, "Profiel is opgeslagen!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Profiel is opgeslagen!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void taskFailed(JSONObject results) {
                Toast.makeText(RegisterActivity.this, "Couldn't register profile, try again!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fatalError(String results) {
            }
        });

        registerProfileTask.execute(Task.REGISTER_PROFILE, params);
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

            if (clicked.equals("birthDate")) {
                year -= 20;
            } else {
                month = month < (12 - 3) ? month + 3 : month;
            }

            DatePickerDialog dpd = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,
                    (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

            dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return dpd;
        }
    }

}

