package com.nrsmagic.sudoku.game;

import android.content.Context;

public class FolderInfo
{
  public long id;
  public String name;
  public int playingCount;
  public int puzzleCount;
  public int solvedCount;
  
  public FolderInfo() {}
  
  public FolderInfo(long paramLong, String paramString)
  {
    this.id = paramLong;
    this.name = paramString;
  }
  
  public String getDetail(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.puzzleCount == 0)
    {
      localStringBuilder.append(paramContext.getString(2131296281));
      return localStringBuilder.toString();
    }
    if (this.puzzleCount == 1) {}
    Object[] arrayOfObject1;
    for (String str = paramContext.getString(2131296282);; str = paramContext.getString(2131296283, arrayOfObject1))
    {
      localStringBuilder.append(str);
      int i = this.puzzleCount - this.solvedCount;
      if ((this.playingCount != 0) || (i != 0))
      {
        localStringBuilder.append(" (");
        if (this.playingCount != 0)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(this.playingCount);
          localStringBuilder.append(paramContext.getString(2131296287, arrayOfObject3));
          if (i != 0) {
            localStringBuilder.append(", ");
          }
        }
        if (i != 0)
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(i);
          localStringBuilder.append(paramContext.getString(2131296286, arrayOfObject2));
        }
        localStringBuilder.append(")");
      }
      if ((i != 0) || (this.puzzleCount == 0)) {
        break;
      }
      localStringBuilder.append(" (").append(paramContext.getString(2131296284)).append(")");
      break;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(this.puzzleCount);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\FolderInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */