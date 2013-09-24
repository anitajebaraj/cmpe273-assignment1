package edu.sjsu.cmpe.library.api.resources;

import javax.swing.Spring;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.FileNotFoundException;
import java.util.*;

import com.google.common.base.Optional;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.BookDetails;
import edu.sjsu.cmpe.library.domain.ErrorMessages;
import edu.sjsu.cmpe.library.domain.Reviews;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;


@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class BookResource {
	BookDetails bookMap = new BookDetails();
	static long newIsbn=1;
	//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
    public BookResource() {
    	

   }
    
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")

public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {   

    	Book book = new Book();
    	Author author=new Author();
    	
    	book.setIsbn(isbn.get());
    	HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
    	HashMap<Integer,ArrayList> bookAuthorDetails=new HashMap<Integer,ArrayList>();
    	HashMap<Integer,ArrayList> bookReviewDetails=new HashMap<Integer,ArrayList>();
      	bookMapDetails=bookMap.getBookHashMap();
      	bookAuthorDetails=bookMap.getAuthorHashMap();
      	bookReviewDetails=bookMap.getReviewHashMap();
    	System.out.print("this is the hashmap"+bookMapDetails);
    	String title="",numOfPages,status,language,publicationDate;

    	int reqIsbn;
  
    	reqIsbn=(int)(long)book.getIsbn();
    	title=(String) bookMapDetails.get(reqIsbn).get(0);//0 is value from arraylist
    	numOfPages=(String)bookMapDetails.get(reqIsbn).get(3);
    	status=(String)bookMapDetails.get(reqIsbn).get(4);
    	//rating=(String)bookMapDetails.get(reqIsbn).get(5);
    	language=(String)bookMapDetails.get(reqIsbn).get(2);
    	publicationDate=(String)bookMapDetails.get(reqIsbn).get(1);
    	book.setTitle(title);
    	book.setNumOfPages(numOfPages);
    	book.setStatus(status);
    	book.setPublicationDate(publicationDate);
    	book.setLanguage(language);
    	book.setIsbn(reqIsbn);
    	ArrayList<Author> authorList=new ArrayList<Author>();
    	authorList=bookAuthorDetails.get(reqIsbn);
    	System.out.print("authorList=="+authorList);
    	book.setAuthors(authorList);
    	
    	System.out.print("authorList book.getAuthors()=="+book.getAuthors());
    	
    	BookDto bookResponse = new BookDto(book);
    		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
    		bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
    		bookResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
    		bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn() +"/reviews", "POST"));
    		//System.out.println("bookReviewDetails.get(book.getIsbn()).size()=="+bookReviewDetails.get(book.getIsbn()-1).size());
    		ArrayList<Author> reviewList=new ArrayList<Author>();
    		reviewList=bookReviewDetails.get(reqIsbn);
    		if(!reviewList.isEmpty())
        		bookResponse.addLink(new LinkDto("view-all-reviews","/books/" + book.getIsbn() +"/reviews", "GET"));
	
    	System.out.println("bookResponse=="+bookResponse);
    	return bookResponse;
    }
    //create book
    @Timed(name = "create-book")
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response createbook(Book book,Author author) {
    	if(book.getPublicationDate()!="" && book.getTitle()!="")
    	{
    	HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
    	HashMap<Integer,ArrayList> bookReviewDetails=new HashMap<Integer,ArrayList>();
    	HashMap<Integer,ArrayList> bookReviewRatingDetails=new HashMap<Integer,ArrayList>();
    	ArrayList<Author> authorList=new ArrayList<Author>();
    	bookMapDetails=bookMap.getBookHashMap();
      //for review
      		bookReviewDetails=bookMap.getReviewHashMap();
      		
      		//for rating
      		bookReviewRatingDetails=bookMap.getReviewRatingHashMap();
      		
      		//for author
      		HashMap<Integer,ArrayList> bookAuthorDetails=new HashMap<Integer,ArrayList>();
			bookAuthorDetails=bookMap.getAuthorHashMap();
      		long authorId;
      		int newIsbn=(int)(long)bookMap.getNewIsbn();
      		
      		System.out.print("newIsbn"+newIsbn);
      	authorList=book.getAuthors();
      	String authors[] = new String[authorList.size()];
      	System.out.print("\n correct till here");
      	for(int listLength=0;listLength<authorList.size();listLength++)
      	{
      		String authorName=authorList.get(listLength).getName();
      		authorList.get(listLength).setAuthorID(listLength+1);
      		System.out.print("\nauthorId==="+authorList.get(listLength).getAuthorID());
      		authors[listLength]=authorList.get(listLength).getName();
      		//bookMap.createAuthorResource(bookAuthorDetails, newIsbn, authors);	
      	}
      	book.setAuthors(authorList);
      	bookMap.createAuthorResource(bookAuthorDetails, newIsbn, authors);	
      	
      	bookMap.createBookMap(bookMapDetails, newIsbn, book.getTitle(),book.getPublicationDate(),book.getLanguage(), book.getNumOfPages(), book.getStatus(), "") ; 	
      	book.setIsbn(newIsbn);
    	
      	ArrayList<String> dummyList=new ArrayList<String>();
    	ArrayList<String> dummyRatingList=new ArrayList<String>();
      	System.out.println("dummyList.size()==="+dummyList.size());
      	bookReviewDetails.put(newIsbn,dummyList);
      	bookMap.setReviewHashMap(bookReviewDetails);
      	
      	bookReviewRatingDetails.put(newIsbn,dummyRatingList);
      	bookMap.setReviewRatingHashMap(bookReviewRatingDetails);
      	
    	
    	LinksDto linksResponse = new LinksDto();
    	linksResponse.addLink(new LinkDto("view-book", "/books/"+book.getIsbn(), "GET"));
    	linksResponse.addLink(new LinkDto("update-book", "/books/"+book.getIsbn() , "PUT"));
    	linksResponse.addLink(new LinkDto("delete-book", "/books/"+book.getIsbn() , "DELETE"));
    	linksResponse.addLink(new LinkDto("create-review", "/books/"+book.getIsbn()+"/reviews" , "POST"));
    	// add more links
    	
    	return Response.status(201).entity(linksResponse).build();
    	}
    	else
    	{
    		ErrorMessages em = new ErrorMessages();
    		em.setStatusCode(400);
    		em.setMessage("Title or publication date cannot be null");
    		return Response.ok(em).build();
    	}
    	
        }
    //DELETE BOOK
@DELETE
@Path("/{isbn}")
@Timed(name = "delete-book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

  public Response deleteBookByISBN(@PathParam("isbn") LongParam isbn) 
  {
	  HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
	
	  bookMapDetails=bookMap.getBookHashMap();
	if(bookMapDetails.get((int)(long)isbn.get())!=null)
	{
	  bookMapDetails.remove((int)(long)isbn.get());
	  System.out.print("\nisbn.get()==="+isbn.get());
	  System.out.print("\nbookMap.getBookHashMap()"+bookMap.getBookHashMap());
	  bookMap.setBookHashMap(bookMapDetails);
	  System.out.print("\nbookMap.getBookHashMap()"+bookMap.getBookHashMap());
	  LinksDto links = new LinksDto();
		links.addLink(new LinkDto("create-book", "/books", "POST"));

		return Response.ok(links).build();
	}
	else
	{
		ErrorMessages em = new ErrorMessages();
		em.setStatusCode(400);
		em.setMessage("BOOK NOT FOUND...TRY AN AVAILABLE ISBN");
		return Response.ok(em).build();
	}
  }
//return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for UUID: " + uuid).build();


    //UPDATE BOOK
	@Timed(name = "update-book")
    @PUT
    @Path("/{isbn}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
    public Response updateBookByIsbn(@PathParam("isbn") LongParam isbn,@QueryParam("status") String newStatus)
	{
		HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
    	bookMapDetails=bookMap.getBookHashMap();
    	ArrayList<String> bookToUpdate=new ArrayList<String>();
    	int updIsbn=(int)(long)isbn.get();
    	bookToUpdate=bookMapDetails.get(updIsbn);
		Book book=new Book();
    	String title="",rating,numOfPages,status,language,publicationDate;
    	//book.setIsbn(isbn.get());
    	Reviews review=new Reviews(); 
    	
    	HashMap<Integer,ArrayList> bookReviewDetails=new HashMap<Integer,ArrayList>();
    	bookReviewDetails=bookMap.getReviewHashMap();
    	
    	
    	
    	bookToUpdate.set(4, newStatus);
    	bookMapDetails.put(updIsbn,(ArrayList<String>) bookToUpdate );
    	bookMap.setBookHashMap(bookMapDetails);
    	System.out.print("doing somrthgn"+bookMap.getBookHashMap());
    	title=(String) bookMapDetails.get(updIsbn).get(0);//0 is value from arraylist
    	numOfPages=(String)bookMapDetails.get(updIsbn).get(3);
    	status=(String)bookMapDetails.get(updIsbn).get(4);
    	rating=(String)bookMapDetails.get(updIsbn).get(5);
    	language=(String)bookMapDetails.get(updIsbn).get(2);
    	publicationDate=(String)bookMapDetails.get(updIsbn).get(1);
    	
    	
    	LinksDto response = new LinksDto();
    	response.addLink(new LinkDto("view-book", "/books/" + updIsbn,
    			"GET"));
    	response.addLink(new LinkDto("update-book","/books/" + updIsbn, "PUT"));
    	response.addLink(new LinkDto("delete-book","/books/" + updIsbn, "DELETE"));
    	response.addLink(new LinkDto("create-review","/books/" +updIsbn +"/reviews", "POST"));
    	ArrayList<Author> reviewList=new ArrayList<Author>();
		reviewList=bookReviewDetails.get(updIsbn);
		if(!reviewList.isEmpty())
			response.addLink(new LinkDto("view-all-reviews","/books/" + book.getIsbn() +"/reviews", "GET"));

    	
    	return Response.status(201).entity(response).build();
		
    }
	//create Review
	@Timed(name="create-book-reviews")
	@POST
	@Path("/{isbn}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response createBookReviews(@PathParam("isbn") LongParam isbn,Reviews review)
	{
		Book book=new Book();
		HashMap<Integer,ArrayList> bookReviewDetails=new HashMap<Integer,ArrayList>();
		HashMap<Integer,ArrayList> bookReviewRatingDetails=new HashMap<Integer,ArrayList>();
		book.setIsbn(isbn.get());
		//for review
		bookReviewDetails=bookMap.getReviewHashMap();
			//for rating
		bookReviewRatingDetails=bookMap.getReviewRatingHashMap();
		
		ArrayList bookReviewList = new ArrayList();
		String comment=review.getComment();
		String commentRating=""+review.getRating();
		int reqIsbn;
    	reqIsbn=(int)(long)book.getIsbn();
    	System.out.print("\reqIsbn=="+reqIsbn);
		bookMap.addCommentToReviewHashMap(bookReviewDetails,(int) book.getIsbn(),comment);
		bookMap.addRatingToReviewHashMap(bookReviewRatingDetails,(int) book.getIsbn(),commentRating);
		System.out.print("\nbookReviewDetails=="+bookReviewDetails);
		//System.out.print("book.getIsbn()=="+book.getIsbn());
		bookReviewList=bookReviewDetails.get(reqIsbn);
		System.out.print("\nbookReviewList=="+bookReviewList);
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-reviews", "/books/"+isbn+"/reviews/"+bookReviewList.size(), "GET"));

		return Response.ok(links).build(); 
	}
    //View all Review of book
	@Timed(name="view-all-reviews")
    @GET
    @Path("/{isbn}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewAllReviews(@PathParam("isbn") LongParam isbn)
    {
    	Book book = new Book();
    	BookDetails bookMap = new BookDetails();
    	book.setIsbn(isbn.get());
    	HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
    	HashMap<Integer,ArrayList> bookReviewRatingDetails=new HashMap<Integer,ArrayList>();
    	HashMap<Integer,ArrayList> bookReviewDetails=new HashMap<Integer,ArrayList>();
      //for book
    	bookMapDetails=bookMap.getBookHashMap();
      //for rating
      	bookReviewRatingDetails=bookMap.getReviewRatingHashMap();
        //for review
      	bookReviewDetails=bookMap.getReviewHashMap();
        ArrayList bookReviewList = new ArrayList();
      	ArrayList bookReviewRatingList = new ArrayList();
    	System.out.print("this is the hashmap"+bookMapDetails);
    	System.out.print("this is the hashmap"+bookReviewDetails);
    	String title="",rating,numOfPages,status,review,language,publicationDate;
    	
    
    	int reqIsbn;

    	reqIsbn=(int)(long)book.getIsbn();
    	//reviews and rating lists
    	bookReviewList=bookReviewDetails.get(reqIsbn);
    	bookReviewRatingList=bookReviewRatingDetails.get(reqIsbn);
    	System.out.print("bookReviewList==="+bookReviewList);
    	System.out.print("bookReviewRatingList==="+bookReviewRatingList);

    	
    	System.out.print("bookReviewList.length()==="+bookReviewList.size());
    	LinksDto linkResponse = new LinksDto();
    	for(int i=1;i<=bookReviewList.size();i++)
    	{	
    		String reviewValues=(String) bookReviewList.get(i-1);
    		String reviewRating=(String)bookReviewRatingList.get(i-1);
    		String id=""+i;
    		linkResponse.addReviewLink(new ReviewDto(id,reviewRating,reviewValues));
    	}
    	return Response.status(201).entity(linkResponse).build();
    	
    }
    
	//View particular Review of book
		@Timed(name="view-book-review")
	    @GET
	    @Path("/{isbn}/reviews/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response viewBookReviewById(@PathParam("isbn") LongParam isbn,@PathParam("id") LongParam id)
	    {
	    	Book book = new Book();
	    	BookDetails bookMap = new BookDetails();
	    	
	    	book.setIsbn(isbn.get());
	    	Reviews review=new Reviews();
	    	long reviewId=id.get();
	    	
	    	HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
	      	bookMapDetails=bookMap.getBookHashMap();
	      	//if(bookMapDetails.isEmpty())
	      		//bookMapDetails=bookMap.createBookHashMap();
	      	HashMap<Integer,ArrayList> bookReviewDetails=new HashMap<Integer,ArrayList>();
	      	HashMap<Integer,ArrayList> bookReviewRatingDetails=new HashMap<Integer,ArrayList>();
	      	ArrayList bookReviewList = new ArrayList();
	      	bookReviewDetails=bookMap.getReviewHashMap();
	      	//if(bookReviewDetails.isEmpty())
				//bookReviewDetails=bookMap.createReviewHashMap();
	      	//for rating
	    	ArrayList bookReviewRatingList = new ArrayList();
	    	bookReviewRatingDetails=bookMap.getReviewRatingHashMap();
	      //	if(bookReviewRatingDetails.isEmpty())
	      	//	bookReviewRatingDetails=bookMap.createReviewRatingHashMap();
	      	
	    	System.out.print("this is the hashmap"+bookMapDetails);
	    	System.out.print("this is the hashmap"+bookReviewDetails);
	    	String title="",rating,numOfPages,status,language,publicationDate;
	    	
	    
	    	int reqIsbn;

	    	reqIsbn=(int)(long)book.getIsbn();
	    	/*title=(String) bookMapDetails.get(reqIsbn).get(0);//0 is value from arraylist
	    	numOfPages=(String)bookMapDetails.get(reqIsbn).get(3);
	    	status=(String)bookMapDetails.get(reqIsbn).get(4);
	    	rating=(String)bookMapDetails.get(reqIsbn).get(5);
	    	language=(String)bookMapDetails.get(reqIsbn).get(2);
	    	publicationDate=(String)bookMapDetails.get(reqIsbn).get(1);*/
	    	//reviews
	    	bookReviewList=bookReviewDetails.get(reqIsbn);
	    	System.out.print("bookReviewList==="+bookReviewList);
	    	//rating
	    	bookReviewRatingList=bookReviewRatingDetails.get(reqIsbn);
	    	
	    	System.out.print("bookReviewList.length()==="+bookReviewList.size());
	    	
	    	LinksDto linkResponse = new LinksDto();
	    	for(int i=1;i<=bookReviewList.size();i++)
	    	{	
	    		if(i==reviewId)
	    		{
	    			String reviewValues=(String) bookReviewList.get(i-1);
	    			String reviewRating=(String) bookReviewRatingList.get(i-1);
	    			String revId=""+reviewId;
	    			linkResponse.addReviewLink(new ReviewDto(revId,reviewRating,reviewValues));
	    		}
	    	}
	    	
	    	linkResponse.addLink(new LinkDto("view-review","/books/" + reqIsbn +"/reviews/"+reviewId, "POST"));
	    	return Response.status(201).entity(linkResponse).build();
	    	
	    }
    
    //view author of a particular book
		@Timed(name="view-book-author-by-id")
	    @GET
	    @Path("/{isbn}/authors/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public LinksDto viewBookAuthorById(@PathParam("isbn") LongParam isbn,@PathParam("id") LongParam id)
	    {
	    	Book book = new Book();
	    	BookDetails bookMap = new BookDetails();
	    	book.setIsbn(isbn.get());
	    	Author author=new Author();
	    	long authorId=id.get();
	    	
	    	HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
	      	bookMapDetails=bookMap.getBookHashMap();
	      	//if(bookMapDetails.isEmpty())
	      		//bookMapDetails=bookMap.createBookHashMap();
	      	HashMap<Integer,ArrayList> bookAuthorDetails=new HashMap<Integer,ArrayList>();
	      	ArrayList bookAuthorList = new ArrayList();
	      	bookAuthorDetails=bookMap.getAuthorHashMap();
	      	 //if(bookAuthorDetails.isEmpty())
				 //bookAuthorDetails=bookMap.createAuthorHashMap();
	    	System.out.print("this is the hashmapfor book"+bookMapDetails);
	    	System.out.print("this is the hashmap for author"+bookAuthorDetails);
	    	String title="",rating,numOfPages,status,language,publicationDate;
	    	
	    
	    	int reqIsbn;

	    	reqIsbn=(int)(long)book.getIsbn();
	    	title=(String) bookMapDetails.get(reqIsbn).get(0);//0 is value from arraylist
	    	numOfPages=(String)bookMapDetails.get(reqIsbn).get(3);
	    	status=(String)bookMapDetails.get(reqIsbn).get(4);
	    	rating=(String)bookMapDetails.get(reqIsbn).get(5);
	    	language=(String)bookMapDetails.get(reqIsbn).get(2);
	    	publicationDate=(String)bookMapDetails.get(reqIsbn).get(1);
	    	//author
	    	bookAuthorList=bookAuthorDetails.get(reqIsbn);
	    	System.out.print("bookAuthorList==="+bookAuthorList);
	    	
	    	
	    	System.out.print("bookAuthorList.length()==="+bookAuthorList.size());
	    	book.setTitle(title);
	    	
	    	book.setNumOfPages(numOfPages);
	    	book.setStatus(status);
	    	book.setPublicationDate(publicationDate);
	    	book.setLanguage(language);
	    	System.out.print("checking above if ");
	    	//BookDto bookResponse = new BookDto(book);
	    	LinksDto linkResponse = new LinksDto();
	    	for(int i=1;i<=bookAuthorList.size();i++)
	    	{	
	    		if(i==authorId)
	    		{
	    			System.out.print("checking if in "+bookAuthorList.get(i-1));
	    			String authorValues=(String) bookAuthorList.get(i-1);
	    			String authId=""+authorId;
	    			linkResponse.addAuthorLink(new AuthorDto(authId,authorValues));
	    		}
	    	}
			return linkResponse;
	    }
		//view all authors of a book
		//view author of a particular book
		@Timed(name="view-book-author")
		@GET
		@Path("/{isbn}/authors")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public LinksDto viewBookAuthors(@PathParam("isbn") LongParam isbn)
		{
			 Book book = new Book();
			 BookDetails bookMap = new BookDetails();
			 book.setIsbn(isbn.get());
			 Author author=new Author();	    	
			 HashMap<Integer,ArrayList> bookMapDetails=new HashMap<Integer,ArrayList>();
			 bookMapDetails=bookMap.getBookHashMap();
			 HashMap<Integer,ArrayList> bookAuthorDetails=new HashMap<Integer,ArrayList>();
			 ArrayList bookAuthorList = new ArrayList();
			 bookAuthorDetails=bookMap.getAuthorHashMap();
			 System.out.print("this is the hashmapfor book"+bookMapDetails);
			 System.out.print("this is the hashmap for author"+bookAuthorDetails);
			 String title="",rating,numOfPages,status,language,publicationDate;
			    	
			    
			 int reqIsbn;

			    	reqIsbn=(int)(long)book.getIsbn();
			    	title=(String) bookMapDetails.get(reqIsbn).get(0);//0 is value from arraylist
			    	numOfPages=(String)bookMapDetails.get(reqIsbn).get(3);
			    	status=(String)bookMapDetails.get(reqIsbn).get(4);
			    	rating=(String)bookMapDetails.get(reqIsbn).get(5);
			    	language=(String)bookMapDetails.get(reqIsbn).get(2);
			    	publicationDate=(String)bookMapDetails.get(reqIsbn).get(1);
			    	//author
			    	bookAuthorList=bookAuthorDetails.get(reqIsbn);
			    	System.out.print("bookAuthorList==="+bookAuthorList);
			    	
			    	
			    	System.out.print("bookAuthorList.length()==="+bookAuthorList.size());
			    	book.setTitle(title);
			    	
			    	book.setNumOfPages(numOfPages);
			    	book.setStatus(status);
			    	book.setLanguage(language);
			    	book.setPublicationDate(publicationDate);
			    	System.out.print("checking above if ");
			    	LinksDto linkResponse = new LinksDto();
			    	for(int i=1;i<=bookAuthorList.size();i++)
			    	{	
			    		
			    			System.out.print("checking if in "+bookAuthorList.get(i-1));
			    			String authorValues=(String) bookAuthorList.get(i-1);
			    			String authId=""+i;
			    			linkResponse.addAuthorLink(new AuthorDto(authId,authorValues));
			    		
			    	}
			    	linkResponse.addLink(new LinkDto("view-book-author", "/books/" + book.getIsbn()+"/authors","GET"));
					return linkResponse;
			    }
				

}
