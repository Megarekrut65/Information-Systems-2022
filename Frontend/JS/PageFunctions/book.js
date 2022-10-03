function findButton() {
    addTableToPage('table', getBooksForTable(document.getElementById('input-title').value,
        document.getElementById('input-genre').value, document.getElementById('input-tag').value,
        document.getElementById('input-author').value));
}

function clearButton() {
    document.getElementById('input-title').value = ''
    document.getElementById('input-genre').value = ''
    document.getElementById('input-tag').value = ''
    document.getElementById('input-author').value = ''
    findButton()
}

function addButton() {
    addToBody(createBookFormCreate((data) => findButton()))
}

function functionButton(form) {
    addToBody(form((data) => findButton()))
}