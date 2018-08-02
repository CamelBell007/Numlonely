package com.nrsmagic.sudoku.utils;

import android.annotation.SuppressLint;
import android.app.Activity;

public class VersionHelperAndroid11
{
  @SuppressLint({"NewApi"})
  public static void refreshActionBarMenu(Activity paramActivity)
  {
    paramActivity.invalidateOptionsMenu();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\utils\VersionHelperAndroid11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */