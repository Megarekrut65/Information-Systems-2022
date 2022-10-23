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

function createBookDeliveryForm(title, obj, action, toSendData = (data) => {}) {
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
                "value": deliveryKey in obj ? obj[deliveryKey]["id"] : "",
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

function createBookDeliveryView(obj) {
    const deliveryKey = "delivery",
        bookKey = "book",
        bookCountDeliveryKey = "bookCount",
        bookPriceDeliveryKey = "bookPrice"
    return createObjectView({
        "title": "Book delivery",
        "fields": {
            [deliveryKey]: createReference("Delivery", obj[deliveryKey].dateOfDelivery,
                obj[deliveryKey], "Delivery"),
            [bookKey]: createReference("Book", obj[bookKey].title,
                obj[bookKey], "Book"),
            [bookCountDeliveryKey]: {
                "type": "text",
                "name": "Book count",
                "value": obj[bookCountDeliveryKey]
            },
            [bookPriceDeliveryKey]: {
                "type": "text",
                "name": "Book price",
                "value": obj[bookPriceDeliveryKey]
            },
            ["totalPrice"]: {
                "type": "text",
                "name": "Total price",
                "value": obj[bookCountDeliveryKey] * obj[bookPriceDeliveryKey]
            }
        },
        "edit": () => {
            addToBody(createBookDeliveryForm("Update the book delivery", obj, updateReloadFunction(obj.id, URLS.bookDelivery)))
        }
    })
}

function getBookDeliveriesForTable(item) {
    item = normalizeItem(item, ["title", "min", "max"])
    return bookDeliveriesToTableProperties((page, perPage)=>getPage(URLS.bookDeliveriesByAllPage,page, perPage, {
        "book-title": item.title,
        "date-of-delivery-min": item.min,
        "date-of-delivery-max": item.max
    }))
}
function getAllBookDeliveriesForTable() {
    return bookDeliveriesToTableProperties(()=>getAll(URLS.bookDeliveries))
}
function bookDeliveriesToTableProperties(getter) {
    let convector = obj=>  {
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
    }
    return {
        "convector":convector,
        "getter":getter
    }
}

function createBookDeliveriesSearch(recreateTable) {
    return {
        "inputs": {
            "title": {
                "type": "text",
                "placeholder": "Books title..."
            },
            "min": {
                "type": "date",
                "label": "Begin"
            },
            "max": {
                "type": "date",
                "label": "End"
            }
        },
        "createTable": (item)=>recreateTable(getBookDeliveriesForTable(item)),
        "formCreate": createBookDeliveryFormCreate,
        "title": "Book deliveries"
    }
}

function createBookDeliveryFunction(toSendData) {
    return null
}