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
    document.getElementsByTagName('body')[0].appendChild(createAuthorFormCreate((data) => findButton()))
}

function functionButton(form) {
    document.getElementsByTagName('body')[0].appendChild(form({}, (data) => findButton()))
}