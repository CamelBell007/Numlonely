package com.nrsmagic.sudoku.db;

public class SudokuImportParams
{
  public long created;
  public String data;
  public long lastPlayed;
  public String note;
  public long state;
  public long time;
  
  public void clear()
  {
    this.created = 0L;
    this.state = 1L;
    this.time = 0L;
    this.lastPlayed = 0L;
    this.data = null;
    this.note = null;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\db\SudokuImportParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */