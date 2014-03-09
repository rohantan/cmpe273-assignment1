package edu.sjsu.cmpe.library.api.resources;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    private static HashMap<Long, Book> new_book_entry = new HashMap<Long,Book>();
	private static long book_id=1;
	private static long author_id=1;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }

    @POST
    @Timed(name = "create-book")
    public Response createBook(Book book) {
	// Store the new book in the BookRepository so that we can retrieve it.
    	/*book.setIsbn(book_id);
    	new_book_entry.put(book_id, book);
    	book_id++;

    	for (int author=0;author<book.getAuthors().length;author++)
    	{
    	book.getAuthors()[author].id=author_id;
    	author_id++;

    	}*/
    	Book savedbook=bookRepository.saveBook(book);
    	
    	BookDto bookResponse = new BookDto();
    	LinksDto lnk=new LinksDto();
    	lnk.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
    	lnk.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
    	lnk.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
    	lnk.addLink(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews", "POST"));

    	return Response.status(201).entity(lnk).build();
    }
    
    @GET
    @Path("{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book savedbook = bookRepository.getBookByISBN(isbn.get());
	String location = "/books/" + savedbook.getIsbn();
	BookDto bookResponse = new BookDto(savedbook);
	List author_links=new ArrayList();
	int j=0;
	for(int i=0;i<bookResponse.getBook().getAuthors().size();i++){
		j=i+1;
		author_links.add(new LinkDto("view-author", location+"/authors/"+j,"GET"));
	}
	bookResponse.getBook().setAuthors(author_links);
	bookResponse.addLink(new LinkDto("view-book", location,"GET"));
	bookResponse.addLink(new LinkDto("update-book",	 location,"POST"));
	
	bookResponse.addLink(new LinkDto("delete-book", location,"DELETE"));
	bookResponse.addLink(new LinkDto("create-review", "/books/reviews/" +savedbook.getIsbn(),"POST"));
	if(!bookResponse.getBook().getReviews().isEmpty()){
		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/reviews/" +savedbook.getIsbn(),"GET"));
	}
    	
    	/*Book retrieveBook=new_book_entry.get(isbn);
    	retrieveBook.setReviews(new ArrayList());
    	BookDto bookResponse = new BookDto(retrieveBook);
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + retrieveBook.getIsbn(), "GET"));
    	bookResponse.addLink(new LinkDto("update-book","/books/" + retrieveBook.getIsbn(), "PUT"));
    	bookResponse.addLink(new LinkDto("delete-book","/books/" + retrieveBook.getIsbn(), "DELETE"));
    	bookResponse.addLink(new LinkDto("create-review","/books/" + retrieveBook.getIsbn() + "/reviews", "POST"));
    	if(!bookResponse.getBook().getReviews().isEmpty()){
    		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/reviews/" +retrieveBook.getIsbn(),"GET"));
    	}	*/
	return bookResponse;
    }
    
    //
    @DELETE
    @Path("{isbn}")
    @Timed(name= "delete-book")
    public Response deleteBook(@PathParam("isbn") LongParam isbn){
    	Book savedbook = bookRepository.getBookByISBN(isbn.get());
    	String location = "/books/" + savedbook.getIsbn();
    	bookRepository.deleteBookByISBN(isbn.get());
    	BookDto bookResponse = new BookDto(savedbook);
    	LinksDto links=new LinksDto();
    	links.addLink(new LinkDto("create-book", location, "POST"));
    	return Response.status(200).entity(links).build();
    }
    
    @PUT
    @Path("{isbn}")
    @Timed(name= "update-book")
    public Response updateBook(@PathParam("isbn") LongParam isbn,@QueryParam("status") String status){
    	Book savedbook = bookRepository.getBookByISBN(isbn.get());
    	String location = "/books/" + savedbook.getIsbn();
    	System.out.println("STATUS:::::::: "+status);
    	bookRepository.updateBookByISBN(isbn.get(),status);
    	BookDto bookResponse = new BookDto(savedbook);
    	LinksDto links=new LinksDto();
    	links.addLink(new LinkDto("view-book..", location, "GET"));
    	links.addLink(new LinkDto("update-book", location, "PUT"));
    	links.addLink(new LinkDto("delete-book", location, "DELETE"));
    	links.addLink(new LinkDto("create-review", "/books/reviews/" +savedbook.getIsbn(), "POST"));
    	if(!bookResponse.getBook().getReviews().isEmpty()){
    		links.addLink(new LinkDto("view-all-review", "/books/reviews/" +savedbook.getIsbn(), "GET"));
    	}
    	return Response.status(200).entity(links).build();
    }
    
    @POST
    @Path("{isbn}/reviews")
    @Timed(name= "create-review")
    public Response createReview(@PathParam("isbn") LongParam isbn,Map bookreviewmap){
    	System.out.println("in createReview$$$$$$$$$$$$$$$$$$$");
    	Book savedbook = bookRepository.createBookReviewByISBN(isbn.get(),bookreviewmap);
    	String location = "/books/reviews/" + savedbook.getReviews().size();
    	BookDto bookResponse = new BookDto();
    	List l=new ArrayList();
    	LinksDto linkvalues=new LinksDto();
    	linkvalues.addLink(new LinkDto("view-review", location, "GET"));
    	return Response.status(201).entity(linkvalues).build();
    }
    
    @GET
    @Path("{isbn}/reviews/{id}")
    @Timed(name= "view-review")
    public Map viewReview(@PathParam("isbn") LongParam isbn,@PathParam("id") int reviewid){
    	System.out.println("@@@@@in view-review@@@@@");
    	Map savedbookmap = bookRepository.getBookReviewByISBN(isbn.get(),reviewid);
    	System.out.println("in view-review:: savedbookmap:: "+savedbookmap.toString());
    	return savedbookmap;
    }
    
    @GET
    @Path("{isbn}/reviews")
    @Timed(name= "view-all-review")
    public Map viewAllReview(@PathParam("isbn") LongParam isbn){
    	Map savedbook = bookRepository.getAllBookReviewByISBN(isbn.get());
    	return savedbook;
    }
    
    @GET
    @Path("{isbn}/authors/{id}")
    @Timed(name= "view-author")
    public Map viewAuthor(@PathParam("isbn") LongParam isbn,@PathParam("id") int id){
    	Map savedauthormap = bookRepository.getBookAuthorByISBN(isbn.get(),id);
    	return savedauthormap;
    	/*int i=0;
    	Book retrieveBook = new_book_entry.get(isbn);
    	while (retrieveBook.getoneAuthors((int)i).id!=id)
    	{
    	i++;
    	}
    	AuthorDto authorResponse = new AuthorDto(retrieveBook.getoneAuthors((int)i));
    	authorResponse.addLink(new LinkDto("view-author", "/books/" + retrieveBook.getIsbn() + "/authors/" + retrieveBook.getoneAuthors((int)i).id, "GET"));
    	return Response.ok(authorResponse).build();*/
    }
    
    @GET
    @Path("{isbn}/authors")
    @Timed(name= "view-all-author")
    public Map viewAllAuthor(@PathParam("isbn") LongParam isbn){
    	Map savedbook = bookRepository.getAllAuthorsOfBookByISBN(isbn.get());
    	return savedbook;
    	/*Book retrieveBook = new_book_entry.get(isbn);
    	AuthorsDto authorResponse = new AuthorsDto(retrieveBook.getAuthors());

    	return authorResponse;*/
    }
    
}

