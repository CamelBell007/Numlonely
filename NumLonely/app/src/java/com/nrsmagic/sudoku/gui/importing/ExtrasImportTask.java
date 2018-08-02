package com.nrsmagic.sudoku.gui.importing;

import com.nrsmagic.sudoku.db.SudokuInvalidFormatException;

public class ExtrasImportTask
  extends AbstractImportTask
{
  private boolean mAppendToFolder;
  private String mFolderName;
  private String mGames;
  
  public ExtrasImportTask(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.mFolderName = paramString1;
    this.mGames = paramString2;
    this.mAppendToFolder = paramBoolean;
  }
  
  protected void processImport()
    throws SudokuInvalidFormatException
  {
    String[] arrayOfString;
    int i;
    if (this.mAppendToFolder)
    {
      appendToFolder(this.mFolderName);
      arrayOfString = this.mGames.split("\n");
      i = arrayOfString.length;
    }
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        return;
        importFolder(this.mFolderName);
        break;
      }
      importGame(arrayOfString[j]);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\importing\ExtrasImportTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */