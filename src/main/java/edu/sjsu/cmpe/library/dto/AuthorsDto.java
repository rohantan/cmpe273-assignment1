package edu.sjsu.cmpe.library.dto;

import edu.sjsu.cmpe.library.domain.Author;

public class AuthorsDto {
    private Author[] author;

    /**
* @param book
*/
    public AuthorsDto(Author[] author) {
     super();
     this.author = author;
        }

/**
* @return the author
*/
    public Author[] getAuthor() {
return author;
    }

    /**
* @param author
* the author to set
*/
    public void setAuthor(Author[] author) {
this.author = author;
    }


}
