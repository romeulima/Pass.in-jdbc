package com.romeulima.passinjdbc.dao.factory;

import com.romeulima.passinjdbc.dao.EventDao;
import com.romeulima.passinjdbc.db.DB;
import com.romeulima.passinjdbc.impl.EventDaoJDBC;

public class DaoFactory {

    public static EventDao createEventDao() {
        return new EventDaoJDBC(DB.getConnection());
    }
}
