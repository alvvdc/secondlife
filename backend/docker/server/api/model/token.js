const mongoose = require('mongoose')
const Schema = mongoose.Schema

const tokenSchema = new Schema({
    token: {
        type: String,
        required: true,
        trim: true
    }, 
    email: {
        type: String,
        required: true,
        trim: true,
        match: [/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/, 'Incorrect mail']
    },
    expiration: {
        type: Date,
        required: true,
        trim: true
    }

}, {versionKey: false})

module.exports = mongoose.model('Token', tokenSchema)