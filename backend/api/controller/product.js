const mongoose = require('mongoose')

const product = require('../model/product')

module.exports = {
    insertProduct : (req, res) => {
        const newProduct = {
            publisher : new mongoose.Types.ObjectId(req.body.publisher),
            category : req.body.category,
            title : req.body.title,
            description : req.body.description,
            price : req.body.price,
            images : req.body.images,
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
            if (err)
                res.status(500).json({error : err})
            else 
                res.status(200).json(result)
        })
    },

    getUnsoldProductsByCategory : (req, res) => {
        const category = req.params.category

        product.find({category : category, isSold : false}, (err, result) => {
            if (err)
                res.status(500).json({error : err})
            else
                res.status(200).json(result)
        })
    }
}