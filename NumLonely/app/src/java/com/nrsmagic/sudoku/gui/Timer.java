package com.nrsmagic.sudoku.gui;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

abstract class Timer
  extends Handler
{
  private long mAccumTime;
  private boolean mIsRunning = false;
  private long mLastLogTime;
  private long mNextTime;
  private int mTickCount;
  private long mTickInterval = 0L;
  private final Runnable runner = new Runnable()
  {
    public final void run()
    {
      if (Timer.this.mIsRunning)
      {
        long l = SystemClock.uptimeMillis();
        Timer localTimer1 = Timer.this;
        localTimer1.mAccumTime += l - Timer.this.mLastLogTime;
        Timer.this.mLastLogTime = l;
        Timer localTimer2 = Timer.this;
        Timer localTimer3 = Timer.this;
        int i = localTimer3.mTickCount;
        localTimer3.mTickCount = (i + 1);
        if (!localTimer2.step(i, Timer.this.mAccumTime))
        {
          Timer localTimer4 = Timer.this;
          localTimer4.mNextTime += Timer.this.mTickInterval;
          if (Timer.this.mNextTime <= l)
          {
            Timer localTimer5 = Timer.this;
            localTimer5.mNextTime += Timer.this.mTickInterval;
          }
          Timer.this.postAtTime(Timer.this.runner, Timer.this.mNextTime);
        }
      }
      else
      {
        return;
      }
      Timer.this.mIsRunning = false;
      Timer.this.done();
    }
  };
  
  public Timer(long paramLong)
  {
    this.mTickInterval = paramLong;
    this.mIsRunning = false;
    this.mAccumTime = 0L;
  }
  
  protected void done() {}
  
  public final long getTime()
  {
    return this.mAccumTime;
  }
  
  public final boolean isRunning()
  {
    return this.mIsRunning;
  }
  
  public final void reset()
  {
    stop();
    this.mTickCount = 0;
    this.mAccumTime = 0L;
  }
  
  boolean restoreState(Bundle paramBundle)
  {
    return restoreState(paramBundle, true);
  }
  
  boolean restoreState(Bundle paramBundle, boolean paramBoolean)
  {
    this.mTickInterval = paramBundle.getLong("tickInterval");
    this.mIsRunning = paramBundle.getBoolean("isRunning");
    this.mTickCount = paramBundle.getInt("tickCount");
    this.mAccumTime = paramBundle.getLong("accumTime");
    this.mLastLogTime = SystemClock.uptimeMillis();
    if (this.mIsRunning)
    {
      if (!paramBoolean) {
        break label64;
      }
      start();
    }
    for (;;)
    {
      return true;
      label64:
      this.mIsRunning = false;
    }
  }
  
  void saveState(Bundle paramBundle)
  {
    if (this.mIsRunning)
    {
      long l = SystemClock.uptimeMillis();
      this.mAccumTime += l - this.mLastLogTime;
      this.mLastLogTime = l;
    }
    paramBundle.putLong("tickInterval", this.mTickInterval);
    paramBundle.putBoolean("isRunning", this.mIsRunning);
    paramBundle.putInt("tickCount", this.mTickCount);
    paramBundle.putLong("accumTime", this.mAccumTime);
  }
  
  public void start()
  {
    if (this.mIsRunning) {
      return;
    }
    this.mIsRunning = true;
    long l = SystemClock.uptimeMillis();
    this.mLastLogTime = l;
    this.mNextTime = l;
    postAtTime(this.runner, this.mNextTime);
  }
  
  protected abstract boolean step(int paramInt, long paramLong);
  
  public void stop()
  {
    if (this.mIsRunning)
    {
      this.mIsRunning = false;
      long l = SystemClock.uptimeMillis();
      this.mAccumTime += l - this.mLastLogTime;
      this.mLastLogTime = l;
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\Timer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */