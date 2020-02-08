const mongoose = require('mongoose')

const Schema = mongoose.Schema

const productVisitsSchema = new Schema({
    product : {
        type: mongoose.ObjectId,
        required: true
    },
    visits : {
        type: Number,
        required: true,
        default: 1
    }
}, {versionKey : false})

module.exports = mongoose.model('ProductVisits', productVisitsSchema)