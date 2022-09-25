function createAuthorshipForm(title, obj, action) {
    const roleNameKey = "author-role",
        bookIdKey = "book-id",
        authorIdKey = "author-id"
    const roleNameAuthorshipKey = "authorRole",
        bookIdAuthorshipKey = "book",
        authorIdAuthorshipKey = "author"
    return createForm(commandList = {
        "title": title,
        "inputs": {
            [roleNameKey]: {
                "type": "text",
                "name": "Role",
                "value": roleNameAuthorshipKey in obj ? obj[roleNameAuthorshipKey] : "",
                "required": true
            },
            [bookIdKey]: {
                "type": "list",
                "name": "Book",
                "value": bookIdAuthorshipKey in obj ? obj[bookIdAuthorshipKey]["id"] : "",
                "list": getAll(URLS.books).map(item => {
                    return { "name": item.title, "id": item.id }
                }),
                "required": true
            },
            [authorIdKey]: {
                "type": "list",
                "name": "Author",
                "value": authorIdAuthorshipKey in obj ? obj[authorIdAuthorshipKey]["id"] : "",
                "list": getAll(URLS.authors).map(item => {
                    return { "name": item.name, "id": item.id }
                }),
                "required": true
            }
        },
        "ok": () => {
            let roleName = document.getElementById(roleNameKey)
            let book = document.getElementById(bookIdKey)
            let bookList = document.getElementById(bookIdKey + "-datalist")
            let author = document.getElementById(authorIdKey)
            let authorList = document.getElementById(authorIdKey + "-datalist")
            let bookId = getMetaDataFromDatalist(bookList, book.value)
            let authorId = getMetaDataFromDatalist(authorList, author.value)
            action({
                [roleNameKey]: formatText(roleName.value),
                [bookIdKey]: bookId,
                [authorIdKey]: authorId
            })
        }
    })
}

function createAuthorshipFormCrate() {
    return createAuthorshipForm("Create new authorship", {}, (obj) => create(URLS.authorship, obj))
}

function createAuthorshipFormUpdate(id) {
    return createAuthorshipForm("Update the authorship", get(URLS.authorship, { "id": id }), (obj) => {
        obj["id"] = id
        update(URLS.authorship, obj)
    })
}