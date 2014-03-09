package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Book {
    private long isbn;
    private String title;
 // add more fields here
    private String publication_date;
	private String language;
    private String num_pages;
    private String status="available";
//    private Author[] authors;
	private List authors;
//    private String authorname;
    
    private List reviews;
//    private List reviews;
//    private Long reviewid;
//    private int reviewrating;
//    private String reviewcomments;
    
    public Book(String x){
    	//keep this blank...
    }
    public Book(){
    	//keep this blank...
    }

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    
    
    //
    
	public String getLanguage() {
		return language;
	}

	/*public Author[] getAuthors() {
		return authors;
	}
	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}*/
	public void setLanguage(String language) {
		this.language = language;
	}


	public String getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}

	public String getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(String num_pages) {
		this.num_pages = num_pages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/*public Author getoneAuthors(int id) {
		return this.authors[id];	
	}*/

	/*public Long getAuthorid() {
		return authorid;
	}

	public void setAuthorid(Long authorid) {
		this.authorid = authorid;
	}*/

	/*public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}*/
	
	/*public Map getReviews() {
		return reviews;
	}

	public void setReviews(Map reviews) {
		this.reviews = reviews;
	}*/

	/*public Long getReviewid() {
		return reviewid;
	}*/

	/*public List getReviews() {
		return reviews;
	}

	public void setReviews(List reviews) {
		this.reviews = reviews;
	}*/
	

	/*public void setReviewid(Long reviewid) {
		this.reviewid = reviewid;
	}*/

	public List getReviews() {
		return reviews;
	}

	public List getAuthors() {
		return authors;
	}
	public void setAuthors(List authors) {
		this.authors = authors;
	}
	public void setReviews(List reviews) {
		this.reviews = reviews;
	}

	/*public int getReviewrating() {
		return reviewrating;
	}

	public void setReviewrating(int reviewrating) {
		this.reviewrating = reviewrating;
	}

	public String getReviewcomments() {
		return reviewcomments;
	}

	public void setReviewcomments(String reviewcomments) {
		this.reviewcomments = reviewcomments;
	}*/
    
}
