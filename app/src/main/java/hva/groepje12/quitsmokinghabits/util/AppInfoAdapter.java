package hva.groepje12.quitsmokinghabits.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hva.groepje12.quitsmokinghabits.R;

/**
 * Created by lucas on 1-5-2017.
 */

public class AppInfoAdapter extends BaseAdapter{
    private Context mContext;
    private List<ApplicationInfo> mListAppInfo;
    private PackageManager mPackManager;

    public AppInfoAdapter(Context c, List<ApplicationInfo> list, PackageManager pm) {
        mContext = c;
        mListAppInfo = list;
        mPackManager = pm;
    }

    @Override
    public int getCount() {
        return mListAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the selected entry
        ApplicationInfo entry = mListAppInfo.get(position);

        // reference to convertView
        View v = convertView;

        // inflate new layout if null
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.listview_apps, null);
        }

        // load controls from layout resources
        ImageView ivAppIcon = (ImageView)v.findViewById(R.id.appIcon);
        TextView tvAppName = (TextView)v.findViewById(R.id.appName);
        //TextView tvPkgName = (TextView)v.findViewById(R.id.appPackage);

        // set data to display
        ivAppIcon.setImageDrawable(entry.loadIcon(mPackManager));
        tvAppName.setText(entry.loadLabel(mPackManager));
        //tvPkgName.setText(entry.packageName);

        // return view
        return v;
    }
}
