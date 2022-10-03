function findButton() {
    addTableToPage('table', getAuthorsForTable(document.getElementById('input-title').value,
        document.getElementById('input-author').value));
}

function clearButton() {
    document.getElementById('input-title').value = ''
    document.getElementById('input-author').value = ''
    findButton()
}

function addButton() {
    addToBody(createAuthorFormCreate((data) => findButton()))
}

function functionButton(form) {
    addToBody(form({}, (data) => findButton()))
}