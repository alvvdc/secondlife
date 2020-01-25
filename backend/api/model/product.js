const mongoose = require('mongoose')

const Schema = mongoose.Schema

const productSchema = new Schema({
    publisher : {
        type: mongoose.ObjectId,
        required: true
    },
    category : {
        type: String,
        enum: ['motor', 'inmobiliaria', 'empleo', 'servicios', 'otros'],
        required: true,
        default: 'otros'
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
    }
}, {versionKey : false})

module.exports = mongoose.model('Product', productSchema)