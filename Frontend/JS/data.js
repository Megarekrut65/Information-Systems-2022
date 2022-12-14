const SERVER_URL = "http://127.0.0.1:8080";
const URLS = {
    genre: SERVER_URL + "/genre",
    genres: SERVER_URL + "/genres",
    genresByName: SERVER_URL + "/genres/by-name",
    genresByAllPage: SERVER_URL + "/genres/by-all/page",

    tag: SERVER_URL + "/tag",
    tags: SERVER_URL + "/tags",
    tagsByName: SERVER_URL + "/tags/by-name",
    tagsByAllPage: SERVER_URL + "/tags/by-all/page",

    book: SERVER_URL + "/book",
    books: SERVER_URL + "/books",
    booksByPublishingHouse: SERVER_URL + "/books/by-publishing-house-id",
    booksByPublishingHousePage: SERVER_URL + "/books/by-publishing-house-id/page",
    booksByTitle: SERVER_URL + "/books/by-title",
    booksByAll: SERVER_URL + "/books/by-all",
    booksByAllPage: SERVER_URL + "/books/by-all/page",
    booksToPurchase: SERVER_URL + "/books/to-purchase",
    booksToWriteOff: SERVER_URL + "/books/to-write-off",

    publishingHouses: SERVER_URL + "/publishing-houses",
    publishingHousesByName: SERVER_URL + "/publishing-houses/by-name",
    publishingHouse: SERVER_URL + "/publishing-house",
    publishingHousesByAllPage: SERVER_URL + "/publishing-houses/by-all/page",

    author: SERVER_URL + "/author",
    authors: SERVER_URL + "/authors",
    authorsByAll: SERVER_URL + "/authors/by-all",
    authorsByAllPage: SERVER_URL + "/authors/by-all/page",

    authorship: SERVER_URL + "/authorship",
    authorships: SERVER_URL + "/authorships",
    authorshipByBook: SERVER_URL + "/authorships/by-book-id",
    authorshipByAuthor: SERVER_URL + "/authorships/by-author-id",
    authorshipByAuthorPage: SERVER_URL + "/authorships/by-author-id/page",

    bookGenreByBook: SERVER_URL + "/book-genres/by-book-id",
    bookGenre: SERVER_URL + "/book-genre",
    bookGenres: SERVER_URL + "/book-genres",

    bookTag: SERVER_URL + "/book-tag",
    bookTags: SERVER_URL + "/book-tags",
    bookTagByBook: SERVER_URL + "/book-tags/by-book-id",

    bookDelivery: SERVER_URL + "/book-delivery",
    bookDeliveries: SERVER_URL + "/book-deliveries",
    bookDeliveriesByDelivery: SERVER_URL + "/book-deliveries/by-delivery-id",
    bookDeliveriesByDeliveryPage: SERVER_URL + "/book-deliveries/by-delivery-id/page",
    bookDeliveriesByBook: SERVER_URL + "/book-deliveries/by-book-id",
    bookDeliveriesByAll: SERVER_URL + "/book-deliveries/by-all",
    bookDeliveriesByAllPage: SERVER_URL + "/book-deliveries/by-all/page",

    delivery: SERVER_URL + "/delivery",
    deliveries: SERVER_URL + "/deliveries",
    deliveriesByAll: SERVER_URL + "/deliveries/by-all",
    deliveriesByAllPage: SERVER_URL + "/deliveries/by-all/page",
    deliveriesByDistributor: SERVER_URL + "/deliveries/by-distributor-id",
    deliveriesByDistributorPage: SERVER_URL + "/deliveries/by-distributor-id/page",


    distributor: SERVER_URL + "/distributor",
    distributors: SERVER_URL + "/distributors",
    distributorsByName: SERVER_URL + "/distributors/by-name",
    distributorsByAllPage: SERVER_URL + "/distributors/by-all/page",


    bookBorrowing: SERVER_URL + "/book-borrowing",
    bookBorrowings: SERVER_URL + "/book-borrowings",
    bookBorrowingsByCustomer: SERVER_URL + "/book-borrowings/by-customer-id",
    bookBorrowingsNotReturnedByCustomer: SERVER_URL + "/book-borrowings/not-returned/by-customer-id",
    bookBorrowingsByAll: SERVER_URL + "/book-borrowings/by-all",
    bookBorrowingsByAllPage: SERVER_URL + "/book-borrowings/by-all/page",
    bookBorrowingsReturnedByCustomerPage: SERVER_URL + "/book-borrowings/by-customer-id/returned/page",
    bookBorrowingsNotReturnedByCustomerPage: SERVER_URL + "/book-borrowings/by-customer-id/not-returned/page",

    customer: SERVER_URL + "/customer",
    customers: SERVER_URL + "/customers",
    customersByAll: SERVER_URL + "/customers/by-all",
    customersByAllPage: SERVER_URL + "/customers/by-all/page",

    monthStatistic: SERVER_URL + "/month-statistic",
    monthStatisticClosest: SERVER_URL + "/month-statistic/closest-statistic/by-month-date",
    monthStatistics: SERVER_URL + "/month-statistics",
    monthStatisticsByPeriod: SERVER_URL + "/month-statistics/by-month-date-period",
    monthStatisticsByPeriodPage: SERVER_URL + "/month-statistics/by-month-date-period/page",

    bookWriteOff: SERVER_URL + "/book-write-off",
    bookWriteOffs: SERVER_URL + "/book-write-offs",
    bookWriteOffsByAll: SERVER_URL + "/book-write-offs/by-all",
    bookWriteOffsByAllPage: SERVER_URL + "/book-write-offs/by-all/page",

    bookLost: SERVER_URL + "/book-lost",
    bookLosts: SERVER_URL + "/book-losts",
    bookLostsByAll: SERVER_URL + "/book-losts/by-all",
    bookLostsByAllPage: SERVER_URL + "/book-losts/by-all/page"
}
const indexProperties = [{
        "title": "Book management",
        "img": "../Images/book.png",
        "list": ["View books", "Search books", "Add new books", "Borrow books"],
        "location": "blank.html?object=Book&objects=Books"
    },
    {
        "title": "Author management",
        "img": "../Images/writers.png",
        "list": ["View authors", "Search authors and books by author", "Add new author"],
        "location": "blank.html?object=Author&objects=Authors"
    },
    {
        "title": "Customer management",
        "img": "../Images/customers.png",
        "list": ["View customers", "Search customers", "Add new customer", "Return book"],
        "location": "blank.html?object=Customer&objects=Customers"
    },
    {
        "title": "Library statistics",
        "img": "../Images/statistic.png",
        "list": ["Borrowing statistic", "Delivery statistic", "Recommendations for purchase"],
        "location": "statistic.html"
    },
    {
        "title": "All data lists",
        "img": "../Images/functions.png",
        "list": ["Genres", "Tags", "Publishing houses"],
        "location": "other.html"
    },
]