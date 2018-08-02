package com.nrsmagic.sudoku.game;

import android.os.Bundle;
import android.os.SystemClock;
import com.nrsmagic.sudoku.game.command.AbstractCommand;
import com.nrsmagic.sudoku.game.command.ClearAllNotesCommand;
import com.nrsmagic.sudoku.game.command.CommandStack;
import com.nrsmagic.sudoku.game.command.EditCellNoteCommand;
import com.nrsmagic.sudoku.game.command.FillInNotesCommand;
import com.nrsmagic.sudoku.game.command.SetCellValueCommand;

public class SudokuGame
{
  public static final int GAME_STATE_COMPLETED = 2;
  public static final int GAME_STATE_NOT_STARTED = 1;
  public static final int GAME_STATE_PLAYING;
  private long mActiveFromTime = -1L;
  private CellCollection mCells;
  private CommandStack mCommandStack;
  private long mCreated = 0L;
  private long mId;
  private long mLastPlayed = 0L;
  private String mNote;
  private OnPuzzleSolvedListener mOnPuzzleSolvedListener;
  private int mState = 1;
  private long mTime = 0L;
  
  public static SudokuGame createEmptyGame()
  {
    SudokuGame localSudokuGame = new SudokuGame();
    localSudokuGame.setCells(CellCollection.createEmpty());
    localSudokuGame.setCreated(System.currentTimeMillis());
    return localSudokuGame;
  }
  
  private void executeCommand(AbstractCommand paramAbstractCommand)
  {
    this.mCommandStack.execute(paramAbstractCommand);
  }
  
  private void finish()
  {
    pause();
    this.mState = 2;
  }
  
  public void clearAllNotes()
  {
    executeCommand(new ClearAllNotesCommand());
  }
  
  public void fillInNotes()
  {
    executeCommand(new FillInNotesCommand());
  }
  
  public CellCollection getCells()
  {
    return this.mCells;
  }
  
  public long getCreated()
  {
    return this.mCreated;
  }
  
  public long getId()
  {
    return this.mId;
  }
  
  public long getLastPlayed()
  {
    return this.mLastPlayed;
  }
  
  public String getNote()
  {
    return this.mNote;
  }
  
  public int getState()
  {
    return this.mState;
  }
  
  public long getTime()
  {
    if (this.mActiveFromTime != -1L) {
      return this.mTime + SystemClock.uptimeMillis() - this.mActiveFromTime;
    }
    return this.mTime;
  }
  
  public boolean hasSomethingToUndo()
  {
    return this.mCommandStack.hasSomethingToUndo();
  }
  
  public boolean hasUndoCheckpoint()
  {
    return this.mCommandStack.hasCheckpoint();
  }
  
  public boolean isCompleted()
  {
    return this.mCells.isCompleted();
  }
  
  public void pause()
  {
    this.mTime += SystemClock.uptimeMillis() - this.mActiveFromTime;
    this.mActiveFromTime = -1L;
    setLastPlayed(System.currentTimeMillis());
  }
  
  public void reset()
  {
    int i = 0;
    if (i >= 9)
    {
      validate();
      setTime(0L);
      setLastPlayed(0L);
      this.mState = 1;
      return;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      Cell localCell = this.mCells.getCell(i, j);
      if (localCell.isEditable())
      {
        localCell.setValue(0);
        localCell.setNote(new CellNote());
      }
    }
  }
  
  public void restoreState(Bundle paramBundle)
  {
    this.mId = paramBundle.getLong("id");
    this.mNote = paramBundle.getString("note");
    this.mCreated = paramBundle.getLong("created");
    this.mState = paramBundle.getInt("state");
    this.mTime = paramBundle.getLong("time");
    this.mLastPlayed = paramBundle.getLong("lastPlayed");
    this.mCells = CellCollection.deserialize(paramBundle.getString("cells"));
    this.mCommandStack = new CommandStack(this.mCells);
    this.mCommandStack.restoreState(paramBundle);
    validate();
  }
  
  public void resume()
  {
    this.mActiveFromTime = SystemClock.uptimeMillis();
  }
  
  public void saveState(Bundle paramBundle)
  {
    paramBundle.putLong("id", this.mId);
    paramBundle.putString("note", this.mNote);
    paramBundle.putLong("created", this.mCreated);
    paramBundle.putInt("state", this.mState);
    paramBundle.putLong("time", this.mTime);
    paramBundle.putLong("lastPlayed", this.mLastPlayed);
    paramBundle.putString("cells", this.mCells.serialize());
    this.mCommandStack.saveState(paramBundle);
  }
  
  public void setCellNote(Cell paramCell, CellNote paramCellNote)
  {
    if (paramCell == null) {
      throw new IllegalArgumentException("Cell cannot be null.");
    }
    if (paramCellNote == null) {
      throw new IllegalArgumentException("Note cannot be null.");
    }
    if (paramCell.isEditable()) {
      executeCommand(new EditCellNoteCommand(paramCell, paramCellNote));
    }
  }
  
  public void setCellValue(Cell paramCell, int paramInt)
  {
    if (paramCell == null) {
      throw new IllegalArgumentException("Cell cannot be null.");
    }
    if ((paramInt < 0) || (paramInt > 9)) {
      throw new IllegalArgumentException("Value must be between 0-9.");
    }
    if (paramCell.isEditable())
    {
      executeCommand(new SetCellValueCommand(paramCell, paramInt));
      validate();
      if (isCompleted())
      {
        finish();
        if (this.mOnPuzzleSolvedListener != null) {
          this.mOnPuzzleSolvedListener.onPuzzleSolved();
        }
      }
    }
  }
  
  public void setCells(CellCollection paramCellCollection)
  {
    this.mCells = paramCellCollection;
    validate();
    this.mCommandStack = new CommandStack(this.mCells);
  }
  
  public void setCreated(long paramLong)
  {
    this.mCreated = paramLong;
  }
  
  public void setId(long paramLong)
  {
    this.mId = paramLong;
  }
  
  public void setLastPlayed(long paramLong)
  {
    this.mLastPlayed = paramLong;
  }
  
  public void setNote(String paramString)
  {
    this.mNote = paramString;
  }
  
  public void setOnPuzzleSolvedListener(OnPuzzleSolvedListener paramOnPuzzleSolvedListener)
  {
    this.mOnPuzzleSolvedListener = paramOnPuzzleSolvedListener;
  }
  
  public void setState(int paramInt)
  {
    this.mState = paramInt;
  }
  
  public void setTime(long paramLong)
  {
    this.mTime = paramLong;
  }
  
  public void setUndoCheckpoint()
  {
    this.mCommandStack.setCheckpoint();
  }
  
  public void start()
  {
    this.mState = 0;
    resume();
  }
  
  public void undo()
  {
    this.mCommandStack.undo();
  }
  
  public void undoToCheckpoint()
  {
    this.mCommandStack.undoToCheckpoint();
  }
  
  public void validate()
  {
    this.mCells.validate();
  }
  
  public static abstract interface OnPuzzleSolvedListener
  {
    public abstract void onPuzzleSolved();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\SudokuGame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */