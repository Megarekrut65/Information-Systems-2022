function getAllMonthStatistics() {
    return getAll(SERVER_URL + "/month-statistics")
}

function findMonthStatisticsByMonthDatePeriod(monthDateMin, monthDateMax) {
    return get(SERVER_URL + "/month-statistics/by-month-date-period", { "month-date-min": monthDateMin, "month-date-max": monthDateMax })
}

function getMonthStatistic(id) {
    return get(SERVER_URL + "/month-statistic", { "id": id })
}