package com.example.getitdone.database;

public class EventDbSchema {
    public static final class EventTable {
        public static final String NAME = "events";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String FAVORITED = "favorited";
            public static final String PICTURE = "picture";
            public static final String TASK1 = "task1";
            public static final String TASK2 = "task2";
            public static final String TASK3 = "task3";
        }
    }
}
