package com.clement.fruit.dao;


import com.clement.fruit.entity.BookEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月16日  10:52
 * @Description:
 */
public interface BookDao {
    List<BookEntity> getAll(String name) throws Exception;

    boolean addBook(BookEntity books) throws Exception;

    boolean updateBook(BookEntity books) throws Exception;

    BookEntity getAllById(Integer id) throws Exception;

    boolean deleteBook(Integer id) throws Exception ;
}
