import tester.*;

class Book {
    String name;
    String author;
    double price;
    int year;
    
    Book(String name, String author, double price, int year) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.year = year;
    }

    // Return price of this book
    double getPrice() {
        return this.price;
    }
    // Return the discounted price of this book given the discount rate
    double salePrice(int discount) {
        return this.price - (discount * this.price / 100);
    }
    
}

// IListOfBook is a union of classes
// - EmptyListOfBook
// - ConsListOfBook

interface IListOfBook {

    // Count how many books are in this list of books
    int count();
    
    // Return a list of all the books in this list of books published
    // before the given year
    IListOfBook allBooksBefore(int year);
    
    // Produce  a list of all books in this list, sorted increasing by price
    IListOfBook sortByPrice();
    
    // Calculate the total sale price of all books in this list for a given discount
    double salePrice(int discount);
    
}
class EmptyListOfBook implements IListOfBook {

    EmptyListOfBook() {}
    
    public int count() {
        // ...
    }

    public IListOfBook allBooksBefore(int year) {
        // ...
    }

    public IListOfBook sortByPrice() {
        // ...
    }

    public double salePrice(int discount) {
        // ...
    }
}

class ConsListOfBook implements IListOfBook {
    Book first;
    IListOfBook rest;
    
    ConsListOfBook(Book first, IListOfBook rest) {
        this.first = first;
        this.rest = rest;
    }
    
    public int count() {
        // ...
    }

    public IListOfBook allBooksBefore(int year) {
        // ...
    }

    public IListOfBook sortByPrice() {
        // ...
    }

    public double salePrice(int discount) {
        // ...
    }
}

class ExamplesBooks {
    
    Book b1 = new Book("Pride and Prejudice", "Jane Austen", 12.99, 1813);
    Book b2 = new Book("1984", "George Orwell", 15.00, 1949);
    Book b3 = new Book("To Kill a Mockingbird", "Haper Lee", 18.99, 1960);
    Book b4 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 10.99, 1925);
    Book b5 = new Book("The Catcher in the Rye", "J.D. Salinger", 14.99, 1951);
    
    IListOfBook emptyList = new EmptyListOfBook();
    IListOfBook twoBooks = new ConsListOfBook(this.b1, 
                                              new ConsListOfBook(this.b2, 
                                                                 this.emptyList));
    IListOfBook threeBooks = new ConsListOfBook(this.b3, this.twoBooks);
    IListOfBook lst4 = new ConsListOfBook(this.b2,
                                          new ConsListOfBook(this.b1,
                                                             this.emptyList));


    boolean testCount(Tester t) {
        return t.checkExpect(emptyList.count(), 0)
            && t.checkExpect(twoBooks.count(), 2)
            && t.checkExpect(threeBooks.count(), 3);
    }

    boolean testSalePrice(Tester t) {
        return t.checkExpect(emptyList.salePrice(10), 0.0)
            && t.checkExpect(twoBooks.salePrice(20), (12.99 + 15.00) * 0.8)
            && t.checkExpect(threeBooks.salePrice(30), (12.99 + 15.00 + 18.99) * 0.7);        
    }

    boolean testAllBooksBefore(Tester t) {
        return t.checkExpect(emptyList.allBooksBefore(2000), new EmptyListOfBook())
            && t.checkExpect(twoBooks.allBooksBefore(2000), this.twoBooks)
            && t.checkExpect(twoBooks.allBooksBefore(1900),
                             new ConsListOfBook(this.b1, new EmptyListOfBook()));
    }

    boolean testSortByPrice(Tester t) {
        return t.checkExpect(emptyList.sortByPrice(), new EmptyListOfBook())
            && t.checkExpect(this.twoBooks.sortByPrice(), this.twoBooks)
            && t.checkExpect(this.lst4.sortByPrice(),
                             new ConsListOfBook(this.b1,
                                                new ConsListOfBook(this.b2,
                                                                   this.emptyList)));
    }
            
}
