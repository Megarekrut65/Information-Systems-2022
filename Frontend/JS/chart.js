function loadColumnChart(){
    createColumnChart("chartContainer", getStatisticChartProperties())
}
function createColumnChart(parentId, properties){
    var chart = new CanvasJS.Chart(parentId, columnChartPropertyBuilder(properties));
    chart.render()
}
function createColumnDataProperties(name, list, labels){
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
function columnChartPropertyBuilder(properties){
    let pr = {            
        title:{
            text: properties.title             
        },
        interactivityEnabled: false,
        data: []
    }
    for(let key in properties.lists){
        pr.data.push(createColumnDataProperties(key, properties.lists[key], properties.labels))
    }
    return pr
}
function createPieChart(parentId, properties){
    console.log(pieChartPropertyBuilder(properties))
    var chart = new CanvasJS.Chart(parentId, pieChartPropertyBuilder(properties));
    chart.render()
}
function createPieDataProperties(name, list){
    let data = {      
        type: "pie",
        showInLegend: true,
        legendText: "{indexLabel}",
        dataPoints: []
    }
    for(let i in list){
        data.dataPoints.push({indexLabel:name, y:list[i]})
    }
    return data
}
function pieChartPropertyBuilder(properties){
    let pr = {            
        title:{
            text: properties.title             
        },
        interactivityEnabled: true,
        data: [{      
            type: "pie",
            showInLegend: true,
            legendText: "{indexLabel}",
            dataPoints: []
        }],
        backgroundColor:"#f7ede2"
    }
    for(let key in properties.lists){
        pr.data[0].dataPoints.push({indexLabel:key, y:properties.lists[key]})
    }
    return pr
}