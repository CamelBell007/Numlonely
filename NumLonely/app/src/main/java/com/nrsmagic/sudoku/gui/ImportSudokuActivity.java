package com.nrsmagic.sudoku.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ImportSudokuActivity
  extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = new Intent(getIntent());
    localIntent.setClass(this, SudokuImportActivity.class);
    startActivity(localIntent);
    finish();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\ImportSudokuActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */