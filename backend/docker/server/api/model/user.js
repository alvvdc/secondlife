const mongoose = require('mongoose')
const bcrypt = require('bcrypt')
const saltRounds = 10
const Schema = mongoose.Schema

const userSchema = new Schema({
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
        default: 1
    },
    image: {
        type: String,
        trim: true
    }

}, {versionKey: false})

userSchema.pre('save', function(next){
    this.password = bcrypt.hashSync(this.password, saltRounds);
    next();
})

userSchema.pre('findOneAndUpdate', function(next){

    if (this._update.password != "")
        this._update.password = bcrypt.hashSync(this._update.password, saltRounds);
    else 
         delete this._update.password

    next();
})

module.exports = mongoose.model('User', userSchema)