function createBookGenreForm(title, obj, action, toSendData) {
    const bookKey = "book-id",
        genreKey = "genre-id"
    return createForm({
        "title": title,
        "inputs": {
            [bookKey]: {
                "type": "text",
                "name": "Book",
                "value": obj.title,
                "readOnly": true
            },
            [genreKey]: {
                "type": "list",
                "name": "Genre",
                "value": "",
                "required": true,
                "list": getAll(URLS.genres),
                "plus": () => {
                    addOptionToList(createGenreFormCrate, genreKey)
                }
            }
        },
        "ok": () => {
            toSendData(action({
                [bookKey]: obj.id,
                [genreKey]: getDataFromList(genreKey)
            }))
        }
    })
}

function createBookGenreFormCrate(book, toSendData) {
    return createBookGenreForm("Add genre to book", book, (obj) => create(URLS.bookGenre, obj), toSendData)
}