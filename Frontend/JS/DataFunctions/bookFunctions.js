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

function findBooksByPublishYearPeriod(publishYearMin, publishYearMax) {
    return get(SERVER_URL + "/books/by-publish-year-period", { "publish-year-min": publishYearMin, "publish-year-max": publishYearMax })
}

function getBook(id) {
    return get(SERVER_URL + "/book", { "id": id })
}

function createBookForm(title, bookObj, action) {
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
                "value": titleKey in bookObj ? bookObj[titleKey] : "",
                "required": true
            },
            [publishingHouseIdKey]: {
                "type": "list",
                "name": "Publishing House",
                "value": publishingHouseKey in bookObj ? bookObj[publishingHouseKey]["id"] : "",
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
                "value": publishYearBookKey in bookObj ? bookObj[publishYearBookKey] : "",
                "required": true,
                "name": "Publish year"
            },
            [commentKey]: {
                "type": "text",
                "name": "Comment",
                "value": commentKey in bookObj ? bookObj[commentKey] : ""
            },

        },
        "ok": () => {
            let title = document.getElementById(titleKey)
            let publishingHouseId = document.getElementById(publishingHouseIdKey)
            let publishingHouseIdList = document.getElementById(publishingHouseIdKey + "-datalist")
            let publishYear = document.getElementById(publishYearKey)
            let comment = document.getElementById(commentKey)
            let id = 0
            for (let i in publishingHouseIdList.options) {
                if (publishingHouseIdList.options[i].value === publishingHouseId.value)
                    id = publishingHouseIdList.options[i].getAttribute("meta-data")
            }
            action({
                [titleKey]: title.value,
                [publishingHouseIdKey]: id,
                [publishYearKey]: publishYear.value,
                [commentKey]: comment.value
            })
        }
    })
}

function createBookFormCrate() {
    return createBookForm("Create new book", {}, (obj) => create(URLS.book, obj))
}

function createBookFormUpdate() {
    return createBookForm("Update the book", getBook(13), (obj) => {
        obj["id"] = 13
        update(URLS.book, obj)
    })
}