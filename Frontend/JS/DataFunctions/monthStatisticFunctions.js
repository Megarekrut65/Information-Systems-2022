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
    return monthStatisticsToTableProperties((page, perPage)=>
    getPage(URLS.monthStatisticsByPeriodPage, page, perPage, { "month-date-min": item.min, "month-date-max": item.max }))
}
function getAllMonthStatisticsForTable() {
    return monthStatisticsToTableProperties((page, perPage)=>getAll(URLS.monthStatistics))
}
function monthStatisticsToTableProperties(getter) {
    let convector = obj=> {{
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
    }}
    return {
        "convector":convector,
        "getter":getter
    }
}
function createMonthStatisticView(statistic) {
    let startDate = new Date(statistic.monthDate)
    startDate.setDate(1)
    let endDate = statistic.monthDate
    let item = { "min": getTextDate(startDate), "max": endDate }
    let delta = getDeltaMonthStatistic(statistic)
    let borrowedTitle = delta["Borrowed/Returned"] >= 0?"Borrowed":"Returned"
    return createObjectView({
        "title": "Statistic for " + getMonthYearDate(statistic.monthDate),
        "fields": {
            "Book total count": statistic.booksTotalCount,
            "Book available count": statistic.booksAvailableCount,
            "Book purchasing count": statistic.booksPurchasingCount,
            "Book borrowing count": statistic.booksBorrowingCount,
            "Book write-off count": statistic.booksWriteOffCount,
            "Book lost count": statistic.booksLostCount,
            "Purchased": delta["Purchased"],
            [borrowedTitle]: Math.abs(delta["Borrowed/Returned"]),
            "Write-offed": delta["Write-offed"],
            "Lost": delta["Lost"]
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
function getDeltaMonthStatistic(statistic){
    let prevDate = new Date(statistic.monthDate)
    prevDate.setDate(0)
    let prevMonth = get(URLS.monthStatisticClosest, {
        "month-date": getTextDate(prevDate)
    })
    return{
        "Purchased": statistic.booksPurchasingCount - prevMonth.booksPurchasingCount,
        "Borrowed/Returned": statistic.booksBorrowingCount - prevMonth.booksBorrowingCount,
        "Write-offed": statistic.booksWriteOffCount - prevMonth.booksWriteOffCount,
        "Lost": statistic.booksLostCount - prevMonth.booksLostCount
    }
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
        let del = getDeltaMonthStatistic(statistic)
        del["Borrowed/Returned"] = Math.abs(del["Borrowed/Returned"])
        deltas.push(del)
    }
    let lists = {}
    for(let i in deltas){
        let statistic = deltas[i]
        for(let key in statistic){
            if(!(key in lists)) lists[key] = []
            lists[key].push(statistic[key])
        }
    }
    let firstDate = new Date(data[0].monthDate)
    firstDate.setDate(1)
    return {
        "title": "Statistic from " + getTextDate(firstDate) + " to " + data[data.length - 1].monthDate,
        "lists":lists,
        "labels":labels
    }
}
function getStatisticPieChartProperties(statistic){
    return {
        "title": "Statistic for " + getMonthYearDate(statistic.monthDate),
        "lists":getDeltaMonthStatistic(statistic)
    }
}