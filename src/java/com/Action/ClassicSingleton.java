package com.Action;

import com.DAO.DaoClass;
import java.util.ResourceBundle;

public class ClassicSingleton {

    public static ClassicSingleton inst = null;
    DaoClass daoClass = new DaoClass();
    ResourceBundle rb = ResourceBundle.getBundle("dbConnection");

    public ClassicSingleton() {
        daoClass.Fun_Updat("SET SESSION query_cache_type = on");
        String fixMaxDbConnQry = "SET GLOBAL max_connections = " + rb.getString("max.Connections").trim() + "";
        daoClass.Fun_Updat(fixMaxDbConnQry);
        String fixCacheSizeQry = "SET GLOBAL query_cache_size = " + rb.getString("query.Cache.Size") + "";
        daoClass.Fun_Updat(fixCacheSizeQry);
    }

    public static ClassicSingleton getOneInst() {
        if (inst == null) {
            inst = new ClassicSingleton();
        }
        return inst;
    }
}
