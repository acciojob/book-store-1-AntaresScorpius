package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("books")
@RestController
public class BookController {

    private List<Book> bookList;
    private int id = 1;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
//        Book b = new Book();
//        b.setName(book.);
        book.setId(this.id);
        this.id++;
        bookList.add(book);
        System.out.println("Post Request");
        System.out.println(book.getName());
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    @GetMapping("/get-book-by-id/")
    public ResponseEntity<Book> getBookById(@RequestParam ("id")Integer id){
        //System.out.println("Get Book by id: " +id);
        for (int i = 0; i < bookList.size(); i++){
           // System.out.println("Current Id: " + bookList.get(i).getId());
            if (bookList.get(i).getId() == id){
                System.out.println("Match FOund");
                return new ResponseEntity<Book>(bookList.get(i), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete-book-by-id/")
    public ResponseEntity<String> deleteBookById(@RequestParam ("id")Integer id){
        System.out.println("Delete id:" +id);
        for (int i = 0; i < bookList.size(); i++){
            if (bookList.get(i).getId() == id){
                bookList.remove(i);
                return new ResponseEntity<>("Removed",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Not Such Element",HttpStatus.OK);
    }
    @DeleteMapping("/delete-all-books/")
    public ResponseEntity<String> deleteAllBooks(){
        bookList = new ArrayList<>();
        this.id = 1;
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
    @GetMapping("/get-all-books/")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
    @GetMapping("/get-books-by-author/")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String authorName){
        System.out.println("Get Books By Author");
        List<Book> ans = new ArrayList<>();
        for (Book b: bookList){
            if (b.getAuthor().equals(authorName)){
                ans.add(b);
            }
        }
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
    @GetMapping("/get-books-by-genre/")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre") String genre){
        System.out.println("Get Books By genre");
        List<Book> ans = new ArrayList<>();
        for (Book b: bookList){
            if (b.getGenre().equals(genre)){
                ans.add(b);
            }
        }
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()

    // get request /get-all-books
    // getAllBooks()

    // delete request /delete-all-books
    // deleteAllBooks()

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
}
