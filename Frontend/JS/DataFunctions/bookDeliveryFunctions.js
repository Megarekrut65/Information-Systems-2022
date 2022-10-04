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

function createBookDeliveryForm(title, obj, action, toSendData) {
    const deliveryIdKey = "delivery-id",
        bookIdKey = "book-id",
        bookCountKey = "book-count",
        bookPriceKey = "book-price"
    const deliveryKey = "delivery",
        bookKey = "book",
        bookCountDeliveryKey = "bookCount",
        bookPriceDeliveryKey = "bookPrice"
    return createForm({
        "title": title,
        "inputs": {
            [deliveryIdKey]: {
                "type": "list",
                "name": "Delivery",
                "value": deliveryKey in obj ? obj[deliveryKey] : "",
                "required": true,
                "list": getAll(URLS.deliveries),
                "convector": item => {
                    return { "id": item.id, "name": item.dateOfDelivery + " from " + item.distributor.name }
                },
                "plus": (convector) => {
                    addOptionToList(createDeliveryFormCreate, deliveryIdKey, convector)
                }
            },
            [bookIdKey]: {
                "type": "list",
                "name": "Book",
                "value": bookKey in obj ? obj[bookKey]["id"] : "",
                "required": true,
                "list": getAll(URLS.books),
                "convector": item => {
                    return { "name": item.title, "id": item.id }
                },
                "plus": (convector) => {
                    addOptionToList(createBookFormCreate, bookIdKey, convector)
                }
            },
            [bookCountKey]: {
                "type": "number",
                "value": bookCountDeliveryKey in obj ? obj[bookCountDeliveryKey] : "",
                "required": true,
                "name": "Book count",
                "min": 1
            },
            [bookPriceKey]: {
                "type": "number",
                "value": bookPriceDeliveryKey in obj ? obj[bookPriceDeliveryKey] : "",
                "required": true,
                "name": "Book price",
                "min": 1
            }
        },
        "ok": () => {
            toSendData(action({
                [deliveryIdKey]: getDataFromList(deliveryIdKey),
                [bookIdKey]: getDataFromList(bookIdKey),
                [bookCountKey]: document.getElementById(bookCountKey).value,
                [bookPriceKey]: document.getElementById(bookPriceKey).value
            }))
        }
    })
}

function createBookDeliveryFormCreate(toSendData) {
    return createBookDeliveryForm("Create new book delivery", {}, createFunction(URLS.bookDelivery), toSendData)
}

function createBookDeliveryFormUpdate(id) {
    return createBookDeliveryForm("Update the book delivery", get(URLS.bookDelivery, { "id": id }),
        updateFunction(id, URLS.bookDelivery), (data) => {})
}

function getBooksForTable(title, genre, tag, author) {
    return booksToTableProperties(get(URLS.booksByAll, { "title": title, "genre-name": genre, "tag-name": tag, "author-name": author }))
}

function bookDeliveriesToTableProperties(bookDeliveries) {
    return bookDeliveries.sort((a, b) => a.delivery.dateOfDelivery.localeCompare(b.delivery.dateOfDelivery))
        .map(obj => {
            return {
                "Id": {
                    "text": obj.id,
                    "id": obj.id,
                    "object": "BookDelivery",
                    "type": "reference"
                },
                "Delivery": {
                    "text": obj.delivery.dateOfDelivery,
                    "id": obj.delivery.id,
                    "object": "Delivery",
                    "type": "reference"
                },
                "Book": {
                    "text": obj.book.title,
                    "id": obj.book.id,
                    "object": "Book",
                    "type": "reference"
                },
                "Book count": obj.bookCount,
                "Book price": obj.bookPrice,
                "Total price": obj.bookCount * obj.bookPrice
            }
        })
}