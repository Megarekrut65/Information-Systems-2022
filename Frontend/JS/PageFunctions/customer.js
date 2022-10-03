function findButton() {
    addTableToPage('table', getCustomersForTable(document.getElementById('input-name').value,
        document.getElementById('input-phone').value, document.getElementById('input-email').value));
}

function clearButton() {
    document.getElementById('input-phone').value = ''
    document.getElementById('input-name').value = ''
    document.getElementById('input-email').value = ''
    findButton()
}

function addButton() {
    addToBody(createCustomerFormCreate((data) => findButton()))
}

function functionButton(form) {
    addToBody(form((data) => findButton()))
}