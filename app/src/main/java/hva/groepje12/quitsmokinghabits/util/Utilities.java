package hva.groepje12.quitsmokinghabits.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lucas on 1-5-2017.
 */

public class Utilities {
    public static List<ApplicationInfo> getInstalledApplication(Context c) {

        return c.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
    }

    /*
     * Launch an application
     * @param	c	Context of application
     * @param	pm	the related package manager of the context
     * @param	pkgName	Name of the package to run
     */
    public static boolean launchApp(Context c, PackageManager pm, String pkgName) {
        // query the intent for lauching
        Intent intent = pm.getLaunchIntentForPackage(pkgName);
        // if intent is available
        if (intent != null) {
            try {
                // launch application
                c.startActivity(intent);
                // if succeed
                return true;

                // if fail
            } catch (ActivityNotFoundException ex) {
                // quick message notification
                Toast toast = Toast.makeText(c, "Application Not Found", Toast.LENGTH_LONG);
                // display message
                toast.show();
            }
        }
        // by default, fail to launch
        return false;
    }
}
