package com.benreily.zzzonked.repositories;

import com.benreily.zzzonked.model.pojo.DribbbleShot;

import java.util.List;

import io.reactivex.Observable;

public interface DatabaseRepository {

    Observable<List<DribbbleShot>> loadShotsFromDb() throws EmptyDbException;

    void saveShotsToDb();

    class EmptyDbException extends Throwable {
        private static String MESSAGE_DB_EMPTY = "There are no shots in the database";

        public EmptyDbException() {
            this(MESSAGE_DB_EMPTY);
        }

        private EmptyDbException(String message) {
            super(message);
        }
    }
}
