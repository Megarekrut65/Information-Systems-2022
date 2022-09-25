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

function createBookForm(title, obj, action) {
    const titleKey = "title",
        publishingHouseIdKey = "publishing-house-id",
        publishYearKey = "publish-year",
        commentKey = "comment"
    const publishingHouseKey = "publishingHouse",
        publishYearBookKey = "publishYear"
    return createForm(commandList = {
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
            let publishingHouseId = document.getElementById(publishingHouseIdKey)
            let publishingHouseIdList = document.getElementById(publishingHouseIdKey + "-datalist")
            let publishYear = document.getElementById(publishYearKey)
            let comment = document.getElementById(commentKey)
            let id = getMetaDataFromDatalist(publishingHouseIdList, publishingHouseId.value)
            action({
                [titleKey]: formatText(title.value),
                [publishingHouseIdKey]: id,
                [publishYearKey]: publishYear.value,
                [commentKey]: formatText(comment.value)
            })
        }
    })
}

function createBookFormCrate() {
    return createBookForm("Create new book", {}, (obj) => create(URLS.book, obj))
}

function createBookFormUpdate(id) {
    return createBookForm("Update the book", get(URLS.book, { "id": id }), (obj) => {
        obj["id"] = id
        update(URLS.book, obj)
    })
}