package edu.sjsu.cmpe.library.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Book;

@JsonPropertyOrder(alphabetic = true)
public class BookDto extends LinksDto {
    private Book book;
    private List review;

    /**
     * @param book
     */
    public BookDto(Book book) {
	super();
	this.book = book;
    }
    
    public BookDto(){
    	
    }
    
    public BookDto(List review) {
//    	super();
    	this.review = review;
        }

    /**
     * @return the book
     */
    public Book getBook() {
	return book;
    }

    /**
     * @param book
     *            the book to set
     */
    public void setBook(Book book) {
	this.book = book;
    }

	public List getReview() {
		return review;
	}

	public void setReview(List review) {
		this.review = review;
	}
    
    
}
