function createAuthorshipForm(title, obj, action, toSendData) {
    const roleNameKey = "author-role",
        bookIdKey = "book-id",
        authorIdKey = "author-id"
    return createForm({
        "title": title,
        "inputs": {
            [roleNameKey]: {
                "type": "text",
                "name": "Role",
                "value": "",
                "required": true
            },
            [bookIdKey]: {
                "type": "text",
                "name": "Book",
                "value": obj.title,
                "readOnly": true
            },
            [authorIdKey]: {
                "type": "list",
                "name": "Author",
                "value": "",
                "list": getAll(URLS.authors),
                "required": true,
                "plus": () => {
                    addOptionToList(createAuthorFormCrate, authorIdKey)
                }
            }
        },
        "ok": () => {
            let roleName = document.getElementById(roleNameKey)
            toSendData(action({
                [roleNameKey]: formatText(roleName.value),
                [bookIdKey]: obj.id,
                [authorIdKey]: getDataFromList(authorIdKey)
            }))
        }
    })
}

function createAuthorshipFormCrate(book, toSendData) {
    return createAuthorshipForm("Create new authorship", book, (obj) => create(URLS.authorship, obj), toSendData)
}