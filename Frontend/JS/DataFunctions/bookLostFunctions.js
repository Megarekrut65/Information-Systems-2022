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

function createBookLostForm(title, obj, action, toSendData) {
    const bookIdKey = "book-id",
        dateOfLossKey = "date-of-loss",
        bookCountKey = "book-count",
        wasReturnedKey = "was-returned",
        causeOfLossKey = "cause-of-loss"
    const bookKey = "book",
        dateLossKey = "dateOfLoss",
        bookCountObjectKey = "bookCount",
        wasReturnedObjectKey = "wasReturned",
        causeOfLossObjectKey = "causeOfLoss"
    return createForm({
        "title": title,
        "inputs": {
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
            [dateOfLossKey]: {
                "type": "date",
                "value": dateLossKey in obj ? obj[dateLossKey] : getToday(),
                "required": true,
                "name": "Date of loss"
            },
            [bookCountKey]: {
                "type": "number",
                "value": bookCountObjectKey in obj ? obj[bookCountObjectKey] : "",
                "required": true,
                "name": "Book count"
            },
            [wasReturnedKey]: {
                "type": "checkbox",
                "value": wasReturnedObjectKey in obj ? obj[wasReturnedObjectKey] : "",
                "name": "Was returned?"
            },
            [causeOfLossKey]: {
                "type": "text",
                "value": causeOfLossObjectKey in obj ? obj[causeOfLossObjectKey] : "",
                "name": "Cause of loss"
            }
        },
        "ok": () => {
            toSendData(action({
                [bookIdKey]: getDataFromList(bookIdKey),
                [dateOfLossKey]: normalize(document.getElementById(dateOfLossKey).value),
                [bookCountKey]: document.getElementById(bookCountKey).value,
                [wasReturnedKey]: document.getElementById(wasReturnedKey).checked,
                [causeOfLossKey]: formatText(document.getElementById(causeOfLossKey).value)
            }))
        }
    })
}

function createBookLostFormCreate(toSendData) {
    return createBookLostForm("Add lost book", {}, createFunction(URLS.bookLost), toSendData)
}

function createBookLostFormUpdate(id, toSendData = (data) => {}) {
    return createBookLostForm("Update the book lost", get(URLS.bookLost, { "id": id }),
        updateFunction(id, URLS.bookLost), toSendData)
}

function createBookLostView(obj) {
    const bookKey = "book",
        dateLossKey = "dateOfLoss",
        bookCountObjectKey = "bookCount",
        wasReturnedObjectKey = "wasReturned",
        causeOfLossObjectKey = "causeOfLoss"
    return createObjectView({
        "title": "Book lost",
        "fields": {
            [dateLossKey]: {
                "type": "text",
                "name": "Date of loss",
                "value": obj[dateLossKey]
            },
            [bookKey]: createReference("Book", obj[bookKey].title,
                obj[bookKey], "Book"),
            [bookCountObjectKey]: {
                "type": "text",
                "name": "Book count",
                "value": obj[bookCountObjectKey]
            },
            [wasReturnedObjectKey]: {
                "type": "text",
                "name": "Was returned",
                "value": obj[wasReturnedObjectKey] ? "Yes" : "No"
            },
            [causeOfLossObjectKey]: {
                "type": "text",
                "name": "Cause of loss",
                "value": obj[causeOfLossObjectKey]
            }
        },
        "edit": () => {
            addToBody(createBookLostForm("Update the book lost", obj, updateReloadFunction(obj.id, URLS.bookLost)))
        }
    })
}

function getBookLostsForTable(bookTitle, minDate, maxDate) {
    return get(URLS.bookLostsByAll, {
            "book-title": bookTitle,
            "date-of-loss-min": minDate,
            "date-of-loss-max": maxDate
        })
        .sort((a, b) => b.dateOfLoss.localeCompare(a.dateOfLoss))
        .map(obj => {
            return {
                "Id": obj.id,
                "Loss date": {
                    "text": obj.dateOfLoss,
                    "id": obj.id,
                    "object": "BookLost",
                    "type": "reference"
                },
                "Book": {
                    "text": obj.book.title,
                    "id": obj.book.id,
                    "object": "Book",
                    "type": "reference"
                },
                "Book count": obj.bookCount,
                "Was Returned": obj.wasReturned ? "Yes" : "No",
                "Cause of loss": obj.causeOfLoss
            }
        })
}