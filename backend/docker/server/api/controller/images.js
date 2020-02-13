const fs = require('fs')
const properties = require('../config/properties')

module.exports.writeImage = (base64, callback) => {
    const buffer = new Buffer(base64, 'base64')
    const filename = `${new Date().valueOf()}.jpeg`

    fs.writeFile(`${properties.productImagesPath}${filename}`, buffer, () => {
        callback(filename)
    })
}

module.exports.writeImageSync = (base64) => {
    const buffer = new Buffer(base64, 'base64')
    const filename = `${new Date().valueOf()}.jpeg`

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