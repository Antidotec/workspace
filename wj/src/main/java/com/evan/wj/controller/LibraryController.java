package com.evan.wj.controller;

import com.evan.wj.pojo.Book;
import com.evan.wj.service.BookService;

import com.evan.wj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    BookService bookService;

    @CrossOrigin
    @GetMapping("/api/books")
    public List<Book> list(@RequestParam("index") int index) throws Exception {
        return bookService.list(index);
    }

    @CrossOrigin
    @PostMapping("/api/books")
    public Book addOrUpdate(@RequestBody Book book) throws Exception {
        bookService.addOrUpdate(book);
        return book;
    }

    @CrossOrigin
    @PostMapping("/api/delBook")
    public void delete(@RequestBody Book book) throws Exception {
        bookService.deleteById(book.getId());
    }


    @CrossOrigin
    @GetMapping("/api/categories/{cid}/books")
    public List<Book> listByCategory(@PathVariable("cid") int cid, @RequestParam("index") int index) throws Exception {
        if (0 != cid) {
            return bookService.listByCategory(cid, index);
        } else {
            return list(index);
        }
    }

    @CrossOrigin
    @GetMapping("/api/search")
    public List<Book> searchResult(@RequestParam("keywords") String keywords,
                                   @RequestParam("index") int index,
                                   @RequestParam("cid") int cid) {
        // 关键词为空时查询出所有书籍
        if ("".equals(keywords)) {
            return bookService.list(index);
        } else {
            return bookService.Search(keywords, index, cid);
        }
    }

    @CrossOrigin
    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "D:/workspace/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @CrossOrigin
    @GetMapping("/api/getBook")
    public Book getBook(@RequestParam("id") int bid) {
        return bookService.getBook(bid);
    }

    @CrossOrigin
    @PostMapping("/api/isOnSale")
    public boolean isOnSale(@RequestParam("bid") int bid) {
        return bookService.isOnSale(bid);
    }

    @CrossOrigin
    @GetMapping("/api/getAllBooks")
    public List<Book> allBooks() {
        return bookService.allBooks();
    }

    @CrossOrigin
    @PostMapping("/api/bookOnSale")
    public void bookOnSale(@RequestParam("bid") int bid) {
        bookService.bookOnSale(bid);
    }

}

