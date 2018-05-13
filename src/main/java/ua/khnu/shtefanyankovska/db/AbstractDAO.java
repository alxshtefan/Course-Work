package ua.khnu.shtefanyankovska.db;

import ua.khnu.shtefanyankovska.entity.Entity;
import ua.khnu.shtefanyankovska.util.MyException;

import java.util.List;

public interface AbstractDAO<T extends Entity> {

	public List<T> findAll() throws MyException;

	public T findEntityById(int id) throws MyException;

	public boolean delete(int id) throws MyException;

	public boolean delete(T entity) throws MyException;

	public boolean create(T entity) throws MyException;

	public boolean update(T entity) throws MyException;

}
