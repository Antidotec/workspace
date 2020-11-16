package com.evan.wj.dao;

import com.evan.wj.pojo.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionDAO extends JpaRepository<Collection, Integer> {
    public Collection findCollectionByUidAndBid(int uid, int bid);

    public void deleteCollectionByUidAndBid(int uid, int bid);
    public List<Collection> findAllByUid(int uid);
}
