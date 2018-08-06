package com.nrsmagic.sudoku.gui;

import android.content.Context;
import com.nrsmagic.sudoku.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class SudokuListFilter
{
  private Context mContext;
  public boolean showStateCompleted = true;
  public boolean showStateNotStarted = true;
  public boolean showStatePlaying = true;
  
  public SudokuListFilter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public String toString()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.showStateNotStarted) {
      localArrayList.add(this.mContext.getString(2131296290));
    }
    if (this.showStatePlaying) {
      localArrayList.add(this.mContext.getString(2131296289));
    }
    if (this.showStateCompleted) {
      localArrayList.add(this.mContext.getString(2131296288));
    }
    return StringUtils.join(localArrayList, ",");
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuListFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */