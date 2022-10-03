function createCards() {
    let place = document.getElementById("card-place")
    for (let i in indexProperties) {
        place.appendChild(createCard(indexProperties[i]))
    }
}

function createCard(properties) {
    let card = document.createElement("div")
    card.className = "card"
    card.onclick = () => {
        window.location = properties.location
        return false
    }
    let img = document.createElement("img")
    img.className = "card-image"
    img.src = properties.img
    img.alt = properties.title
    card.appendChild(img)
    let text = document.createElement("div")
    text.className = "card-text"
    text.textContent = properties.title
    card.appendChild(text)
    let cardList = document.createElement("div")
    cardList.className = "card-list"
    let list = properties.list.concat(["..."])
    for (let i in list) {
        let item = document.createElement("div")
        item.textContent = "\u25b6 " + list[i]
        cardList.appendChild(item)
    }
    card.appendChild(cardList)
    return card
}