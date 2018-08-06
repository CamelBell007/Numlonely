package com.nrsmagic.sudoku.gui.inputmethod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.gui.HintsQueue;
import com.nrsmagic.sudoku.gui.SudokuBoardView;
import com.nrsmagic.sudoku.gui.SudokuBoardView.OnCellSelectedListener;
import com.nrsmagic.sudoku.gui.SudokuBoardView.OnCellTappedListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IMControlPanel
  extends LinearLayout
{
  public static final int INPUT_METHOD_NUMPAD = 2;
  public static final int INPUT_METHOD_POPUP = 0;
  public static final int INPUT_METHOD_SINGLE_NUMBER = 1;
  private int mActiveMethodIndex = -1;
  private SudokuBoardView mBoard;
  private Context mContext;
  private SudokuGame mGame;
  private HintsQueue mHintsQueue;
  private List<InputMethod> mInputMethods = new ArrayList();
  private SudokuBoardView.OnCellSelectedListener mOnCellSelected = new SudokuBoardView.OnCellSelectedListener()
  {
    public void onCellSelected(Cell paramAnonymousCell)
    {
      if ((IMControlPanel.this.mActiveMethodIndex != -1) && (IMControlPanel.this.mInputMethods != null)) {
        ((InputMethod)IMControlPanel.this.mInputMethods.get(IMControlPanel.this.mActiveMethodIndex)).onCellSelected(paramAnonymousCell);
      }
    }
  };
  private SudokuBoardView.OnCellTappedListener mOnCellTapListener = new SudokuBoardView.OnCellTappedListener()
  {
    public void onCellTapped(Cell paramAnonymousCell)
    {
      if ((IMControlPanel.this.mActiveMethodIndex != -1) && (IMControlPanel.this.mInputMethods != null)) {
        ((InputMethod)IMControlPanel.this.mInputMethods.get(IMControlPanel.this.mActiveMethodIndex)).onCellTapped(paramAnonymousCell);
      }
    }
  };
  private OnClickListener mSwitchModeListener = new OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      IMControlPanel.this.activateNextInputMethod();
    }
  };
  
  public IMControlPanel(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public IMControlPanel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }
  
  private void addInputMethod(int paramInt, InputMethod paramInputMethod)
  {
    paramInputMethod.initialize(this.mContext, this, this.mGame, this.mBoard, this.mHintsQueue);
    this.mInputMethods.add(paramInt, paramInputMethod);
  }
  
  private void createInputMethods()
  {
    if (this.mInputMethods.size() == 0)
    {
      addInputMethod(0, new IMPopup());
      addInputMethod(1, new IMSingleNumber());
      addInputMethod(2, new IMNumpad());
    }
  }
  
  private void ensureControlPanel(int paramInt)
  {
    InputMethod localInputMethod = (InputMethod)this.mInputMethods.get(paramInt);
    if (!localInputMethod.isInputMethodViewCreated())
    {
      View localView = localInputMethod.getInputMethodView();
      ((Button)localView.findViewById(2131230735)).setOnClickListener(this.mSwitchModeListener);
      addView(localView, -1, -1);
    }
  }
  
  private void ensureInputMethods()
  {
    if (this.mInputMethods.size() == 0) {
      throw new IllegalStateException("Input methods are not created yet. Call initialize() first.");
    }
  }
  
  public void activateFirstInputMethod()
  {
    ensureInputMethods();
    if ((this.mActiveMethodIndex == -1) || (!((InputMethod)this.mInputMethods.get(this.mActiveMethodIndex)).isEnabled())) {
      activateInputMethod(0);
    }
  }
  
  public void activateInputMethod(int paramInt)
  {
    if ((paramInt < -1) || (paramInt >= this.mInputMethods.size()))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      throw new IllegalArgumentException(String.format("Invalid method id: %s.", arrayOfObject));
    }
    ensureInputMethods();
    if (this.mActiveMethodIndex != -1) {
      ((InputMethod)this.mInputMethods.get(this.mActiveMethodIndex)).deactivate();
    }
    int i = paramInt;
    int j = 0;
    int k = 0;
    if (i != -1) {}
    int m;
    for (;;)
    {
      k = 0;
      if (0 == 0)
      {
        int i1 = this.mInputMethods.size();
        k = 0;
        if (j <= i1) {
          break label210;
        }
      }
      for (;;)
      {
        if (k == 0) {
          i = -1;
        }
        m = 0;
        if (m < this.mInputMethods.size()) {
          break label264;
        }
        this.mActiveMethodIndex = i;
        if (this.mActiveMethodIndex != -1)
        {
          InputMethod localInputMethod2 = (InputMethod)this.mInputMethods.get(this.mActiveMethodIndex);
          localInputMethod2.activate();
          if (this.mHintsQueue != null) {
            this.mHintsQueue.showOneTimeHint(localInputMethod2.getInputMethodName(), localInputMethod2.getNameResID(), localInputMethod2.getHelpResID(), new Object[0]);
          }
        }
        return;
        label210:
        if (!((InputMethod)this.mInputMethods.get(i)).isEnabled()) {
          break;
        }
        ensureControlPanel(i);
        k = 1;
      }
      i++;
      if (i == this.mInputMethods.size()) {
        i = 0;
      }
      j++;
    }
    label264:
    InputMethod localInputMethod1 = (InputMethod)this.mInputMethods.get(m);
    View localView;
    if (localInputMethod1.isInputMethodViewCreated())
    {
      localView = localInputMethod1.getInputMethodView();
      if (m != i) {
        break label317;
      }
    }
    label317:
    for (int n = 0;; n = 8)
    {
      localView.setVisibility(n);
      m++;
      break;
    }
  }
  
  public void activateNextInputMethod()
  {
    ensureInputMethods();
    int i = 1 + this.mActiveMethodIndex;
    if (i >= this.mInputMethods.size())
    {
      if (this.mHintsQueue != null) {
        this.mHintsQueue.showOneTimeHint("thatIsAll", 2131296335, 2131296340, new Object[0]);
      }
      i = 0;
    }
    activateInputMethod(i);
  }
  
  public int getActiveMethodIndex()
  {
    return this.mActiveMethodIndex;
  }
  
  public <T extends InputMethod> T getInputMethod(int paramInt)
  {
    ensureInputMethods();
    return (InputMethod)this.mInputMethods.get(paramInt);
  }
  
  public List<InputMethod> getInputMethods()
  {
    return Collections.unmodifiableList(this.mInputMethods);
  }
  
  public void initialize(SudokuBoardView paramSudokuBoardView, SudokuGame paramSudokuGame, HintsQueue paramHintsQueue)
  {
    this.mBoard = paramSudokuBoardView;
    this.mBoard.setOnCellTappedListener(this.mOnCellTapListener);
    this.mBoard.setOnCellSelectedListener(this.mOnCellSelected);
    this.mGame = paramSudokuGame;
    this.mHintsQueue = paramHintsQueue;
    createInputMethods();
  }
  
  public void pause()
  {
    Iterator localIterator = this.mInputMethods.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      ((InputMethod)localIterator.next()).pause();
    }
  }
  
  public void showHelpForActiveMethod()
  {
    ensureInputMethods();
    if (this.mActiveMethodIndex != -1)
    {
      InputMethod localInputMethod = (InputMethod)this.mInputMethods.get(this.mActiveMethodIndex);
      localInputMethod.activate();
      this.mHintsQueue.showHint(localInputMethod.getNameResID(), localInputMethod.getHelpResID(), new Object[0]);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\IMControlPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */