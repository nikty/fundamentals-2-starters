import tester.*;
/*
  Suppose you are working on a research paper, and you have gathered a
  set of documents together for your bibliography: books and Wikipedia
  articles. Every document has an author, a title, and a bibliography
  of documents; additionally, books have publishers, and wiki articles
  have URLs.

  - Since you know that wiki articles are not necessarily authoritative
  sources[citation needed], you want to produce a bibliography
  containing just the authors and titles of the books you’ve found,
  either directly or transitively through the bibliographies of other
  documents. Format the entries as “Last name, First name. "Title".”

  - Since bibliographies must be alphabetized, sort the bibliography by
  the authors’ last names.

  - Documents may be referenced more than once, but should only appear
  in the bibliography once. Remove any duplicates (defined as the same
  author name and the same title)

  Design your data definitions to represent this information. Design a
  method to produce a properly formatted bibliography. Design whatever
  helper methods you need to solve the problem well.
*/

// interp. One of
// - Book
// - WikiArticle
interface IDocument {
    // Produce a bibliography of books, including this
    ILoDocument makeBookBibliography();

    // Return true if the given document has same author and title as this
    boolean sameAuthorTitle(IDocument that);

    // Return author
    String getAuthor();

    // Return title
    String getTitle();

    // Produce string representation of this item 
    String print();
}


// interp. A book with author, title, bibliography and publisher.
class Book implements IDocument {
    String author; // if author has last name, then "Last Name, First Name", else only "First Name"
    String title;
    ILoDocument bibliography;
    String publisher;

    Book(String author, String title, ILoDocument bibliography, String publisher) {
        this.author = author;
        this.title = title;
        this.bibliography = bibliography;
        this.publisher = publisher;
    }

    public ILoDocument makeBookBibliography() {
        return new ConsLoDocument(this, this.bibliography.makeBookBibliography());
    }

    public boolean sameAuthorTitle(IDocument that) {
        return this.getAuthor().equals(that.getAuthor()) && this.getTitle().equals(that.getTitle());
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String print() {
        return this.author + ". \"" + this.title + "\"";
    }
}

// interp. A wiki article with author, title, bibliography and URI.
class WikiArticle implements IDocument {
    String author; // if author has last name, then "Last Name, First Name", else only "First Name"
    String title;
    ILoDocument bibliography;
    String uri;

    WikiArticle(String author, String title, ILoDocument bibliography, String uri) {
        this.author = author;
        this.title = title;
        this.bibliography = bibliography;
        this.uri = uri;
    }

    // Do not include this article in the list of books
    public ILoDocument makeBookBibliography() {
        return this.bibliography.makeBookBibliography();
    }

    public boolean sameAuthorTitle(IDocument that) {
        return this.getAuthor().equals(that.getAuthor()) && this.getTitle().equals(that.getTitle());
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String print() {
        return this.author + ". \"" + this.title + "\"";
    }
}

// interp. List of documents. One of
// - MtLoDocument
// - ConsLoDocument
interface ILoDocument {
    // Produce book bibliography for this list
    ILoDocument makeBookBibliography();

    // Produce formatted list of books
    ILoString makeFormattedBookBibliography();

    // Return true if given document is in this list
    boolean contains(IDocument doc);

    // Return copy of this list sorted by author's name
    // ASSUME: Last Name comes first in the name, if any
    ILoDocument sortByAuthorLastName();

    // Produce a list of string representations of the items
    ILoString print();

    // Append given list to this list
    ILoDocument append(ILoDocument other);

    // Insert given document into already sorted list
    ILoDocument insertSortedByAuthorLastName(IDocument doc);

    // Return list without duplicates
    // ASSUME: two documents are same if their author and title are same
    ILoDocument unique();
}

// interp. Empty list
class MtLoDocument implements ILoDocument {
    MtLoDocument() {}

    public ILoDocument makeBookBibliography() {
        return this;
    }

    public ILoString makeFormattedBookBibliography() {
        return new MtLoString();
    }

    public boolean contains(IDocument doc) {
        return false;
    }

    public ILoDocument sortByAuthorLastName() {
        return this;
    }

    public ILoString print() {
        return new MtLoString();
    }

    public ILoDocument append(ILoDocument other) {
        return other;
    }
    
    public ILoDocument insertSortedByAuthorLastName(IDocument doc) {
        return new ConsLoDocument(doc, this);
    }

    public ILoDocument unique() {
        return this;
    }
}

// interp. List of documents
class ConsLoDocument implements ILoDocument {
    IDocument first;
    ILoDocument rest;

    ConsLoDocument(IDocument first, ILoDocument rest) {
        this.first = first;
        this.rest = rest;
    }

    public ILoDocument makeBookBibliography() {
        return this.first.makeBookBibliography().append(this.rest.makeBookBibliography());
    }

    public ILoString makeFormattedBookBibliography() {
        return this.makeBookBibliography()
            .sortByAuthorLastName().unique().print();
    }

    public boolean contains(IDocument doc) {
        return this.first.sameAuthorTitle(doc) || this.rest.contains(doc);
    }

    public ILoDocument sortByAuthorLastName() {
        return this.rest.sortByAuthorLastName().insertSortedByAuthorLastName(this.first);
    }

    public ILoString print() {
        return new ConsLoString(this.first.print(),
                                this.rest.print());
    }

    public ILoDocument insertSortedByAuthorLastName(IDocument doc) {
        if (this.first.getAuthor().compareTo(doc.getAuthor()) > 0) {
            return new ConsLoDocument(doc, this);
        } else {
            return new ConsLoDocument(this.first, this.rest.insertSortedByAuthorLastName(doc));
        }
    }

    public ILoDocument append(ILoDocument other) {
        return new ConsLoDocument(this.first, this.rest.append(other));
    }

    public ILoDocument unique() {
        if (this.rest.contains(this.first)) {
            return this.rest.unique();
        } else {
            return new ConsLoDocument(this.first, this.rest.unique());
        }
    }
}

// interp. List of strings,
interface ILoString {
}

class ConsLoString implements ILoString {
    String first;
    ILoString rest;

    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }
}

class MtLoString implements ILoString {
    MtLoString() {}
}
            
class DocumentExamples {
    // TODO: tests for helper methods
    IDocument javaGoetz = new Book("Goetz, Brian",
                                   "Java Concurrency in Practice",
                                   new MtLoDocument(),
                                   "Addison-Wesley");
    IDocument viega = new Book("Viega, John",
                               "Building Secure Software: How to Avoid Security Problems the Right Way",
                               new MtLoDocument(),
                               "Addison-Wesley");

    IDocument javaSpec = new Book("Gosling, James",
                                  "The Java Language Specification",
                                  new MtLoDocument(),
                                  "Addison-Wesley");

    IDocument designPattterns = new Book("Gamma, Erich",
                                         "Design Patterns: Elements of Reusable Object-Oriented Software",
                                         new MtLoDocument(),
                                         "Addison-Wesley");

    ILoDocument biblioJavaBloch = new ConsLoDocument(javaGoetz, new ConsLoDocument
                                                   (viega, new ConsLoDocument
                                                    (designPattterns, new ConsLoDocument
                                                     (javaSpec, new MtLoDocument()))));
    
    IDocument javaBloch = new Book("Bloch, Joshua",
                                   "Effective Java: Programming Language Guide",
                                   biblioJavaBloch,
                                   "Addison-Wesley");

    ILoDocument biblioJavaFelleisen = new ConsLoDocument(designPattterns,
                                                       new ConsLoDocument(javaSpec, new MtLoDocument()));

    IDocument javaFelleisen = new Book("Felleisen, Matthias",
                                       "A Little Java, A Few Patterns",
                                       biblioJavaFelleisen,
                                       "The MIT Press");

    ILoDocument biblioWikiFelleisen = new ConsLoDocument(javaFelleisen, new MtLoDocument());

    IDocument wikiFelleisen = new WikiArticle("Wikipedia contributors",
                                              "Matthias Felleisen",
                                              biblioWikiFelleisen,
                                              "https://en.wikipedia.org/wiki/Matthias_Felleisen");
                                           
    ILoDocument biblioWikiLisp = new ConsLoDocument(wikiFelleisen, new MtLoDocument());
    
    IDocument wikiLisp = new WikiArticle("Wikipedia contributors",
                                         "Lisp (programming language)",
                                         biblioWikiLisp,
                                         "https://en.wikipedia.org/wiki/Lisp_(programming_language)");


    ILoDocument biblioWikiJava = new ConsLoDocument(javaBloch, new ConsLoDocument
                                                  (javaSpec, new ConsLoDocument
                                                   (wikiLisp, new MtLoDocument())));

    IDocument wikiJava = new WikiArticle("Wikipedia contributors",
                                         "Java (programming language)",
                                         biblioWikiJava,
                                         "https://en.wikipedia.org/wiki/Java_(programming_language)");

    ILoDocument biblioResearchPaper = new ConsLoDocument(wikiJava, new MtLoDocument());

    boolean testMakeBibliography(Tester t) {
        return t.checkExpect(biblioResearchPaper.makeFormattedBookBibliography(),
                             new ConsLoString
                             ("Bloch, Joshua. \"Effective Java: Programming Language Guide\"",
                              new ConsLoString
                              ("Felleisen, Matthias. \"A Little Java, A Few Patterns\"",
                              new ConsLoString
                              ("Gamma, Erich. \"Design Patterns: Elements of Reusable Object-Oriented Software\"",
                               new ConsLoString
                              ("Goetz, Brian. \"Java Concurrency in Practice\"",
                               new ConsLoString
                               ("Gosling, James. \"The Java Language Specification\"",
                                new ConsLoString
                                ("Viega, John. \"Building Secure Software: How to Avoid Security Problems the Right Way\"",
                                 new MtLoString())))))));
    }
}
