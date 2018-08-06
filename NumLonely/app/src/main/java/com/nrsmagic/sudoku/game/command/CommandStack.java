package com.nrsmagic.sudoku.game.command;

import android.os.Bundle;
import com.nrsmagic.sudoku.game.CellCollection;
import java.util.Iterator;
import java.util.Stack;

public class CommandStack
{
  private CellCollection mCells;
  private Stack<AbstractCommand> mCommandStack = new Stack();
  
  public CommandStack(CellCollection paramCellCollection)
  {
    this.mCells = paramCellCollection;
  }
  
  private AbstractCommand pop()
  {
    return (AbstractCommand)this.mCommandStack.pop();
  }
  
  private void push(AbstractCommand paramAbstractCommand)
  {
    if ((paramAbstractCommand instanceof AbstractCellCommand)) {
      ((AbstractCellCommand)paramAbstractCommand).setCells(this.mCells);
    }
    this.mCommandStack.push(paramAbstractCommand);
  }
  
  private void validateCells()
  {
    this.mCells.validate();
  }
  
  public boolean empty()
  {
    return this.mCommandStack.empty();
  }
  
  public void execute(AbstractCommand paramAbstractCommand)
  {
    push(paramAbstractCommand);
    paramAbstractCommand.execute();
  }
  
  public boolean hasCheckpoint()
  {
    Iterator localIterator = this.mCommandStack.iterator();
    do
    {
      if (!localIterator.hasNext()) {
        return false;
      }
    } while (!((AbstractCommand)localIterator.next()).isCheckpoint());
    return true;
  }
  
  public boolean hasSomethingToUndo()
  {
    return this.mCommandStack.size() != 0;
  }
  
  public void restoreState(Bundle paramBundle)
  {
    int i = paramBundle.getInt("cmdStack.size");
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      Bundle localBundle = paramBundle.getBundle("cmdStack." + j);
      AbstractCommand localAbstractCommand = AbstractCommand.newInstance(localBundle.getString("commandClass"));
      localAbstractCommand.restoreState(localBundle);
      push(localAbstractCommand);
    }
  }
  
  public void saveState(Bundle paramBundle)
  {
    paramBundle.putInt("cmdStack.size", this.mCommandStack.size());
    for (int i = 0;; i++)
    {
      if (i >= this.mCommandStack.size()) {
        return;
      }
      AbstractCommand localAbstractCommand = (AbstractCommand)this.mCommandStack.get(i);
      Bundle localBundle = new Bundle();
      localBundle.putString("commandClass", localAbstractCommand.getCommandClass());
      localAbstractCommand.saveState(localBundle);
      paramBundle.putBundle("cmdStack." + i, localBundle);
    }
  }
  
  public void setCheckpoint()
  {
    if (!this.mCommandStack.empty()) {
      ((AbstractCommand)this.mCommandStack.peek()).setCheckpoint(true);
    }
  }
  
  public void undo()
  {
    if (!this.mCommandStack.empty())
    {
      pop().undo();
      validateCells();
    }
  }
  
  public void undoToCheckpoint()
  {
    if (this.mCommandStack.empty()) {}
    for (;;)
    {
      validateCells();
      return;
      ((AbstractCommand)this.mCommandStack.pop()).undo();
      if (!this.mCommandStack.empty()) {
        if (!((AbstractCommand)this.mCommandStack.peek()).isCheckpoint()) {
          break;
        }
      }
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\command\CommandStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */