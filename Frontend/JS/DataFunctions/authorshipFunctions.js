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
            [bookIdKey]: "title" in obj ? {
                "type": "text",
                "name": "Book",
                "value": obj.title,
                "readOnly": true
            } : {
                "type": "list",
                "name": "Book",
                "value": "",
                "list": getAll(URLS.books),
                "convector": item => {
                    return { "id": item.id, "name": item.title }
                },
                "required": true,
                "plus": (convector) => {
                    addOptionToList(createBookFormCreate, bookIdKey, convector)
                }
            },
            [authorIdKey]: "name" in obj ? {
                "type": "text",
                "name": "Author",
                "value": obj.name,
                "readOnly": true
            } : {
                "type": "list",
                "name": "Author",
                "value": "",
                "list": getAll(URLS.authors),
                "required": true,
                "plus": (convector) => {
                    addOptionToList(createAuthorFormCreate, authorIdKey, convector)
                }
            }
        },
        "ok": () => {
            let roleName = document.getElementById(roleNameKey)
            toSendData(action({
                [roleNameKey]: formatText(roleName.value),
                [bookIdKey]: "title" in obj ? obj.id : getDataFromList(bookIdKey),
                [authorIdKey]: "name" in obj ? obj.id : getDataFromList(authorIdKey)
            }))
        }
    })
}

function createAuthorshipFormCreate(obj, toSendData) {
    return createAuthorshipForm("Create new authorship", obj, createFunction(URLS.authorship), toSendData)
}