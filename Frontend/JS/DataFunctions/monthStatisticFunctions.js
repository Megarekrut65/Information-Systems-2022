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

function getMonthStatisticsForTable(item) {
    item = normalizeItem(item, ["min", "max"])
    return get(URLS.monthStatisticsByPeriod, { "month-date-min": item.min, "month-date-max": item.max })
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
    let prevDate = new Date(statistic.monthDate)
    prevDate.setDate(0)
    let prevMonth = get(URLS.monthStatisticClosest, {
        "month-date": getTextDate(prevDate)
    })
    let startDate = new Date(statistic.monthDate)
    startDate.setDate(1)
    let endDate = statistic.monthDate
    let item = { "min": getTextDate(startDate), "max": endDate }
    let borrowed = statistic.booksBorrowingCount - prevMonth.booksBorrowingCount;
    let borrowedTitle = borrowed >= 0?"Borrowed":"Returned"
    return createObjectView({
        "title": "Statistic for " + getMonthYearDate(statistic.monthDate),
        "fields": {
            "Book total count": statistic.booksTotalCount,
            "Book available count": statistic.booksAvailableCount,
            "Book purchasing count": statistic.booksPurchasingCount,
            "Book borrowing count": statistic.booksBorrowingCount,
            "Book write-off count": statistic.booksWriteOffCount,
            "Book lost count": statistic.booksLostCount,
            "Purchased": statistic.booksPurchasingCount - prevMonth.booksPurchasingCount,
            [borrowedTitle]: Math.abs(borrowed),
            "Write-offed": statistic.booksWriteOffCount - prevMonth.booksWriteOffCount,
            "Lost": statistic.booksLostCount - prevMonth.booksLostCount
        },
        "tables": {
            "Deliveries": getDeliveriesForTable(item),
            "Borrowings": getBookBorrowingsForTable(item),
            "Write-offs": getBookWriteOffsForTable(item),
            "Losts": getBookLostsForTable(item)
        }
    })
}

function createMonthStatisticsSearch(recreateTable) {
    return {
        "inputs": {
            "min": {
                "type": "date",
                "label": "Begin"
            },
            "max": {
                "type": "date",
                "label": "End"
            }
        },
        "createTable": (item)=>recreateTable(getMonthStatisticsForTable(item)),
        "title": "Statistics"
    }
}

function createMonthStatisticFunction(toSendData) {
    return null
}
function getStatisticChartProperties(){
    var now = new Date();
    let today = new Date(now.getFullYear(), now.getMonth()+1, 1);
    let prevYear = new Date(today)
    prevYear.setFullYear(prevYear.getFullYear() - 1)
    let data = get(URLS.monthStatisticsByPeriod, { "month-date-min":  getTextDate(prevYear), "month-date-max": getTextDate(today) })
    .sort((a, b) => a.monthDate.localeCompare(b.monthDate))
    if(data.length == 0) return {}
    let deltas = [], labels = []
    for(let i in data){
        let statistic = data[i]
        let prevDate = new Date(statistic.monthDate)
        labels.push(prevDate.toLocaleString('en-GB', { month: 'short' }))
        prevDate.setDate(0)
        let prevMonth = get(URLS.monthStatisticClosest, {
            "month-date": getTextDate(prevDate)
        })
        deltas.push({
            "Purchased": statistic.booksPurchasingCount - prevMonth.booksPurchasingCount,
            "Borrowed/Returned": Math.abs(statistic.booksBorrowingCount - prevMonth.booksBorrowingCount),
            "Write-offed": statistic.booksWriteOffCount - prevMonth.booksWriteOffCount,
            "Lost": statistic.booksLostCount - prevMonth.booksLostCount
        })
    }
    let lists = {}
    for(let i in deltas){
        let statistic = deltas[i]
        for(let key in statistic){
            if(!(key in lists)) lists[key] = []
            lists[key].push(statistic[key])
        }
    }
    return {
        "title": "Statistic from " + data[0].monthDate + " to " + data[data.length - 1].monthDate,
        "lists":lists,
        "labels":labels
    }
}