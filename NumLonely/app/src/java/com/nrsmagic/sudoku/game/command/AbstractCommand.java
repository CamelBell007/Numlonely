package com.nrsmagic.sudoku.game.command;

import android.os.Bundle;

public abstract class AbstractCommand
{
  private boolean mIsCheckpoint;
  
  public static AbstractCommand newInstance(String paramString)
  {
    if (paramString.equals(ClearAllNotesCommand.class.getSimpleName())) {
      return new ClearAllNotesCommand();
    }
    if (paramString.equals(EditCellNoteCommand.class.getSimpleName())) {
      return new EditCellNoteCommand();
    }
    if (paramString.equals(FillInNotesCommand.class.getSimpleName())) {
      return new FillInNotesCommand();
    }
    if (paramString.equals(SetCellValueCommand.class.getSimpleName())) {
      return new SetCellValueCommand();
    }
    throw new IllegalArgumentException(String.format("Unknown command class '%s'.", new Object[] { paramString }));
  }
  
  abstract void execute();
  
  public String getCommandClass()
  {
    return getClass().getSimpleName();
  }
  
  public boolean isCheckpoint()
  {
    return this.mIsCheckpoint;
  }
  
  void restoreState(Bundle paramBundle)
  {
    this.mIsCheckpoint = paramBundle.getBoolean("isCheckpoint");
  }
  
  void saveState(Bundle paramBundle)
  {
    paramBundle.putBoolean("isCheckpoint", this.mIsCheckpoint);
  }
  
  public void setCheckpoint(boolean paramBoolean)
  {
    this.mIsCheckpoint = paramBoolean;
  }
  
  abstract void undo();
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\command\AbstractCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */