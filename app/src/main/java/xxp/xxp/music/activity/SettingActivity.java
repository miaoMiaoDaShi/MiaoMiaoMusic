package xxp.xxp.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import xxp.xxp.music.R;
import xxp.xxp.music.utils.ToastUtils;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (!checkServiceAlive()) {
            return;
        }

        getFragmentManager().beginTransaction().replace(xxp.xxp.music.R.id.ll_fragment_container,
                new SettingFragment()).commit();
    }

    public static class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        private Preference mSoundEffect;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(xxp.xxp.music.R.xml.preference_setting);

            mSoundEffect = findPreference(getString(xxp.xxp.music.R.string.setting_key_sound_effect));
            mSoundEffect.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == mSoundEffect) {
                Intent intent = new Intent("android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL");
                intent.putExtra("android.media.extra.PACKAGE_NAME", getActivity().getPackageName());
                intent.putExtra("android.media.extra.CONTENT_TYPE", 0);
                intent.putExtra("android.media.extra.AUDIO_SESSION", 0);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(xxp.xxp.music.R.string.device_not_support);
                }
                return true;
            }
            return false;
        }
    }
}
