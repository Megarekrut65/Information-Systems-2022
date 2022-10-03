function findButton() {
    addTableToPage('table', getMonthStatisticsForTable(
        document.getElementById('input-min').value,
        document.getElementById('input-max').value));
}

function clearButton() {
    document.getElementById('input-min').value = ''
    document.getElementById('input-max').value = ''
    findButton()
}

function functionButton(form) {
    document.getElementsByTagName('body')[0].appendChild(form((data) => findButton()))
}