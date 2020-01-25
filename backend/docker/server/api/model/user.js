const mongoose = require('mongoose')
const bcrypt = require('bcrypt')
const saltRounds = 10
const Schema = mongoose.Schema

const userSchema = new Schema({
    id: {
        type: Number,
        required: true,
        trim: true,
        default: -1
    }, 
    nickname: {
        type: String,
        required: true,
        trim: true,
        default: 'Nickless'
    }, 
    name: {
        type: String,
        required: true,
        trim: true,
        default: 'Nameless'
    },
    lastName1: {
        type: String,
        required: true,
        trim: true,
        default: 'lastnameless'
    },
    lastName2: {
        type: String,
        trim: true
    },
    email: {
        type: String,
        required: true,
        trim: true,
        match: [/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/, 'Incorrect mail'],
        default: 'email@secondlife.com'
    },
    password: {
        type: String,
        required: true,
        trim: true
    },
    phone: {
        type: String,
        trim: true
    },
    type: {
        type: Number,
        trim: true,
        default: 0
    }

}, {versionKey: false})

userSchema.pre('save', function(next){
    this.password = bcrypt.hashSync(this.password, saltRounds);
    next();
})

module.exports = mongoose.model('User', userSchema)