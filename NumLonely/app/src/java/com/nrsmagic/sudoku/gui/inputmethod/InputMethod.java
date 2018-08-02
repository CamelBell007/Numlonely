package com.nrsmagic.sudoku.gui.inputmethod;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.gui.HintsQueue;
import com.nrsmagic.sudoku.gui.SudokuBoardView;

public abstract class InputMethod
{
  protected boolean mActive = false;
  protected SudokuBoardView mBoard;
  protected Context mContext;
  protected IMControlPanel mControlPanel;
  private boolean mEnabled = true;
  protected SudokuGame mGame;
  protected HintsQueue mHintsQueue;
  private String mInputMethodName;
  protected View mInputMethodView;
  
  public void activate()
  {
    this.mActive = true;
    onActivated();
  }
  
  protected abstract View createControlPanelView();
  
  public void deactivate()
  {
    this.mActive = false;
    onDeactivated();
  }
  
  public abstract String getAbbrName();
  
  public abstract int getHelpResID();
  
  protected String getInputMethodName()
  {
    return this.mInputMethodName;
  }
  
  public View getInputMethodView()
  {
    if (this.mInputMethodView == null)
    {
      this.mInputMethodView = createControlPanelView();
      Button localButton = (Button)this.mInputMethodView.findViewById(2131230735);
      localButton.setText(getAbbrName());
      localButton.getBackground().setColorFilter(new LightingColorFilter(-16711681, 0));
      onControlPanelCreated(this.mInputMethodView);
    }
    return this.mInputMethodView;
  }
  
  public abstract int getNameResID();
  
  protected void initialize(Context paramContext, IMControlPanel paramIMControlPanel, SudokuGame paramSudokuGame, SudokuBoardView paramSudokuBoardView, HintsQueue paramHintsQueue)
  {
    this.mContext = paramContext;
    this.mControlPanel = paramIMControlPanel;
    this.mGame = paramSudokuGame;
    this.mBoard = paramSudokuBoardView;
    this.mHintsQueue = paramHintsQueue;
    this.mInputMethodName = getClass().getSimpleName();
  }
  
  public boolean isEnabled()
  {
    return this.mEnabled;
  }
  
  public boolean isInputMethodViewCreated()
  {
    return this.mInputMethodView != null;
  }
  
  protected void onActivated() {}
  
  protected void onCellSelected(Cell paramCell) {}
  
  protected void onCellTapped(Cell paramCell) {}
  
  protected void onControlPanelCreated(View paramView) {}
  
  protected void onDeactivated() {}
  
  protected void onPause() {}
  
  protected void onRestoreState(IMControlPanelStatePersister.StateBundle paramStateBundle) {}
  
  protected void onSaveState(IMControlPanelStatePersister.StateBundle paramStateBundle) {}
  
  public void pause()
  {
    onPause();
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
    if (!paramBoolean) {
      this.mControlPanel.activateNextInputMethod();
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\InputMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */