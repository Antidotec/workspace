package com.evan.wj.controller;

import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Collection;
import com.evan.wj.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollectController {
    @Autowired
    CollectionService collectionService;

    @CrossOrigin
    @PostMapping("/api/isCollected")
    public boolean isCollected(@RequestBody Collection collection) throws Exception {
        return collectionService.isCollected(collection);
    }

    @CrossOrigin
    @PostMapping("/api/collect")
    public void Collect(@RequestBody Collection collection) throws Exception {
        collectionService.collect(collection);
    }

    @CrossOrigin
    @PostMapping("/api/disCollect")
    public void disCollect(@RequestBody Collection collection) throws Exception {
        collectionService.disCollect(collection);
    }

    @CrossOrigin
    @GetMapping("/api/collections")
    public List<Book> getCollections(@RequestParam("uid") int uid) throws Exception {
        return collectionService.getCollections(uid);
    }

    @CrossOrigin
    @GetMapping("/api/collectionList")
    public List<Collection> collectionList() throws Exception {
        return collectionService.collectionList();
    }
    @CrossOrigin
    @PostMapping("/api/delCollection")
    public void delUser(@RequestParam int id){
        collectionService.delCollection(id);
    }

}
