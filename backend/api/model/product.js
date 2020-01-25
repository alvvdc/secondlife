const mongoose = require('mongoose')

const ObjectId = mongoose.ObjectId
const Schema = mongoose.Schema

const productSchema = new Schema({
    _id : {
        type: ObjectId,
        required: true
    },
    publisher : {
        type: ObjectId,
        required: true
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
})

//module.exports = mongoose.model('Product', productSchema)
module.exports = productSchema