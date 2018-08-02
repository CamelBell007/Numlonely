package com.nrsmagic.sudoku.gui;

import java.util.Formatter;

public class GameTimeFormat
{
  private static final int TIME_99_99 = 9801000;
  private Formatter mGameTimeFormatter = new Formatter(this.mTimeText);
  private StringBuilder mTimeText = new StringBuilder();
  
  public String format(long paramLong)
  {
    this.mTimeText.setLength(0);
    if (paramLong > 9801000L)
    {
      Formatter localFormatter2 = this.mGameTimeFormatter;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Long.valueOf(paramLong / 60000L);
      arrayOfObject2[1] = Long.valueOf(paramLong / 1000L % 60L);
      localFormatter2.format("%d:%02d", arrayOfObject2);
    }
    for (;;)
    {
      return this.mTimeText.toString();
      Formatter localFormatter1 = this.mGameTimeFormatter;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Long.valueOf(paramLong / 60000L);
      arrayOfObject1[1] = Long.valueOf(paramLong / 1000L % 60L);
      localFormatter1.format("%02d:%02d", arrayOfObject1);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\GameTimeFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */