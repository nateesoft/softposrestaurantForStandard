package com.softpos.pos.core.controller;

import java.util.List;

/**
 *
 * @author nathee
 * @param <T>
 */
public interface CrudDbInterface<T> {
    public boolean create(T t);
    public List<T> findAll();
    public T findById(String id);
    public boolean update(T t);
    public boolean delete(String id);
    public boolean deleteAll();
}
