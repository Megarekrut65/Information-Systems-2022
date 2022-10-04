function getAllMonthStatistics() {
    return getAll(SERVER_URL + "/month-statistics")
}

function findMonthStatisticsByMonthDatePeriod(monthDateMin, monthDateMax) {
    return get(SERVER_URL + "/month-statistics/by-month-date-period", { "month-date-min": monthDateMin, "month-date-max": monthDateMax })
}

function getMonthStatistic(id) {
    return get(SERVER_URL + "/month-statistic", { "id": id })
}

function getMonthYearDate(date) {
    const d = new Date(date)
    return d.toLocaleString('en-GB', { month: 'long' }) + " " + d.getFullYear()
}

function getMonthStatisticsForTable(minDate, maxDate) {
    return get(URLS.monthStatisticsByPeriod, { "month-date-min": minDate, "month-date-max": maxDate })
        .sort((a, b) => b.monthDate.localeCompare(a.monthDate))
        .map(obj => {
            return {
                "Id": obj.id,
                "Date": {
                    "text": getMonthYearDate(obj.monthDate),
                    "id": obj.id,
                    "object": "MonthStatistic",
                    "type": "reference"
                },
                "Book total": obj.booksTotalCount,
                "Book available": obj.booksAvailableCount,
                "Book purchasing": obj.booksPurchasingCount,
                "Book borrowing": obj.booksBorrowingCount,
                "Book write-off": obj.booksWriteOffCount,
                "Book lost": obj.booksLostCount,
            }
        })
}

function createMonthStatisticView(statistic) {
    let startDate = new Date(statistic.monthDate)
    startDate.setDate(1)
    let endDate = statistic.monthDate
    return createObjectView({
        "title": "Statistic for " + getMonthYearDate(statistic.monthDate),
        "tables": {
            "Deliveries": getDeliveriesForTable('', getTextDate(startDate), endDate),
            "Borrowings": getBookBorrowingsForTable('', '', getTextDate(startDate), endDate)
        }
    })
}