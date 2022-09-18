function getAllBooks() {
    return getAll(SERVER_URL + "/books")
}

function createBook(obj) {
    return create(SERVER_URL + "/book", obj)
}

function updateBook(obj) {
    return update(SERVER_URL + "/book", obj)
}

function removeBook(id) {
    return remove(SERVER_URL + "/book", id)
}

function findBooksByTitle(title) {
    return get(SERVER_URL + "/books/by-title", { "title": title })
}

function findBooksByPublishingHouse(publishingHouseId) {
    return get(SERVER_URL + "/books/by-publishing-house-id", { "publishing-house-id": publishingHouseId })
}

function getBook(id) {
    return get(SERVER_URL + "/book", { "id": id })
}