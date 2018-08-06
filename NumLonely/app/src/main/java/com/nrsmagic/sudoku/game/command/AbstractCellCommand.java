package com.nrsmagic.sudoku.game.command;

import com.nrsmagic.sudoku.game.CellCollection;

public abstract class AbstractCellCommand
  extends AbstractCommand
{
  private CellCollection mCells;
  
  protected CellCollection getCells()
  {
    return this.mCells;
  }
  
  protected void setCells(CellCollection paramCellCollection)
  {
    this.mCells = paramCellCollection;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\command\AbstractCellCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */