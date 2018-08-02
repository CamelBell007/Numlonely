package com.nrsmagic.sudoku.db;

public class SudokuInvalidFormatException
  extends Exception
{
  private static final long serialVersionUID = -5415032786641425594L;
  private final String mData;
  
  public SudokuInvalidFormatException(String paramString)
  {
    super("Invalid format of sudoku.");
    this.mData = paramString;
  }
  
  public String getData()
  {
    return this.mData;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\db\SudokuInvalidFormatException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */