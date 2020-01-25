const mongoose = require('mongoose')

const ObjectId = mongoose.ObjectId
const Schema = mongoose.Schema

const productSchema = require('./product')

const categorySchema = new Schema({
    _id : {
        type: ObjectId,
        required: true
    },
    category : {
        type: String,
        required: true,
        trim: true,
        unique: true
    },
    products : [productSchema]
})

module.exports = mongoose.model('Category', categorySchema)