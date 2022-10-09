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

function createBookWriteOffForm(title, obj, action, toSendData = (data) => {}) {
    const bookIdKey = "book-id",
        dateOfWriteOffKey = "date-of-write-off",
        bookCountKey = "book-count"
    const bookKey = "book",
        writeOffKey = "dateOfWriteOff",
        bookCountObjectKey = "bookCount"
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
            [dateOfWriteOffKey]: {
                "type": "date",
                "value": writeOffKey in obj ? obj[writeOffKey] : getToday(),
                "required": true,
                "name": "Date of write-off"
            },
            [bookCountKey]: {
                "type": "number",
                "value": bookCountObjectKey in obj ? obj[bookCountObjectKey] : "",
                "name": "Book count",
                "required": true,
                "min": 1
            }
        },
        "ok": () => {
            toSendData(action({
                [bookIdKey]: getDataFromList(bookIdKey),
                [dateOfWriteOffKey]: normalize(document.getElementById(dateOfWriteOffKey).value),
                [bookCountKey]: document.getElementById(bookCountKey).value
            }))
        }
    })
}

function createBookWriteOffFormCreate(toSendData) {
    return createBookWriteOffForm("Write off book", {}, createFunction(URLS.bookWriteOff), toSendData)
}

function createBookWriteOffFormUpdate(id, toSendData = (data) => {}) {
    return createBookWriteOffForm("Update the book write-off", get(URLS.bookWriteOff, { "id": id }),
        updateFunction(id, URLS.bookWriteOff), toSendData)
}

function createBookWriteOffView(obj) {
    const bookKey = "book",
        writeOffKey = "dateOfWriteOff",
        bookCountObjectKey = "bookCount"
    return createObjectView({
        "title": "Book write-off",
        "fields": {
            [writeOffKey]: {
                "type": "text",
                "name": "Date of write-off",
                "value": obj[writeOffKey]
            },
            [bookKey]: createReference("Book", obj[bookKey].title,
                obj[bookKey], "Book"),
            [bookCountObjectKey]: {
                "type": "text",
                "name": "Book count",
                "value": obj[bookCountObjectKey]
            }
        },
        "edit": () => {
            addToBody(createBookWriteOffForm("Update the book write-off", obj, updateReloadFunction(obj.id, URLS.bookWriteOff)))
        }
    })
}

function getBookWriteOffsForTable(item) {
    item = normalizeItem(item, ["title", "min", "max"])
    return get(URLS.bookWriteOffsByAll, {
            "book-title": item.title,
            "date-of-write-off-min": item.min,
            "date-of-write-off-max": item.max
        })
        .sort((a, b) => b.dateOfWriteOff.localeCompare(a.dateOfWriteOff))
        .map(obj => {
            return {
                "Id": obj.id,
                "Write-off date": {
                    "text": obj.dateOfWriteOff,
                    "id": obj.id,
                    "object": "BookWriteOff",
                    "type": "reference"
                },
                "Book": {
                    "text": obj.book.title,
                    "id": obj.book.id,
                    "object": "Book",
                    "type": "reference"
                },
                "Book count": obj.bookCount
            }
        })
}

function createBookWriteOffsSearch(recreateTable) {
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
        "createTable": (item)=>recreateTable(getBookWriteOffsForTable(item)),
        "formCreate": createBookWriteOffFormCreate,
        "title": "Book write-offs"
    }
}

function createBookWriteOffFunction(toSendData) {
    return null
}