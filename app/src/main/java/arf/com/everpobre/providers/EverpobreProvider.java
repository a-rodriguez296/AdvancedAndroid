package arf.com.everpobre.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import arf.com.everpobre.model.db.DBConstants;
import arf.com.everpobre.model.db.DBHelper;

/**
 * Created by arodriguez on 10/6/15.
 * Un Content provider es una herramienta que genera un API para yo acceder
 * a los datos de mi DB
 */
public class EverpobreProvider extends ContentProvider {

    public static final String EVERPOBRE_PROVIDER = "arf.com.everpobre.provider";

    // content://io.keepcoding.everpobre.provider/notebooks
    public static final Uri NOTEBOOKS_URI = Uri.parse("content://" + EVERPOBRE_PROVIDER + "/notebooks");

    // content://io.keepcoding.everpobre.provider/notes
    public static final Uri NOTES_URI = Uri.parse("content://" + EVERPOBRE_PROVIDER + "/notes");


    // Create the constants used to differentiate between the different URI requests.
    private static final int ALL_NOTEBOOKS = 1;

    private static final int SINGLE_NOTEBOOK = 2;
    private static final int ALL_NOTES = 3;
    private static final int SINGLE_NOTE = 4;

    private static final UriMatcher uriMatcher;

    // Populate the UriMatcher object, where a URI ending in elements will correspond to a request for all items, and elements/[rowID] represents a single row.

    //Contructor estático
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(EVERPOBRE_PROVIDER, "notebooks", ALL_NOTEBOOKS);
        uriMatcher.addURI(EVERPOBRE_PROVIDER, "notebooks/#", SINGLE_NOTEBOOK);
        uriMatcher.addURI(EVERPOBRE_PROVIDER, "notes", ALL_NOTES);
        uriMatcher.addURI(EVERPOBRE_PROVIDER, "notes/#", SINGLE_NOTE);
    }

    private DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());

        return false;
    }

    // Implement this to handle requests for the MIME type of the data at the given URI.
    // single item: vnd.android.cursor.item/vnd.<companyname>.<contenttype>
    // all items: vnd.android.cursor.dir/vnd.<companyname>.<contenttype>
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_NOTEBOOKS:
                return "vnd.android.cursor.dir/vnd.arf.notebook";
            case SINGLE_NOTEBOOK:
                return "vnd.android.cursor.item/vnd.arf.notebook";
            case ALL_NOTES:
                return "vnd.android.cursor.dir/vnd.arf.note";
            case SINGLE_NOTE:
                return "vnd.android.cursor.item/vnd.arf.note";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Open the database.

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Replace these with valid SQL statements if necessary.
        String groupBy = null;
        String having = null;
        // Use an SQLite Query Builder to simplify constructing the database query.

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(getTableName(uri));

        // If this is a row query, limit the result set to the passed in row.
        String rowID = null;
        switch (uriMatcher.match(uri)) {
            case SINGLE_NOTEBOOK :
                rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DBConstants.KEY_NOTEBOOK_ID + "=" + rowID);
                break;
            case SINGLE_NOTE:
                rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DBConstants.KEY_NOTE_ID + "=" + rowID);
                break;
            default: break;
        }

        // Specify the table on which to perform the query. This can // be a specific table or a join as required. queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);
        // Execute the query.
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        // Return the result Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);


        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // Open a read / write database to support the transaction.

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = getTableName(uri);

        // To add empty rows to your database by passing in an empty Content Values object
        // you must use the null column hack parameter to specify the name of the column that can be set to null.
        String nullColumnHack = null;
        // Insert the values into the table
        long id = db.insert(tableName, nullColumnHack, contentValues);
        // Construct and return the URI of the newly inserted row.
        if (id > -1) {
            // Construct and return the URI of the newly inserted row.
            Uri insertedUri = null;
            switch (uriMatcher.match(uri)) {
                case ALL_NOTEBOOKS:
                    insertedUri = ContentUris.withAppendedId(NOTEBOOKS_URI, id);
                    break;
                case SINGLE_NOTEBOOK :
                    insertedUri = ContentUris.withAppendedId(NOTEBOOKS_URI, id);
                    break;
                case ALL_NOTES:
                    insertedUri = ContentUris.withAppendedId(NOTES_URI, id);
                    break;
                case SINGLE_NOTE:
                    insertedUri = ContentUris.withAppendedId(NOTES_URI, id);
                    break;
                default: break;
            }

            // Notify any observers of the change in the data set.
            //Con esto se le notifica a los observadores interesados de los cambios
            getContext().getContentResolver().notifyChange(uri, null);
            getContext().getContentResolver().notifyChange(insertedUri, null);

            return insertedUri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        String rowID = null;

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_NOTEBOOK:
                rowID = uri.getPathSegments().get(1);
                selection = DBConstants.KEY_NOTEBOOK_ID + "=" + rowID;
                break;
            case SINGLE_NOTE:
                rowID = uri.getPathSegments().get(1);
                selection = DBConstants.KEY_NOTE_ID + "=" + rowID;
                break;
            default:
                break;
        }

        // Perform the deletion.
        int deleteCount = db.delete(tableName, selection, selectionArgs);
        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of deleted items.
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rowID = null;
        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_NOTEBOOK :
                rowID = uri.getPathSegments().get(1);
                selection = DBConstants.KEY_NOTEBOOK_ID + "=" + rowID;
                break;
            case SINGLE_NOTE :
                rowID = uri.getPathSegments().get(1);
                selection = DBConstants.KEY_NOTE_ID + "=" + rowID;
                break;
            default:
                break;
        }

        if (rowID == null) {
            return -1;
        }

        int updateCount = db.update(getTableName(uri), contentValues, selection, selectionArgs);

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;

    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case ALL_NOTEBOOKS:
                tableName = DBConstants.TABLE_NOTEBOOK;
                break;
            case SINGLE_NOTEBOOK :
                tableName = DBConstants.TABLE_NOTEBOOK;
                break;
            case ALL_NOTES:
                tableName = DBConstants.TABLE_NOTE;
                break;
            case SINGLE_NOTE:
                tableName = DBConstants.TABLE_NOTE;
                break;
            default: break;
        }
        return tableName;
    }
}
