function getAllBookWriteOffs() {
    return getAll(SERVER_URL + "/book-write-offs")
}

function createBookWriteOff(obj) {
    return create(SERVER_URL + "/book-write-off", obj)
}

function updateBookWriteOff(obj) {
    return update(SERVER_URL + "/book-write-off", obj)
}

function removeBookWriteOff(id) {
    return remove(SERVER_URL + "/book-write-off", id)
}

function findBookWriteOffsByDatePeriod(dateOfWriteOffMin, dateOfWriteOffMax) {
    return get(SERVER_URL + "/book-write-offs/by-date-of-write-off-period", { "date-of-write-off-min": dateOfWriteOffMin, "date-of-write-off-max": dateOfWriteOffMax })
}

function findBookWriteOffsByBook(bookId) {
    return get(SERVER_URL + "/book-write-offs/by-book-id", { "book-id": bookId })
}

function getBookWriteOff(id) {
    return get(SERVER_URL + "/book-write-off", { "id": id })
}