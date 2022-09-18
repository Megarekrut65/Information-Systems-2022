function getAllObjects() {
    console.log(getAllPublishingHouses())
}

function createObject() {
    console.log(createPublishingHouse({ "name": "Ranok", "address": "Kiev" }))
}

function updateObject() {
    console.log(updatePublishingHouse({ "id": 1, "name": "Avers", "address": "Lviv" }))
}

function removeObject() {
    console.log(removePublishingHouse(5))
}

function findObject() {
    console.log(findPublishingHousesByName("Ranok"))
}

function getObject() {
    console.log(getPublishingHouse(1))
}