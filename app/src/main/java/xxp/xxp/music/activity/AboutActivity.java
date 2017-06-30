package xxp.xxp.music.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import xxp.xxp.music.R;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description : 关于界面
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (!checkServiceAlive()) {
            return;
        }

        getFragmentManager().beginTransaction().replace(R.id.ll_fragment_container, new AboutFragment()).commit();
    }

    public static class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        private Preference mVersion;
        private Preference mStar;
        private Preference mEmail;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_about);

            mVersion = findPreference("version");
            mStar = findPreference("star");
            mEmail = findPreference("email");

            setListener();
        }

        private void setListener() {
            mStar.setOnPreferenceClickListener(this);
            mEmail.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == mStar) {
                openUrl(getString(R.string.about_project_url));
                return true;
            } else if (preference == mEmail) {
                return true;
            }
            return false;
        }

        private void openUrl(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}
