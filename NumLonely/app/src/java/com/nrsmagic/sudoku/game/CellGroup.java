package com.nrsmagic.sudoku.game;

import java.util.HashMap;
import java.util.Map;

public class CellGroup
{
  private Cell[] mCells = new Cell[9];
  private int mPos = 0;
  
  public void addCell(Cell paramCell)
  {
    this.mCells[this.mPos] = paramCell;
    this.mPos = (1 + this.mPos);
  }
  
  public boolean contains(int paramInt)
  {
    for (int i = 0;; i++)
    {
      if (i >= this.mCells.length) {
        return false;
      }
      if (this.mCells[i].getValue() == paramInt) {
        return true;
      }
    }
  }
  
  protected boolean validate()
  {
    boolean bool = true;
    HashMap localHashMap = new HashMap();
    int i = 0;
    if (i >= this.mCells.length) {
      return bool;
    }
    Cell localCell = this.mCells[i];
    int j = localCell.getValue();
    if (localHashMap.get(Integer.valueOf(j)) != null)
    {
      this.mCells[i].setValid(Boolean.valueOf(false));
      ((Cell)localHashMap.get(Integer.valueOf(j))).setValid(Boolean.valueOf(false));
      bool = false;
    }
    for (;;)
    {
      i++;
      break;
      localHashMap.put(Integer.valueOf(j), localCell);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\CellGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */