function getAllBooks() {
    return getAll(URLS.books)
}

function createBook(obj) {
    return create(URLS.book, obj)
}

function updateBook(obj) {
    return update(URLS.book, obj)
}

function removeBook(id) {
    return remove(URLS.book, id)
}

function findBooksByTitle(title) {
    return get(SERVER_URL + "/books/by-title", { "title": title })
}

function findBooksByPublishingHouse(publishingHouseId) {
    return get(SERVER_URL + "/books/by-publishing-house-id", { "publishing-house-id": publishingHouseId })
}

function findBooksByPublishYearPeriod(publishYearMin, publishYearMax) {
    return get(SERVER_URL + "/books/by-publish-year-period", { "publish-year-min": publishYearMin, "publish-year-max": publishYearMax })
}

function getBook(id) {
    return get(URLS.book, { "id": id })
}


function createBookForm(title, obj, action, toSendData) {
    const titleKey = "title",
        publishingHouseIdKey = "publishing-house-id",
        publishYearKey = "publish-year",
        commentKey = "comment"
    const publishingHouseKey = "publishingHouse",
        publishYearBookKey = "publishYear"
    return createForm({
        "title": title,
        "inputs": {
            [titleKey]: {
                "type": "text",
                "name": "Book title",
                "value": titleKey in obj ? obj[titleKey] : "",
                "required": true
            },
            [publishingHouseIdKey]: {
                "type": "list",
                "name": "Publishing House",
                "value": publishingHouseKey in obj ? obj[publishingHouseKey]["id"] : "",
                "required": true,
                "list": getAll(URLS.publishingHouses).map(item => {
                    return { "name": item.name, "id": item.id }
                }),
                "plus": () => {
                    //let parent = document.getElementById("box")
                    //parent.appendChild()
                    alert("Plus")
                }
            },
            [publishYearKey]: {
                "type": "number",
                "value": publishYearBookKey in obj ? obj[publishYearBookKey] : "",
                "required": true,
                "name": "Publish year"
            },
            [commentKey]: {
                "type": "text",
                "name": "Comment",
                "value": commentKey in obj ? obj[commentKey] : ""
            },

        },
        "ok": () => {
            let title = document.getElementById(titleKey)
            let publishYear = document.getElementById(publishYearKey)
            let comment = document.getElementById(commentKey)
            toSendData(action({
                [titleKey]: formatText(title.value),
                [publishingHouseIdKey]: getDataFromList(publishingHouseIdKey),
                [publishYearKey]: publishYear.value,
                [commentKey]: formatText(comment.value)
            }))
        }
    })
}

function createBookFormCrate(toSendData) {
    return createBookForm("Create new book", {}, createFunction(URLS.book), toSendData)
}

function createBookFormUpdate(id) {
    return createBookForm("Update the book", get(URLS.book, { "id": id }),
        updateFunction(id, URLS.book), (data) => {})
}

function createBookView(obj) {
    const titleKey = "title",
        publishingHouseIdKey = "publishing-house-id",
        publishYearKey = "publish-year",
        commentKey = "comment",
        bookGenreKey = "book-genre",
        bookTagKey = "book-tag",
        authorshipKey = "authorship",
        bookInfo = "book-info"
    const publishingHouseKey = "publishingHouse",
        publishYearBookKey = "publishYear"
    return createObjectView({
        "title": "Book",
        "fields": {
            [titleKey]: {
                "type": "text",
                "name": "Title",
                "value": obj[titleKey]
            },
            [bookGenreKey]: createList("Genres", obj, get(URLS.bookGenreByBook, { "book-id": obj["id"] }).map(item => {
                return { "name": item.genre.name, "id": item.id }
            }), URLS.bookGenre, createBookGenreFormCrate, (data) => { return { "id": data.id, "name": data.genre.name } }),
            [bookTagKey]: createList("Tags", obj, get(URLS.bookTagByBook, { "book-id": obj["id"] }).map(item => {
                return { "name": item.tag.name, "id": item.id }
            }), URLS.bookTag, createBookTagFormCrate, (data) => { return { "id": data.id, "name": data.tag.name } }),
            [authorshipKey]: createList("Authorship", obj, get(URLS.authorshipByBook, { "book-id": obj["id"] }).map(item => {
                return { "name": item.author.name + " - " + item.authorRole, "id": item.id }
            }), URLS.authorship, createAuthorshipFormCrate, (data) => { return { "id": data.id, "name": data.author.name } }),
            [publishingHouseIdKey]: {
                "type": "reference",
                "name": "Publishing house",
                "value": obj[publishingHouseKey]["name"],
                "onReference": () => {
                    let id = obj[publishingHouseKey]["id"]
                    openNewPage(id, "PublishingHouse")
                }
            },
            [publishYearKey]: {
                "type": "text",
                "name": "Publish year",
                "value": obj[publishYearBookKey]
            },
            [commentKey]: {
                "type": "text",
                "name": "Comment",
                "value": obj[commentKey]
            }
        },
        "edit": () => {
            document.getElementsByTagName("body")[0]
                .appendChild(createBookForm("Update the book", obj, addIdAndUpdateReloadFunction(obj.id, URLS.book)))
        }
    })
}