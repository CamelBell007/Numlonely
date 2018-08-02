package com.nrsmagic.sudoku.gui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.util.Log;
import android.webkit.WebView;
import com.nrsmagic.sudoku.utils.AndroidUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Changelog
{
  private static final String PREF_FILE_CHANGELOG = "changelog";
  private static final String TAG = "Changelog";
  private Context mContext;
  private SharedPreferences mPrefs;
  
  public Changelog(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPrefs = this.mContext.getSharedPreferences("changelog", 0);
  }
  
  private String getChangelogFromResources()
  {
    localInputStream = null;
    try
    {
      localInputStream = this.mContext.getResources().openRawResource(2131034112);
      char[] arrayOfChar = new char[65536];
      StringBuilder localStringBuilder = new StringBuilder();
      InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream, "UTF-8");
      int i;
      do
      {
        i = localInputStreamReader.read(arrayOfChar, 0, arrayOfChar.length);
        if (i > 0) {
          localStringBuilder.append(arrayOfChar, 0, i);
        }
      } while (i >= 0);
      String str = localStringBuilder.toString();
      if (localInputStream != null) {}
      try
      {
        localInputStream.close();
        return str;
      }
      catch (IOException localIOException4)
      {
        localIOException4.printStackTrace();
        Log.e("Changelog", "Error when reading changelog from raw resources.", localIOException4);
        return str;
      }
      try
      {
        localInputStream.close();
        throw ((Throwable)localObject);
      }
      catch (IOException localIOException1)
      {
        for (;;)
        {
          localIOException1.printStackTrace();
          Log.e("Changelog", "Error when reading changelog from raw resources.", localIOException1);
        }
      }
    }
    catch (IOException localIOException2)
    {
      localIOException2.printStackTrace();
      Log.e("Changelog", "Error when reading changelog from raw resources.", localIOException2);
      if (localInputStream != null) {}
      try
      {
        localInputStream.close();
        return "";
      }
      catch (IOException localIOException3)
      {
        for (;;)
        {
          localIOException3.printStackTrace();
          Log.e("Changelog", "Error when reading changelog from raw resources.", localIOException3);
        }
      }
    }
    finally
    {
      if (localInputStream == null) {}
    }
  }
  
  private void showChangelogDialog()
  {
    String str = getChangelogFromResources();
    WebView localWebView = new WebView(this.mContext);
    localWebView.loadData(str, "text/html", "utf-8");
    new Builder(this.mContext).setIcon(17301569).setTitle(2131296387).setView(localWebView).setPositiveButton(2131296264, null).create().show();
  }
  
  public void showOnFirstRun()
  {
    String str = "changelog_" + AndroidUtils.getAppVersionCode(this.mContext);
    if (!this.mPrefs.getBoolean(str, false))
    {
      showChangelogDialog();
      Editor localEditor = this.mPrefs.edit();
      localEditor.putBoolean(str, true);
      localEditor.commit();
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\Changelog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */