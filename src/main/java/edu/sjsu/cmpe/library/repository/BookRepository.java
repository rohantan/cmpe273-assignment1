package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.LinkDto;

public class BookRepository implements BookRepositoryInterface {
	/** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
	private final ConcurrentHashMap<Long, Book> bookInMemoryMap;

	/** Never access this key directly; instead use generateISBNKey() */
	private long isbnKey;
	private long reviewid;
	private int authorid;

	public BookRepository(ConcurrentHashMap<Long, Book> bookMap) {
		checkNotNull(bookMap, "bookMap must not be null for BookRepository");
		bookInMemoryMap = bookMap;
		isbnKey = 0;
		reviewid=0;
		authorid=0;
	}


	/**
	 * This should be called if and only if you are adding new books to the
	 * repository.
	 * 
	 * @return a new incremental ISBN number
	 */
	private final Long generateISBNKey() {
		// increment existing isbnKey and return the new value
		return Long.valueOf(++isbnKey);
	}

	/**
	 * This should be called if and only if you are adding new review comment to the
	 * book.
	 * 
	 * @return a new incremental reviewid
	 */
	private final Long generatereviewid() {
		// increment existing isbnKey and return the new value
		return Long.valueOf(++reviewid);
	}

	/**
	 * This should be called if and only if you are adding new review comment to the
	 * book.
	 * 
	 * @return a new incremental reviewid
	 */
	private final int generateAuthorId() {
		// increment existing isbnKey and return the new value
		return Integer.valueOf(++authorid);
	}

	/**
	 * This will auto-generate unique ISBN for new books.
	 */
	@Override
	public Book saveBook(Book newBook) {
		/*Book createnewbook=new Book();
		return createnewbook;*/
		
		checkNotNull(newBook, "newBook instance must not be null");
		// Generate new ISBN
		Long isbn = generateISBNKey();
		newBook.setIsbn(isbn);

		Book createnewbook=new Book();
		ArrayList bookauthordetailscontainerlist=new ArrayList();

		createnewbook.setTitle(newBook.getTitle());
		createnewbook.setIsbn(newBook.getIsbn());
		createnewbook.setPublication_date(newBook.getPublication_date());
		createnewbook.setLanguage(newBook.getLanguage());
		createnewbook.setNum_pages(newBook.getNum_pages());
		createnewbook.setStatus(newBook.getStatus());
		createnewbook.setReviews(new ArrayList());

		for(int i=0;i<newBook.getAuthors().size();i++){
			authorid = generateAuthorId();
			/*Map authordetailsmap=new HashMap();
			authordetailsmap.put("id", authorid);
			authordetailsmap.put("name",newBook.getAuthors().get(i).toString());*/
			bookauthordetailscontainerlist.add(i, newBook.getAuthors().get(i));
//			bookauthordetailscontainerlist.add(i,authordetailsmap);
			System.out.println("bookauthordetailscontainerlist "+bookauthordetailscontainerlist.get(i));
		}
		System.out.println("bookauthordetailscontainerlist:: "+bookauthordetailscontainerlist.toString());
		createnewbook.setAuthors(bookauthordetailscontainerlist);

		bookInMemoryMap.putIfAbsent(isbn, createnewbook);
		System.out.println("bookInMemoryMap******** "+bookInMemoryMap.get(isbn).getAuthors().toString());
		return createnewbook;
	}

	/**
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
	 */
	@Override
	public Book getBookByISBN(Long isbn) {
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		System.out.println("######################## "+bookInMemoryMap.get(isbn).getAuthors().toString());
		return bookInMemoryMap.get(isbn);
	}

	@Override
	public Book updateBookByISBN(Long isbn,String status) {
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		Book book=new Book();
		book=bookInMemoryMap.get(isbn);
		book.setStatus(status);
		System.out.println("book status::::::::::::::::; "+book.getStatus());

		bookInMemoryMap.replace(isbn, book);
		return bookInMemoryMap.get(isbn);
	}

	@Override
	public Book deleteBookByISBN(Long isbn) {
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		Book book=bookInMemoryMap.remove(isbn);

		return book;
	}

	public Book createBookReviewByISBN(long isbn, Map bookreviewmap){
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		Book savedbook=new Book();

		savedbook=bookInMemoryMap.get(isbn);
		ArrayList reviewlist=new ArrayList();
		Map reviewcontainermap=new HashMap();

		System.out.println("reviewcontainermap:: "+reviewcontainermap.toString());

		if(null!=savedbook.getReviews()){
			for(int i=0;i<savedbook.getReviews().size();i++){
				reviewlist.add(savedbook.getReviews().get(i));
			}
		}
		System.out.println("bookreviewmap.get(rating))"+bookreviewmap.get("rating"));
		System.out.println("bookreviewmap.get(comment))"+bookreviewmap.get("comment"));

		reviewid = generatereviewid();
		reviewcontainermap.put("id", reviewid);
		reviewcontainermap.put("rating", bookreviewmap.get("rating"));
		reviewcontainermap.put("comment", bookreviewmap.get("comment"));

		reviewlist.add(reviewcontainermap);
		savedbook.setReviews(reviewlist);
		bookInMemoryMap.replace(isbn, savedbook);
		System.out.println("bookInMemoryMap:_"+bookInMemoryMap.get(isbn).getReviews().toString());

		return bookInMemoryMap.get(isbn);
	}

	public Map getBookReviewByISBN(Long isbn,int reviewid){
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		List savedbooklist=new ArrayList();
		Map bookreviewmap=new HashMap();
		Book savedbook=bookInMemoryMap.get(isbn);
		System.out.println("getBookReviewByISBN!!!");
		savedbooklist=savedbook.getReviews();
		if(!savedbooklist.isEmpty()){
			bookreviewmap.put("review",savedbooklist.get(reviewid-1));
		}
		List lst=new ArrayList();
		String location = "/books/"+isbn+"reviews/" +reviewid ;
		LinkDto ldto=new LinkDto("view-review", location, "GET");
		lst.add(ldto);		
		bookreviewmap.put("links",lst);
		System.out.println("@@@bookInMemoryMap.get(isbn).getReviews()@@@ "+bookInMemoryMap.get(isbn).getReviews().toString());
		System.out.println("##savedbook.toString()## "+savedbook.toString());

		return bookreviewmap;
	}

	public Map getAllBookReviewByISBN(Long isbn){
    	checkArgument(isbn > 0,
    			"ISBN was %s but expected greater than zero value", isbn);
    	List savedbooklist=new ArrayList();
    	Book savedbook=bookInMemoryMap.get(isbn);
    	savedbooklist=savedbook.getReviews();
    	Map reviewmap=new HashMap();
		reviewmap.put("reviews", savedbooklist);
		reviewmap.put("links",new ArrayList());
    	return reviewmap;
    }

    public Map getBookAuthorByISBN(Long isbn, int authorid){
//    	return new HashMap();
    	
    	checkArgument(isbn > 0,
    			"ISBN was %s but expected greater than zero value", isbn);
    	List savedbooklist=new ArrayList();
		Map bookauthormap=new HashMap();
		Book savedbook=bookInMemoryMap.get(isbn);
		System.out.println("getBookAuthorByISBN!!!");
		System.out.println("savedbook.getAuthors()"+savedbook.getAuthors());
		savedbooklist=savedbook.getAuthors();
		System.out.println("authorid"+authorid);
		System.out.println("savedbooklist0"+savedbooklist.get(0));
		System.out.println("savedbooklist1"+savedbooklist.get(1));
		if(!savedbooklist.isEmpty()){
			bookauthormap.put("author",savedbooklist.get(authorid-1));
		}
		String location = "/books/"+isbn+"/authors/" +authorid ;
		LinkDto ldto=new LinkDto("view-author", location, "GET");
		List lst=new ArrayList();
		lst.add(ldto);
		bookauthormap.put("links",lst);

    	return bookauthormap;
    }

    public Map getAllAuthorsOfBookByISBN(Long isbn){
//    	return new HashMap();
    	
    	checkArgument(isbn > 0,
    			"ISBN was %s but expected greater than zero value", isbn);
    	List savedbooklist=new ArrayList();
    	Book savedbook=bookInMemoryMap.get(isbn);
    	savedbooklist=savedbook.getAuthors();
    	Map authormap=new HashMap();
    	authormap.put("authors", savedbooklist);
    	authormap.put("links",new ArrayList());
    	return authormap;
    }

}
