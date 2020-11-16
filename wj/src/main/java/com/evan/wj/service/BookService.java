package com.evan.wj.service;

import com.evan.wj.dao.BookDAO;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;

    public List<Book> list(int index) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Book> books = bookDAO.findAll(sort);
        return filter(books, index);
    }

    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    public List<Book> listByCategory(int cid, int index) {
        Category category = categoryService.get(cid);
        return filter(bookDAO.findAllByCategory(category), index);
    }

    public List<Book> Search(String keywords, int index, int cid) {
        List<Book> books = bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
        if (cid != 0)
            books.removeIf(book -> book.getCategory().getId() != cid);
        return filter(books, index);
    }

    public Book getBook(int id) {
        return bookDAO.findBookById(id);
    }

    public List<Book> filter(List<Book> books, int index) {
        if (index == 0)
            books.removeIf(Book::isSale);
        else if (index == 1)
            books.removeIf(book -> !book.isSale());
        return books;
    }

    public boolean isOnSale(int bid) {
        return bookDAO.findBookById(bid).isSale();
    }

    public List<Book> allBooks() {
        return bookDAO.findAll();
    }

    public void bookOnSale(int bid){
        Book book = bookDAO.findBookById(bid);
        System.out.println(book.getTitle());
        book.setSale(true);
        bookDAO.save(book);
    }
}

