function getAllBookBorrowings() {
    return getAll(SERVER_URL + "/book-borrowings")
}

function createBookBorrowing(obj) {
    return create(SERVER_URL + "/book-borrowing", obj)
}

function updateBookBorrowing(obj) {
    return update(SERVER_URL + "/book-borrowing", obj)
}

function removeBookBorrowing(id) {
    return remove(SERVER_URL + "/book-borrowing", id)
}

function findBookBorrowingsByDatePeriod(dateOfBorrowingMin, dateOfBorrowingMax) {
    return get(SERVER_URL + "/book-borrowings/by-date-of-borrowing-period", { "date-of-borrowing-min": dateOfBorrowingMin, "date-of-borrowing-max": dateOfBorrowingMax })
}

function findBookBorrowingsByBook(bookId) {
    return get(SERVER_URL + "/book-borrowings/by-book-id", { "book-id": bookId })
}

function findBookBorrowingsByCustomer(customerId) {
    return get(SERVER_URL + "/book-borrowings/by-customer-id", { "customer-id": customerId })
}

function getBookBorrowing(id) {
    return get(SERVER_URL + "/book-borrowing", { "id": id })
}