function createBookGenreForm(title, obj, action, toSendData) {
    const bookKey = "book-id",
        genreKey = "genre-id"
    return createForm(commandList = {
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
                "plus": () => {}
            }
        },
        "ok": () => {
            let genre = document.getElementById(genreKey)
            let genreList = document.getElementById(genreKey + "-datalist")
            let id = getMetaDataFromDatalist(genreList, genre.value)
            toSendData(action({
                [bookKey]: obj.id,
                [genreKey]: id
            }))
        }
    })
}

function createBookGenreFormCrate(book, toSendData) {
    return createBookGenreForm("Add genre to book", book, (obj) => create(URLS.bookGenre, obj), toSendData)
}