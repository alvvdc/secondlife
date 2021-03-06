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

        const filenames = writeImagesSync(req.body.images)

        const updateProduct = {
            _id : req.params.id,
            publisher : new mongoose.Types.ObjectId(req.body.publisher),
            category : req.body.category,
            title : req.body.title,
            description : req.body.description,
            price : req.body.price,
            images : filenames,
            isSold : req.body.isSold
        }

        product.findByIdAndUpdate(updateProduct._id, updateProduct, (err, result) => {
            if (err)
                res.status(404).json({error : err})
            else   
                res.status(200).json(result)
        })
    },

    getProductById : (req, res) => {
        const id = req.params.id

        product.findById(id, (err, result) => {
            if (err)
                res.status(404).json({error : err})
            else
                res.status(200).json(convertProductImagesToBase64(result))
        })
    },

    getAllProducts : (req, res) => {
        product.find({}, (err, result) => {
            if (err) {
                res.status(500).json({error : err})
            } else {
                res.status(200).json(convertProductListImagesToBase64(result))
            }
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
    },

    deleteProductById : (req, res) => {
        const id = req.params.id

        product.findOneAndDelete({_id : id}, (err, result) => {
            if (err)
            res.status(404).json({error : err})
                else
            res.status(200).json(result)
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

const convertProductImagesToBase64 = (product) => {    
    if (product == null)
        return product
    
    const productImages = product.images.map((productImage) => {

        const base64 = images.readImageSync(productImage)
        return base64
    })
    const newProduct = product
    newProduct.images = productImages
    return newProduct
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

    for (let image of imagesArray) {
        imagesFilenames.push(images.writeImageSync(image))
    }
    return imagesFilenames
}