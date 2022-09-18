function getAllObjects() {
    console.log(getAllBooks())
}

function createObject() {
    console.log(createBook({ "title": "Math", "publishing-house-id": 1, "publish-year": 2002, "comment": "" }))
}

function updateObject() {
    console.log(updateBook({ "id": 3, "title": "Biology", "publishing-house-id": 2, "publish-year": 2006, "comment": "Bio bio" }))
}

function removeObject() {
    console.log(removePublishingHouse(4))
}

function findObject() {
    console.log(findBooksByTitle("Math"))
    console.log(findBooksByPublishingHouse(2))
}

function getObject() {
    console.log(getBook(1))
}