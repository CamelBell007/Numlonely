package com.nrsmagic.sudoku.game.command;

import android.os.Bundle;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.CellNote;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClearAllNotesCommand
  extends AbstractCellCommand
{
  private List<NoteEntry> mOldNotes = new ArrayList();
  
  void execute()
  {
    CellCollection localCellCollection = getCells();
    this.mOldNotes.clear();
    int i = 0;
    if (i >= 9) {
      return;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      Cell localCell = localCellCollection.getCell(i, j);
      CellNote localCellNote = localCell.getNote();
      if (!localCellNote.isEmpty())
      {
        this.mOldNotes.add(new NoteEntry(i, j, localCellNote));
        localCell.setNote(new CellNote());
      }
    }
  }
  
  void restoreState(Bundle paramBundle)
  {
    super.restoreState(paramBundle);
    int[] arrayOfInt1 = paramBundle.getIntArray("rows");
    int[] arrayOfInt2 = paramBundle.getIntArray("cols");
    String[] arrayOfString = paramBundle.getStringArray("notes");
    for (int i = 0;; i++)
    {
      if (i >= arrayOfInt1.length) {
        return;
      }
      this.mOldNotes.add(new NoteEntry(arrayOfInt1[i], arrayOfInt2[i], CellNote.deserialize(arrayOfString[i])));
    }
  }
  
  void saveState(Bundle paramBundle)
  {
    super.saveState(paramBundle);
    int[] arrayOfInt1 = new int[this.mOldNotes.size()];
    int[] arrayOfInt2 = new int[this.mOldNotes.size()];
    String[] arrayOfString = new String[this.mOldNotes.size()];
    int i = 0;
    Iterator localIterator = this.mOldNotes.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        paramBundle.putIntArray("rows", arrayOfInt1);
        paramBundle.putIntArray("cols", arrayOfInt2);
        paramBundle.putStringArray("notes", arrayOfString);
        return;
      }
      NoteEntry localNoteEntry = (NoteEntry)localIterator.next();
      arrayOfInt1[i] = localNoteEntry.rowIndex;
      arrayOfInt2[i] = localNoteEntry.colIndex;
      arrayOfString[i] = localNoteEntry.note.serialize();
      i++;
    }
  }
  
  void undo()
  {
    CellCollection localCellCollection = getCells();
    Iterator localIterator = this.mOldNotes.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      NoteEntry localNoteEntry = (NoteEntry)localIterator.next();
      localCellCollection.getCell(localNoteEntry.rowIndex, localNoteEntry.colIndex).setNote(localNoteEntry.note);
    }
  }
  
  private static class NoteEntry
  {
    public int colIndex;
    public CellNote note;
    public int rowIndex;
    
    public NoteEntry(int paramInt1, int paramInt2, CellNote paramCellNote)
    {
      this.rowIndex = paramInt1;
      this.colIndex = paramInt2;
      this.note = paramCellNote;
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\command\ClearAllNotesCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */