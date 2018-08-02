package com.nrsmagic.sudoku.gui;

import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class GameSettingsActivity
  extends PreferenceActivity
  implements OnSharedPreferenceChangeListener
{
  private BackupManager mBackupManager;
  private OnPreferenceChangeListener mShowHintsChanged = new OnPreferenceChangeListener()
  {
    public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
    {
      boolean bool = ((Boolean)paramAnonymousObject).booleanValue();
      HintsQueue localHintsQueue = new HintsQueue(GameSettingsActivity.this);
      if (bool) {
        localHintsQueue.resetOneTimeHints();
      }
      return true;
    }
  };
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130968576);
    findPreference("show_hints").setOnPreferenceChangeListener(this.mShowHintsChanged);
    this.mBackupManager = new BackupManager(this);
    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    this.mBackupManager.dataChanged();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\GameSettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */