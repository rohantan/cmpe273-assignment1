package edu.sjsu.cmpe.library.repository;

import java.util.List;
import java.util.Map;

import edu.sjsu.cmpe.library.domain.Book;

/**
 * Book repository interface.
 * 
 * What is repository pattern?
 * 
 * @see http://martinfowler.com/eaaCatalog/repository.html
 */
public interface BookRepositoryInterface {
    /**
     * Save a new book in the repository
     * 
     * @param newBook
     *            a book instance to be create in the repository
     * @return a newly created book instance with auto-generated ISBN
     */
    Book saveBook(Book newBook);

    /**
     * Retrieve an existing book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Book getBookByISBN(Long isbn);

    // TODO: add other operations here!
    
    /**
     * Delete an existing book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Book deleteBookByISBN(Long isbn);
    
    /**
     * Update an existing book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Book updateBookByISBN(Long isbn,String status);
    
    /**
     * Create a book review by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Book createBookReviewByISBN(long isbn, Map bookreviewmap);
    
    /**
     * View a book review by ISBN and reviewid
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Map getBookReviewByISBN(Long isbn,int reviewid);
    /*
    /**
     * View all book review by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Map getAllBookReviewByISBN(Long isbn);
    
    /**
     * View an author of the book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Map getBookAuthorByISBN(Long isbn, int authorid);
    
    /**
     * View all authors of the book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Map getAllAuthorsOfBookByISBN(Long isbn);
    
}
