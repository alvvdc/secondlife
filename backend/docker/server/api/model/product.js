const mongoose = require('mongoose')

const Schema = mongoose.Schema

const productSchema = new Schema({
    publisher : {
        type: mongoose.ObjectId,
        required: true
    },
    category : {
        type: String,
        enum: ['MOTOR', 'INMOBILIARIA', 'EMPLEO', 'SERVICIOS', 'OTROS'],
        required: true,
        default: 'OTROS'
    },
    title : {
        type: String,
        required: true,
        trim: true
    },
    description : {
        type: String,
        required: true,
        trim: true
    },
    price : {
        type: Number,
        required: true
    },
    images : {
        type: [String]
    },
    isSold : {
        type: Boolean,
        default: false
    }
}, {versionKey : false})

module.exports = mongoose.model('Product', productSchema)