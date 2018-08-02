package com.nrsmagic.sudoku.game.command;

import android.os.Bundle;

import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.CellNote;

public class EditCellNoteCommand
  extends AbstractCellCommand
{
  private int mCellColumn;
  private int mCellRow;
  private CellNote mNote;
  private CellNote mOldNote;
  
  EditCellNoteCommand() {}
  
  public EditCellNoteCommand(Cell paramCell, CellNote paramCellNote)
  {
    this.mCellRow = paramCell.getRowIndex();
    this.mCellColumn = paramCell.getColumnIndex();
    this.mNote = paramCellNote;
  }
  
  void execute()
  {
    Cell localCell = getCells().getCell(this.mCellRow, this.mCellColumn);
    this.mOldNote = localCell.getNote();
    localCell.setNote(this.mNote);
  }
  
  void restoreState(Bundle paramBundle)
  {
    super.restoreState(paramBundle);
    this.mCellRow = paramBundle.getInt("cellRow");
    this.mCellColumn = paramBundle.getInt("cellColumn");
    this.mNote = CellNote.deserialize(paramBundle.getString("note"));
    this.mOldNote = CellNote.deserialize(paramBundle.getString("oldNote"));
  }
  
  void saveState(Bundle paramBundle)
  {
    super.saveState(paramBundle);
    paramBundle.putInt("cellRow", this.mCellRow);
    paramBundle.putInt("cellColumn", this.mCellColumn);
    paramBundle.putString("note", this.mNote.serialize());
    paramBundle.putString("oldNote", this.mOldNote.serialize());
  }
  
  void undo()
  {
    getCells().getCell(this.mCellRow, this.mCellColumn).setNote(this.mOldNote);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\command\EditCellNoteCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */