function getAllObjects() {
    console.log(getAllGenres())
}

function createObject() {
    console.log(createGenre({ "name": "Fantasy" }))
}

function updateObject() {
    console.log(updateGenre({ "id": 1, "name": "Western" }))
}

function removeObject() {
    console.log(removeGenre(5))
}

function find() {
    console.log(findAuthorByName("Marko"))
}