package com.nrsmagic.sudoku.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import java.util.List;

public class AndroidUtils
{
  public static int getAppVersionCode(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException(localNameNotFoundException);
    }
  }
  
  public static String getAppVersionName(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException(localNameNotFoundException);
    }
  }
  
  public static boolean isIntentAvailable(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().queryIntentActivities(new Intent(paramString), 65536).size() > 0;
  }
  
  public static void setThemeFromPreferences(Context paramContext)
  {
    String str = PreferenceManager.getDefaultSharedPreferences(paramContext).getString("theme", "default");
    if (str.equals("default"))
    {
      paramContext.setTheme(2131361797);
      return;
    }
    if (str.equals("paperi"))
    {
      paramContext.setTheme(2131361798);
      return;
    }
    if (str.equals("paperii"))
    {
      paramContext.setTheme(2131361799);
      return;
    }
    paramContext.setTheme(2131361797);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\utils\AndroidUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */