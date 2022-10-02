const SERVER_URL = "http://127.0.0.1:8080";
const URLS = {
    genre: SERVER_URL + "/genre",
    genres: SERVER_URL + "/genres",
    tag: SERVER_URL + "/tag",
    tags: SERVER_URL + "/tags",
    book: SERVER_URL + "/book",
    books: SERVER_URL + "/books",
    publishingHouses: SERVER_URL + "/publishing-houses",
    publishingHouse: SERVER_URL + "/publishing-house",
    author: SERVER_URL + "/author",
    authors: SERVER_URL + "/authors",
    authorship: SERVER_URL + "/authorship",
    authorships: SERVER_URL + "/authorships",
    bookGenreByBook: SERVER_URL + "/book-genres/by-book-id",
    bookGenre: SERVER_URL + "/book-genre",
    bookGenres: SERVER_URL + "/book-genres",
    bookTag: SERVER_URL + "/book-tag",
    bookTags: SERVER_URL + "/book-tags",
    bookTagByBook: SERVER_URL + "/book-tags/by-book-id",
    authorshipByBook: SERVER_URL + "/authorships/by-book-id"
}
const indexProperties = [
    { "title": "Book management", "img": "../Images/book.png", "list": ["View books", "Search books", "Add new books", "Borrow books"], "location": "book.html" },
    { "title": "Author management", "img": "../Images/writers.png", "list": ["View authors", "Search authors and books by author", "Add new author"], "location": "author.html" },
    { "title": "Customer management", "img": "../Images/customers.png", "list": ["View customers", "Search customers", "Add new customer", "Return book"], "location": "customer.html" },
    { "title": "Library statistics", "img": "../Images/statistic.png", "list": ["Borrowing statistic", "Purchasing statistic", "Recommendations for purchase"], "location": "statistic.html" },
]