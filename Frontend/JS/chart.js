function loadChart(){
    createChart("chartContainer", getStatisticChartProperties())
}
function createChart(parentId, properties){
    var chart = new CanvasJS.Chart(parentId, chartPropertyBuilder(properties));
    chart.render()
}
function createDataProperties(name, list, labels){
    let data = {      
        type: "column",
        name: name,
        indexLabel: "{y}",
        showInLegend: true,
        dataPoints: []
    }
    for(let i in list){
        data.dataPoints.push({label:labels[i], y:list[i]})
    }
    return data
}
function chartPropertyBuilder(properties){
    let pr = {            
        title:{
            text: properties.title             
        },
        interactivityEnabled: false,
        data: []
    }
    for(let key in properties.lists){
        pr.data.push(createDataProperties(key, properties.lists[key], properties.labels))
    }
    return pr
}