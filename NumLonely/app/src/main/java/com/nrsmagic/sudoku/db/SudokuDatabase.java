package com.nrsmagic.sudoku.db;

import android.app.backup.BackupManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.FolderInfo;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.gui.SudokuListFilter;

public class SudokuDatabase
{
  public static final String DATABASE_NAME = "opensudoku";
  public static final String FOLDER_TABLE_NAME = "folder";
  private static final String INBOX_FOLDER_NAME = "Inbox";
  public static final String SUDOKU_TABLE_NAME = "sudoku";
  private BackupManager mBackupManager;
  private SQLiteStatement mInsertSudokuStatement;
  private DatabaseHelper mOpenHelper;
  
  public SudokuDatabase(Context paramContext)
  {
    this.mOpenHelper = new DatabaseHelper(paramContext);
    this.mBackupManager = new BackupManager(paramContext);
  }
  
  public void beginTransaction()
  {
    this.mOpenHelper.getWritableDatabase().beginTransaction();
  }
  
  public void close()
  {
    if (this.mInsertSudokuStatement != null) {
      this.mInsertSudokuStatement.close();
    }
    this.mOpenHelper.close();
  }
  
  public void deleteFolder(long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getWritableDatabase();
    localSQLiteDatabase.delete("sudoku", "folder_id=" + paramLong, null);
    localSQLiteDatabase.delete("folder", "_id=" + paramLong, null);
    this.mBackupManager.dataChanged();
  }
  
  public void deleteSudoku(long paramLong)
  {
    this.mOpenHelper.getWritableDatabase().delete("sudoku", "_id=" + paramLong, null);
    this.mBackupManager.dataChanged();
  }
  
  public void endTransaction()
  {
    this.mOpenHelper.getWritableDatabase().endTransaction();
  }
  
  public Cursor exportFolder(long paramLong)
  {
    String str = "select f._id as folder_id, f.name as folder_name, f.created as folder_created, s.created, s.state, s.time, s.last_played, s.data, s.puzzle_note from folder f left outer join sudoku s on f._id = s.folder_id";
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getReadableDatabase();
    if (paramLong != -1L) {
      str = str + " where f._id = ?";
    }
    String[] arrayOfString;
    if (paramLong != -1L)
    {
      arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
    }
    for (;;)
    {
      return localSQLiteDatabase.rawQuery(str, arrayOfString);
      arrayOfString = null;
    }
  }
  
  public Cursor exportSudoku(long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    return localSQLiteDatabase.rawQuery("select f._id as folder_id, f.name as folder_name, f.created as folder_created, s.created, s.state, s.time, s.last_played, s.data, s.puzzle_note from sudoku s inner join folder f on s.folder_id = f._id where s._id = ?", arrayOfString);
  }
  
  public FolderInfo findFolder(String paramString)
  {
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables("folder");
    localSQLiteQueryBuilder.appendWhere("name = ?");
    Cursor localCursor = null;
    try
    {
      localCursor = localSQLiteQueryBuilder.query(this.mOpenHelper.getReadableDatabase(), null, null, new String[] { paramString }, null, null, null);
      if (localCursor.moveToFirst())
      {
        long l = localCursor.getLong(localCursor.getColumnIndex("_id"));
        String str = localCursor.getString(localCursor.getColumnIndex("name"));
        FolderInfo localFolderInfo = new FolderInfo();
        localFolderInfo.id = l;
        localFolderInfo.name = str;
        return localFolderInfo;
      }
      FolderInfo localFolderInfo = null;
      return null;
    }
    finally
    {
      if (localCursor != null) {
        localCursor.close();
      }
    }
  }
  
  public FolderInfo getFolderInfo(long paramLong)
  {
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables("folder");
    localSQLiteQueryBuilder.appendWhere("_id=" + paramLong);
    Cursor localCursor = null;
    try
    {
      localCursor = localSQLiteQueryBuilder.query(this.mOpenHelper.getReadableDatabase(), null, null, null, null, null, null);
      if (localCursor.moveToFirst())
      {
        long l = localCursor.getLong(localCursor.getColumnIndex("_id"));
        String str = localCursor.getString(localCursor.getColumnIndex("name"));
        FolderInfo localFolderInfo = new FolderInfo();
        localFolderInfo.id = l;
        localFolderInfo.name = str;
        return localFolderInfo;
      }
      FolderInfo localFolderInfo = null;
      return null;
    }
    finally
    {
      if (localCursor != null) {
        localCursor.close();
      }
    }
  }
  
  public FolderInfo getFolderInfoFull(long paramLong)
  {
    Cursor localObject1 = null;
    for (;;)
    {
      try
      {
        Cursor localCursor = this.mOpenHelper.getReadableDatabase().rawQuery("select folder._id as _id, folder.name as name, sudoku.state as state, count(sudoku.state) as count from folder left join sudoku on folder._id = sudoku.folder_id where folder._id = " + paramLong + " group by sudoku.state", null);
        localObject1 = localCursor;
        localObject4 = null;
      }
      finally
      {
        boolean bool;
        long l;
        String str;
        int i;
        int j;
        if (localObject1 != null) {
          ((Cursor)localObject1).close();
        }
      }
      try
      {
        bool = ((Cursor)localObject1).moveToNext();
        if (!bool)
        {
          if (localObject1 != null) {
            ((Cursor)localObject1).close();
          }
          return (FolderInfo)localObject4;
        }
        l = ((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("_id"));
        str = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("name"));
        i = ((Cursor)localObject1).getInt(((Cursor)localObject1).getColumnIndex("state"));
        j = ((Cursor)localObject1).getInt(((Cursor)localObject1).getColumnIndex("count"));
        if (localObject4 != null) {
          break label230;
        }
        localObject5 = new FolderInfo(l, str);
      }
      finally
      {
        continue;
        localObject5 = localObject4;
        continue;
      }
      ((FolderInfo)localObject5).puzzleCount = (j + ((FolderInfo)localObject5).puzzleCount);
      if (i == 2) {
        ((FolderInfo)localObject5).solvedCount = (j + ((FolderInfo)localObject5).solvedCount);
      }
      if (i == 0) {
        ((FolderInfo)localObject5).playingCount = (j + ((FolderInfo)localObject5).playingCount);
      }
      localObject4 = localObject5;
    }
  }
  
  public Cursor getFolderList()
  {
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables("folder");
    return localSQLiteQueryBuilder.query(this.mOpenHelper.getReadableDatabase(), null, null, null, null, null, "created ASC");
  }
  
  public FolderInfo getInboxFolder()
  {
    FolderInfo localFolderInfo = findFolder("Inbox");
    if (localFolderInfo != null) {
      localFolderInfo = insertFolder("Inbox", Long.valueOf(System.currentTimeMillis()));
    }
    return localFolderInfo;
  }
  
  public SudokuGame getSudoku(long paramLong)
  {
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables("sudoku");
    localSQLiteQueryBuilder.appendWhere("_id=" + paramLong);
    Cursor localCursor = null;
    try
    {
      localCursor = localSQLiteQueryBuilder.query(this.mOpenHelper.getReadableDatabase(), null, null, null, null, null, null);
      boolean bool = localCursor.moveToFirst();
      Object localObject3 = null;
      long l1;
      long l2;
      String str1;
      long l3;
      int i;
      long l4;
      String str2;
      SudokuGame localSudokuGame;
      if (bool)
      {
        l1 = localCursor.getLong(localCursor.getColumnIndex("_id"));
        l2 = localCursor.getLong(localCursor.getColumnIndex("created"));
        str1 = localCursor.getString(localCursor.getColumnIndex("data"));
        l3 = localCursor.getLong(localCursor.getColumnIndex("last_played"));
        i = localCursor.getInt(localCursor.getColumnIndex("state"));
        l4 = localCursor.getLong(localCursor.getColumnIndex("time"));
        str2 = localCursor.getString(localCursor.getColumnIndex("puzzle_note"));
        localSudokuGame = new SudokuGame();
      }
      if (localCursor == null) {
        break label293;
      }
    }
    finally
    {
      try
      {
        localSudokuGame.setId(l1);
        localSudokuGame.setCreated(l2);
        localSudokuGame.setCells(CellCollection.deserialize(str1));
        localSudokuGame.setLastPlayed(l3);
        localSudokuGame.setState(i);
        localSudokuGame.setTime(l4);
        localSudokuGame.setNote(str2);
        localObject3 = localSudokuGame;
        if (localCursor != null) {
          localCursor.close();
        }
        return (SudokuGame)localObject3;
      }
      finally {}
      localObject1 = finally;
    }
    localCursor.close();
    label293:
    throw ((Throwable)localObject1);
  }
  
  public Cursor getSudokuList(long paramLong, SudokuListFilter paramSudokuListFilter)
  {
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables("sudoku");
    localSQLiteQueryBuilder.appendWhere("folder_id=" + paramLong);
    if (paramSudokuListFilter != null)
    {
      if (!paramSudokuListFilter.showStateCompleted) {
        localSQLiteQueryBuilder.appendWhere(" and state!=2");
      }
      if (!paramSudokuListFilter.showStateNotStarted) {
        localSQLiteQueryBuilder.appendWhere(" and state!=1");
      }
      if (!paramSudokuListFilter.showStatePlaying) {
        localSQLiteQueryBuilder.appendWhere(" and state!=0");
      }
    }
    return localSQLiteQueryBuilder.query(this.mOpenHelper.getReadableDatabase(), null, null, null, null, null, "created DESC");
  }
  
  public long importSudoku(long paramLong, SudokuImportParams paramSudokuImportParams)
    throws SudokuInvalidFormatException
  {
    if (paramSudokuImportParams.data == null) {
      throw new SudokuInvalidFormatException(paramSudokuImportParams.data);
    }
    if ((!CellCollection.isValid(paramSudokuImportParams.data, CellCollection.DATA_VERSION_PLAIN)) && (!CellCollection.isValid(paramSudokuImportParams.data, CellCollection.DATA_VERSION_1))) {
      throw new SudokuInvalidFormatException(paramSudokuImportParams.data);
    }
    if (this.mInsertSudokuStatement == null) {
      this.mInsertSudokuStatement = this.mOpenHelper.getWritableDatabase().compileStatement("insert into sudoku (folder_id, created, state, time, last_played, data, puzzle_note) values (?, ?, ?, ?, ?, ?, ?)");
    }
    this.mInsertSudokuStatement.bindLong(1, paramLong);
    this.mInsertSudokuStatement.bindLong(2, paramSudokuImportParams.created);
    this.mInsertSudokuStatement.bindLong(3, paramSudokuImportParams.state);
    this.mInsertSudokuStatement.bindLong(4, paramSudokuImportParams.time);
    this.mInsertSudokuStatement.bindLong(5, paramSudokuImportParams.lastPlayed);
    this.mInsertSudokuStatement.bindString(6, paramSudokuImportParams.data);
    if (paramSudokuImportParams.note == null) {
      this.mInsertSudokuStatement.bindNull(7);
    }
    for (;;)
    {
      long l = this.mInsertSudokuStatement.executeInsert();
      if (l <= 0L) {
        break;
      }
      this.mBackupManager.dataChanged();
      return l;
      this.mInsertSudokuStatement.bindString(7, paramSudokuImportParams.note);
    }
    throw new SQLException("Failed to insert sudoku.");
  }
  
  public FolderInfo insertFolder(String paramString, Long paramLong)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("created", paramLong);
    localContentValues.put("name", paramString);
    long l = this.mOpenHelper.getWritableDatabase().insert("folder", "_id", localContentValues);
    if (l > 0L)
    {
      FolderInfo localFolderInfo = new FolderInfo();
      localFolderInfo.id = l;
      localFolderInfo.name = paramString;
      this.mBackupManager.dataChanged();
      return localFolderInfo;
    }
    throw new SQLException(String.format("Failed to insert folder '%s'.", new Object[] { paramString }));
  }
  
  public long insertSudoku(long paramLong, SudokuGame paramSudokuGame)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("data", paramSudokuGame.getCells().serialize());
    localContentValues.put("created", Long.valueOf(paramSudokuGame.getCreated()));
    localContentValues.put("last_played", Long.valueOf(paramSudokuGame.getLastPlayed()));
    localContentValues.put("state", Integer.valueOf(paramSudokuGame.getState()));
    localContentValues.put("time", Long.valueOf(paramSudokuGame.getTime()));
    localContentValues.put("puzzle_note", paramSudokuGame.getNote());
    localContentValues.put("folder_id", Long.valueOf(paramLong));
    long l = localSQLiteDatabase.insert("sudoku", "name", localContentValues);
    if (l > 0L)
    {
      this.mBackupManager.dataChanged();
      return l;
    }
    throw new SQLException("Failed to insert sudoku.");
  }
  
  public void setTransactionSuccessful()
  {
    this.mOpenHelper.getWritableDatabase().setTransactionSuccessful();
    this.mBackupManager.dataChanged();
  }
  
  public void updateFolder(long paramLong, String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("name", paramString);
    this.mOpenHelper.getWritableDatabase().update("folder", localContentValues, "_id=" + paramLong, null);
    this.mBackupManager.dataChanged();
  }
  
  public void updateSudoku(SudokuGame paramSudokuGame)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("data", paramSudokuGame.getCells().serialize());
    localContentValues.put("last_played", Long.valueOf(paramSudokuGame.getLastPlayed()));
    localContentValues.put("state", Integer.valueOf(paramSudokuGame.getState()));
    localContentValues.put("time", Long.valueOf(paramSudokuGame.getTime()));
    localContentValues.put("puzzle_note", paramSudokuGame.getNote());
    this.mOpenHelper.getWritableDatabase().update("sudoku", localContentValues, "_id=" + paramSudokuGame.getId(), null);
    this.mBackupManager.dataChanged();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\db\SudokuDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */