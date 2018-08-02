package com.nrsmagic.sudoku.utils;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import java.io.File;

public class SudokuBackup
  extends BackupAgentHelper
{
  private static final String DATABASE_KEY = "database_key";
  private static final String PREFS_DEFAULT_KEY = "default_prefs";
  
  public void onCreate()
  {
    addHelper("default_prefs", new SharedPreferencesBackupHelper(this, new String[] { getPackageName() + "_preferences" }));
    addHelper("database_key", new FileBackupHelper(this, new String[] { getDatabasePath("opensudoku").getAbsolutePath() }));
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\utils\SudokuBackup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */