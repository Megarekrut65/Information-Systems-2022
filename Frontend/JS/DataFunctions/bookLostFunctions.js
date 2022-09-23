function getAllBookLosts() {
    return getAll(SERVER_URL + "/book-losts")
}

function createBookLost(obj) {
    return create(SERVER_URL + "/book-lost", obj)
}

function updateBookLost(obj) {
    return update(SERVER_URL + "/book-lost", obj)
}

function removeBookLost(id) {
    return remove(SERVER_URL + "/book-lost", id)
}

function findBookLostsByDatePeriod(dateOfLossMin, dateOfLossMax) {
    return get(SERVER_URL + "/book-losts/by-date-of-loss-period", { "date-of-loss-min": dateOfLossMin, "date-of-loss-max": dateOfLossMax })
}

function findBookLostsByBook(bookId) {
    return get(SERVER_URL + "/book-losts/by-book-id", { "book-id": bookId })
}

function getBookLost(id) {
    return get(SERVER_URL + "/book-lost", { "id": id })
}