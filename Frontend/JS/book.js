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
    document.getElementsByTagName('body')[0].appendChild(createBookFormCreate((data) => findButton()))
}

function functionButton(form) {
    document.getElementsByTagName('body')[0].appendChild(form((data) => findButton()))
}