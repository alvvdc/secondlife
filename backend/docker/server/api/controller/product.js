const mongoose = require('mongoose')

const product = require('../model/product')
const images = require('./images')

module.exports = {
    insertProduct : (req, res) => {

        const filenames = writeImagesSync(req.body.images)
        
        const newProduct = {
            publisher : new mongoose.Types.ObjectId(req.body.publisher),
            category : req.body.category,
            title : req.body.title,
            description : req.body.description,
            price : req.body.price,
            images : filenames,
            isSold : req.body.isSold
        }

        product.create(newProduct, (err, result) => {
            if (err)
                res.status(500).json({error : err})
            else
                res.status(201).json(result)
        })
    },

    updateProduct : (req, res) => {

        product.findByIdAndUpdate(req.params.id, req.body, (err, result) => {
            if (err)
                res.status(404).json({error : err})
            else   
                res.status(200).json(result)
        })
    },

    getUnsoldProducts : (req, res) => {

        product.find({isSold : false}, (err, result) => {
            if (err) {
                res.status(500).json({error : err})
            } else {
                res.status(200).json(convertProductListImagesToBase64(result))
            }
        })
    },

    getUnsoldProductsByCategory : (req, res) => {
        const category = req.params.category

        product.find({category : category, isSold : false}, (err, result) => {
            if (err)
                res.status(500).json({error : err})
            else
                res.status(200).json(convertProductListImagesToBase64(result))
        })
    }
}

const convertProductListImagesToBase64 = (productsList) => {
    return productsList.map((product) => {                    
        const productImages = product.images.map((productImage) => {

            const base64 = images.readImageSync(productImage)
            return base64
        })
        const newProduct = product
        newProduct.images = productImages
        return newProduct
    })
}

const writeImages = (imagesArray, callback) => {

    const imagesFilenames = []

    if (imagesArray.length > 0) {
        const image = imagesArray[0]
        images.writeImage(image, (filename) => {
            imagesFilenames.push(filename)
            return callback(imagesFilenames)
        })
    }
    return callback(imagesFilenames)
}

const writeImagesSync = (imagesArray) => {
    const imagesFilenames = []

    if (imagesArray.length > 0) {
        const image = imagesArray[0]
        imagesFilenames.push(images.writeImageSync(image))
    }
    return imagesFilenames
}