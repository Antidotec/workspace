package com.evan.wj.service;

import com.evan.wj.dao.BookDAO;
import com.evan.wj.dao.CollectionDAO;
import com.evan.wj.dao.UserDAO;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Collection;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionDAO collectionDAO;

    @Autowired
    BookDAO bookDAO;
    @Autowired
    UserDAO userDAO;

    public boolean isCollected(Collection collection) {
        Collection c = collectionDAO.findCollectionByUidAndBid(collection.getUid(), collection.getBid());
        return c != null;
    }

    public void collect(Collection collection) {
        collectionDAO.save(collection);
    }

    @Transactional
    public void disCollect(Collection collection) {
        collectionDAO.deleteCollectionByUidAndBid(collection.getUid(), collection.getBid());
    }

    public List<Book> getCollections(int uid) {
        List<Collection> cc = collectionDAO.findAllByUid(uid);
        List<Book> b = new ArrayList<Book>();
        for (Collection c : cc)
            b.add(bookDAO.findBookById(c.getBid()));
        return b;
    }

    public List<Collection> collectionList() {
        List<Collection> cc = collectionDAO.findAll();
        for (Collection c : cc) {
            Book b = bookDAO.findBookById(c.getBid());
            User u = userDAO.findUserById(c.getUid());
            c.setCover(b.getCover());
            c.setTitle(b.getTitle());
            c.setUsername(u.getUsername());
        }
        return cc;
    }
    public void delCollection(int id){
        collectionDAO.deleteById(id);
    }
}
