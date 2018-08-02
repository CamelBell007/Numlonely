package com.nrsmagic.sudoku.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class DatabaseHelper
  extends SQLiteOpenHelper
{
  public static final int DATABASE_VERSION = 8;
  private static final String TAG = "DatabaseHelper";
  private Context mContext;
  
  DatabaseHelper(Context paramContext)
  {
    super(paramContext, "opensudoku", null, 8);
    this.mContext = paramContext;
  }
  
  private void createIndexes(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("create index sudoku_idx1 on sudoku (folder_id);");
  }
  
  /* Error */
  private int getNewSudokuId(SQLiteDatabase paramSQLiteDatabase)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: iconst_1
    //   3: anewarray 38	java/lang/String
    //   6: astore 5
    //   8: aload 5
    //   10: iconst_0
    //   11: ldc 40
    //   13: iconst_1
    //   14: anewarray 42	java/lang/Object
    //   17: dup
    //   18: iconst_0
    //   19: ldc 44
    //   21: aastore
    //   22: invokestatic 48	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   25: aastore
    //   26: aload_1
    //   27: ldc 50
    //   29: aload 5
    //   31: aconst_null
    //   32: aconst_null
    //   33: aconst_null
    //   34: aconst_null
    //   35: aconst_null
    //   36: invokevirtual 54	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   39: astore_2
    //   40: aload_2
    //   41: invokeinterface 60 1 0
    //   46: ifeq +44 -> 90
    //   49: aload_2
    //   50: iconst_0
    //   51: invokeinterface 64 2 0
    //   56: istore 7
    //   58: iload 7
    //   60: iconst_1
    //   61: iadd
    //   62: istore 8
    //   64: aload_2
    //   65: ifnull +9 -> 74
    //   68: aload_2
    //   69: invokeinterface 68 1 0
    //   74: iload 8
    //   76: ireturn
    //   77: astore_3
    //   78: aload_2
    //   79: ifnull +9 -> 88
    //   82: aload_2
    //   83: invokeinterface 68 1 0
    //   88: aload_3
    //   89: athrow
    //   90: aload_2
    //   91: ifnull +9 -> 100
    //   94: aload_2
    //   95: invokeinterface 68 1 0
    //   100: iconst_1
    //   101: ireturn
    //   102: astore 9
    //   104: iload 8
    //   106: ireturn
    //   107: astore 4
    //   109: goto -21 -> 88
    //   112: astore 6
    //   114: goto -14 -> 100
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	this	DatabaseHelper
    //   0	117	1	paramSQLiteDatabase	SQLiteDatabase
    //   1	94	2	localCursor	android.database.Cursor
    //   77	12	3	localObject	Object
    //   107	1	4	localException1	Exception
    //   6	24	5	arrayOfString	String[]
    //   112	1	6	localException2	Exception
    //   56	6	7	i	int
    //   62	43	8	j	int
    //   102	1	9	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   2	58	77	finally
    //   68	74	102	java/lang/Exception
    //   82	88	107	java/lang/Exception
    //   94	100	112	java/lang/Exception
  }
  
  private void insertFolder(SQLiteDatabase paramSQLiteDatabase, long paramLong, String paramString)
  {
    long l = System.currentTimeMillis();
    paramSQLiteDatabase.execSQL("INSERT INTO folder VALUES (" + paramLong + ", " + l + ", '" + paramString + "');");
  }
  
  private void insertSudoku(SQLiteDatabase paramSQLiteDatabase, SQLiteStatement paramSQLiteStatement, long paramLong1, long paramLong2, String paramString)
  {
    paramSQLiteStatement.bindLong(1, paramLong2);
    paramSQLiteStatement.bindLong(2, paramLong1);
    paramSQLiteStatement.bindLong(3, 0L);
    paramSQLiteStatement.bindLong(4, 1L);
    paramSQLiteStatement.bindLong(5, 0L);
    paramSQLiteStatement.bindNull(6);
    paramSQLiteStatement.bindString(7, paramString);
    paramSQLiteStatement.bindNull(8);
    paramSQLiteStatement.executeInsert();
  }
  
  private void loadAssets(String paramString, SQLiteDatabase paramSQLiteDatabase, int paramInt)
  {
    SQLiteStatement localSQLiteStatement = paramSQLiteDatabase.compileStatement(String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?)", new Object[] { "sudoku", "_id", "folder_id", "created", "state", "time", "last_played", "data", "puzzle_note" }));
    InputStream localInputStream = null;
    int i = getNewSudokuId(paramSQLiteDatabase);
    for (int j = 0;; j++)
    {
      try
      {
        localInputStream = this.mContext.getAssets().open(paramString);
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(localInputStream)));
        String  str = localBufferedReader.readLine();
        if (str == null) {
          if (localInputStream == null) {}
        }
      }
      catch (IOException localIOException2)
      {
        String str;
        long l1;
        long l2;
        localIOException2.printStackTrace();
        throw new RuntimeException(localIOException2.getMessage());
      }
      finally
      {
        if (localInputStream != null) {}
        try
        {
          localInputStream.close();
          throw ((Throwable)localObject);
        }
        catch (IOException localIOException1)
        {
          for (;;) {}
        }
      }
      try
      {
        localInputStream.close();
        return;
      }
      catch (IOException localIOException3) {}
      l1 = paramInt;
      l2 = i + j;
      insertSudoku(paramSQLiteDatabase, localSQLiteStatement, l1, l2, str);
    }
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE sudoku (_id INTEGER PRIMARY KEY,folder_id INTEGER,created INTEGER,state INTEGER,time INTEGER,last_played INTEGER,data Text,puzzle_note Text);");
      paramSQLiteDatabase.execSQL("CREATE TABLE folder (_id INTEGER PRIMARY KEY,created INTEGER,name TEXT);");
      insertFolder(paramSQLiteDatabase, 1L, this.mContext.getString(2131296381));
      loadAssets("easy", paramSQLiteDatabase, 1);
      insertFolder(paramSQLiteDatabase, 2L, this.mContext.getString(2131296380));
      loadAssets("medium", paramSQLiteDatabase, 2);
      insertFolder(paramSQLiteDatabase, 3L, this.mContext.getString(2131296379));
      loadAssets("hard", paramSQLiteDatabase, 3);
      insertFolder(paramSQLiteDatabase, 4L, this.mContext.getString(2131296392));
      loadAssets("very_hard", paramSQLiteDatabase, 4);
      createIndexes(paramSQLiteDatabase);
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Log.i("DatabaseHelper", "Upgrading database from version " + paramInt1 + " to " + paramInt2 + ".");
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\db\DatabaseHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */