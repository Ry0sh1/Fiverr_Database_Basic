package Database_Managment;

public class Column {

    public final String columnName;
    public final int dataType;
    public final boolean primaryKey;
    public final boolean foreignKey;
    public final String foreignTableName;
    public final String foreignColumnName;
    public final boolean notNull;
    public final boolean autoIncrement;

    public Column(String columnName, int dataType, boolean primaryKey, boolean foreignKey, String foreignTableName, String foreignColumnName, boolean notNull, boolean autoIncrement) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.primaryKey = primaryKey;
        this.foreignKey = foreignKey;
        this.foreignTableName = foreignTableName;
        this.foreignColumnName = foreignColumnName;
        this.notNull = notNull;
        this.autoIncrement = autoIncrement;
    }

}
