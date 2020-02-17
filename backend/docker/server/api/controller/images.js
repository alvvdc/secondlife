const fs = require('fs')
const properties = require('../config/properties')


let lastDate
let index = 0

const getNewFilename = () => {
    const date = new Date().valueOf()

    if (date == lastDate) index++
    else index = 0

    lastDate = date
    return `${date}-${index}.jpeg`
}


module.exports.writeImage = (base64, callback) => {
    const buffer = new Buffer(base64, 'base64')
    const filename = getNewFilename()

    fs.writeFile(`${properties.productImagesPath}${filename}`, buffer, () => {
        callback(filename)
    })
}

module.exports.writeImageSync = (base64) => {
    const buffer = new Buffer(base64, 'base64')
    const filename = getNewFilename()

    fs.writeFileSync(`${properties.productImagesPath}${filename}`, buffer)
    return filename
}

module.exports.readImage = (filename, callback) => {
    fs.readFile(`${properties.productImagesPath}${filename}`, (err, data) => {

        const base64 = new Buffer(data).toString('base64')
        callback(err, base64)
    })
}

module.exports.readImageSync = (filename) => {
    const data = fs.readFileSync(`${properties.productImagesPath}${filename}`)
    return new Buffer(data).toString('base64')
}