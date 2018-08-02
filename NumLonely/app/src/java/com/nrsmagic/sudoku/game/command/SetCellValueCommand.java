package com.nrsmagic.sudoku.game.command;

import android.os.Bundle;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.CellCollection;

public class SetCellValueCommand
  extends AbstractCellCommand
{
  private int mCellColumn;
  private int mCellRow;
  private int mOldValue;
  private int mValue;
  
  SetCellValueCommand() {}
  
  public SetCellValueCommand(Cell paramCell, int paramInt)
  {
    this.mCellRow = paramCell.getRowIndex();
    this.mCellColumn = paramCell.getColumnIndex();
    this.mValue = paramInt;
  }
  
  void execute()
  {
    Cell localCell = getCells().getCell(this.mCellRow, this.mCellColumn);
    this.mOldValue = localCell.getValue();
    localCell.setValue(this.mValue);
  }
  
  void restoreState(Bundle paramBundle)
  {
    super.restoreState(paramBundle);
    this.mCellRow = paramBundle.getInt("cellRow");
    this.mCellColumn = paramBundle.getInt("cellColumn");
    this.mValue = paramBundle.getInt("value");
    this.mOldValue = paramBundle.getInt("oldValue");
  }
  
  void saveState(Bundle paramBundle)
  {
    super.saveState(paramBundle);
    paramBundle.putInt("cellRow", this.mCellRow);
    paramBundle.putInt("cellColumn", this.mCellColumn);
    paramBundle.putInt("value", this.mValue);
    paramBundle.putInt("oldValue", this.mOldValue);
  }
  
  void undo()
  {
    getCells().getCell(this.mCellRow, this.mCellColumn).setValue(this.mOldValue);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\command\SetCellValueCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */