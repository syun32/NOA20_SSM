package com.example.ictproject;

import android.provider.BaseColumns;

public class DataBase {
    private DataBase(){}

    public static class RecipeEntry implements BaseColumns {
        public static final String DB_NAME = "SSM";
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONDIMENTS0 = "condiments0";
        public static final String COLUMN_NAME_CONDIMENTS1 = "condiments1";
        public static final String COLUMN_NAME_GRAM0 = "gram0";
        public static final String COLUMN_NAME_GRAM1 = "gram1";
    }

}
