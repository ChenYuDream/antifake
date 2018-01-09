package com.sinosoft.antifake.ibatis.dao;


/**
 * Data Access Object (DAO) interface.   This is an interface
 * used to tag our DAO classes and to provide common methods to all DAOs. 
 */
public interface DAO {
    static final String FIND_BY_AND = "FIND_BY_AND";
    static final String FIND_BY_OR = "FIND_BY_OR";
    static final String FIND_BY_LIKE = "FIND_BY_LIKE";
    static final String ORDER_BY = "ORDER_BY";
}