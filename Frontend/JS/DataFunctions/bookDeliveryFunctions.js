function getAllBookDeliveries() {
    return getAll(SERVER_URL + "/book-deliveries")
}

function createBookDelivery(obj) {
    return create(SERVER_URL + "/book-delivery", obj)
}

function updateBookDelivery(obj) {
    return update(SERVER_URL + "/book-delivery", obj)
}

function removeBookDelivery(id) {
    return remove(SERVER_URL + "/book-delivery", id)
}

function findBookDeliveriesByDelivery(deliveryId) {
    return get(SERVER_URL + "/book-deliveries/by-delivery-id", { "delivery-id": deliveryId })
}

function findBookDeliveriesByBook(bookId) {
    return get(SERVER_URL + "/book-deliveries/by-book-id", { "book-id": bookId })
}

function getBookDelivery(id) {
    return get(SERVER_URL + "/book-delivery", { "id": id })
}