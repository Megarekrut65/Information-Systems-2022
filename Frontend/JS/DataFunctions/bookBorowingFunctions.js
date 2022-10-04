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

function createBookBorrowingForm(title, obj, action, toSendData) {
    if ("error" in obj) return errorFormCreate(title, "Borrowing not found!")
    const customerIdKey = "customer-id",
        bookIdKey = "book-id",
        borrowingDateKey = "date-of-borrowing",
        estimatedDateKey = "estimated-date-of-return",
        actualDateKey = "actual-date-of-return",
        comment = "comment"
    const customerKey = "customer",
        bookKey = "book",
        borrowingKey = "dateOfBorrowing",
        estimatedKey = "estimatedDateOfReturn",
        actualKey = "actualDateOfReturn"
    return createForm({
        "title": title,
        "inputs": {
            [customerIdKey]: {
                "type": "list",
                "name": "Customer",
                "value": customerKey in obj ? obj[customerKey]["id"] : "",
                "required": true,
                "list": getAll(URLS.customers),
                "convector": item => {
                    return { "name": item.name + " " + item.phoneNumber, "id": item.id }
                },
                "plus": (convector) => {
                    addOptionToList(createCustomerFormCreate, customerIdKey, convector)
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
            [borrowingDateKey]: {
                "type": "date",
                "value": borrowingKey in obj ? obj[borrowingKey] : getToday(),
                "required": true,
                "name": "Date of borrowing"
            },
            [estimatedDateKey]: {
                "type": "date",
                "value": estimatedKey in obj ? obj[estimatedKey] : "",
                "required": true,
                "name": "Estimated date of return"
            },
            [actualDateKey]: {
                "type": "date",
                "value": actualKey in obj ? obj[actualKey] : "",
                "name": "Actual date of return"
            },
            [comment]: {
                "type": "text",
                "value": comment in obj ? obj[comment] : "",
                "name": "Comment"
            }
        },
        "ok": () => {
            toSendData(action({
                [customerIdKey]: getDataFromList(customerIdKey),
                [bookIdKey]: getDataFromList(bookIdKey),
                [borrowingDateKey]: normalize(document.getElementById(borrowingDateKey).value),
                [estimatedDateKey]: normalize(document.getElementById(estimatedDateKey).value),
                [actualDateKey]: normalize(document.getElementById(actualDateKey).value),
                [comment]: formatText(document.getElementById(comment).value)
            }))
        }
    })
}

function createBookBorrowingFormCreate(toSendData) {
    return createBookBorrowingForm("Borrow book", {}, createFunction(URLS.bookBorrowing), toSendData)
}

function createBookBorrowingFormUpdate(id, toSendData = (data) => {}) {
    return createBookBorrowingForm("Update the book borrowing", get(URLS.bookBorrowing, { "id": id }),
        updateFunction(id, URLS.bookBorrowing), toSendData)
}

function createBookBorrowingView(obj) {
    const customerIdKey = "customer",
        bookIdKey = "book",
        borrowingDateKey = "dateOfBorrowing",
        estimatedDateKey = "estimatedDateOfReturn",
        actualDateKey = "actualDateOfReturn",
        comment = "comment"
    return createObjectView({
        "title": "Book borrowing",
        "fields": {
            [borrowingDateKey]: {
                "type": "text",
                "name": "Date of borrowing",
                "value": obj[borrowingDateKey]
            },
            [estimatedDateKey]: {
                "type": "text",
                "name": "Estimated date of return",
                "value": obj[estimatedDateKey]
            },
            [actualDateKey]: {
                "type": "text",
                "name": "Actual date of return",
                "value": obj[actualDateKey]
            },
            [customerIdKey]: createReference("Customer", obj[customerIdKey].name,
                obj[customerIdKey], "Customer"),
            [bookIdKey]: createReference("Book", obj[bookIdKey].title,
                obj[bookIdKey], "Book"),
            [comment]: {
                "type": "text",
                "name": "Comment",
                "value": obj[comment]
            }
        },
        "edit": () => {
            addToBody(createBookBorrowingForm("Update the book borrowing", obj, updateReloadFunction(obj.id, URLS.bookBorrowing)))
        }
    })
}

function getBookBorrowingsForTable(bookTitle, customerName, minDate, maxDate) {
    return get(URLS.bookBorrowingsByAll, {
            "book-title": bookTitle,
            "customer-name": customerName,
            "date-of-borrowing-min": minDate,
            "date-of-borrowing-max": maxDate
        })
        .sort((a, b) => b.dateOfBorrowing.localeCompare(a.dateOfBorrowing))
        .map(obj => {
            return {
                "Id": obj.id,
                "Borrowing date": {
                    "text": obj.dateOfBorrowing,
                    "id": obj.id,
                    "object": "BookBorrowing",
                    "type": "reference"
                },
                "Estimated return date": obj.estimatedDateOfReturn,
                "Actual return date": obj.actualDateOfReturn,
                "Book": {
                    "text": obj.book.title,
                    "id": obj.book.id,
                    "object": "Book",
                    "type": "reference"
                },
                "Customer": {
                    "text": obj.customer.name,
                    "id": obj.customer.id,
                    "object": "Customer",
                    "type": "reference"
                }
            }
        })
}

function createBookReturnCustomerFormCreate(toSendData) {
    const customerIdKey = "customer-id"
    return createForm({
        "title": "Select customer",
        "inputs": {
            [customerIdKey]: {
                "type": "list",
                "name": "Customer",
                "value": "",
                "required": true,
                "list": getAll(URLS.customers),
                "convector": item => {
                    return { "name": item.name + " " + item.phoneNumber, "id": item.id }
                },
                "plus": (convector) => {
                    addOptionToList(createCustomerFormCreate, customerIdKey, convector)
                }
            }
        },
        "ok": () => {
            addToBody(createBookReturnBorrowingFormCreate(getCustomer(getDataFromList(customerIdKey)), toSendData))
        }
    })
}

function createBookReturnBorrowingFormCreate(customer, toSendData) {
    const customerIdKey = "customer-id",
        borrowingIdKey = "borrowing-id"
    return createForm({
        "title": "Select borrowing",
        "inputs": {
            [customerIdKey]: {
                "type": "text",
                "name": "Customer",
                "value": customer.id + " - " + customer.name,
                "readOnly": true
            },
            [borrowingIdKey]: {
                "type": "list",
                "name": "Borrowing",
                "value": "",
                "required": true,
                "list": get(URLS.bookBorrowingsNotReturnedByCustomer, {
                    [customerIdKey]: customer.id
                }),
                "convector": item => {
                    return { "name": item.book.title + " - " + item.dateOfBorrowing, "id": item.id }
                }
            }
        },
        "ok": () => {
            addToBody(createBookBorrowingFormUpdate(getDataFromList(borrowingIdKey), toSendData))
        }
    })
}