package hva.groepje12.quitsmokinghabits.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

import hva.groepje12.quitsmokinghabits.exceptions.ProfileNotFoundException;
import hva.groepje12.quitsmokinghabits.model.Profile;

public class ProfileManager {

    private SharedPreferences prefs;
    private Profile profile;

    public ProfileManager(Context context) {
        prefs = context.getSharedPreferences("profile", Context.MODE_PRIVATE);

        try {
            profile = getProfile();
        } catch (ProfileNotFoundException ex) {
            profile = new Profile();
            saveToPreferences(profile);
        }
    }

    public void saveToPreferences(Profile profile) {
        Editor prefEditor = prefs.edit();
        Gson gson = new Gson();

        prefEditor.putString("profile", gson.toJson(profile));
        prefEditor.apply();
    }

    public Profile getCurrentProfile() {
        return profile;
    }

    public RequestParams getParams() {
        Calendar birth = profile.getBirthDate();
        String birthDate = birth.get(Calendar.YEAR) + "-" + (birth.get(Calendar.MONTH) + 1) +
                "-" + birth.get(Calendar.DAY_OF_MONTH);

        Calendar stop = profile.getStopDate();
        String stopDate = stop.get(Calendar.YEAR) + "-" + (stop.get(Calendar.MONTH) + 1) +
                "-" + stop.get(Calendar.DAY_OF_MONTH);

        RequestParams params = new RequestParams();
        params.put("first_name", profile.getFirstName());
        params.put("last_name", profile.getLastName());
        params.put("birth_date", birthDate);
        params.put("cigarettes_per_pack", profile.getCigarettesPerPack());
        params.put("cigarettes_per_day", profile.getCigarettesPerDay());
        params.put("price_per_pack", profile.getPricePerPack());
        params.put("stop_date", stopDate);

        return params;
    }


    private Profile getProfile() throws ProfileNotFoundException {
        String profileJson = prefs.getString("profile", "");

        if (profileJson.equals("")) {
            throw new ProfileNotFoundException();
        }

        return new Gson().fromJson(profileJson, Profile.class);
    }
}
