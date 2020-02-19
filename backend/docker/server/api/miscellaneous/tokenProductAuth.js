const Jwt = require('jsonwebtoken')
const Token = require('../model/token')
const User = require('../model/user')
const Product = require('../model/product')

const tokenAuth = (req, res, next) => {

    const userid = req.body.publisher
    if (!userid)
        res.status(500).json({error : 'Error getting publisher id'})

    const token = req.headers['x-access-token']
    const secretKey = req.app.get('secretKey')
    Jwt.verify(token, secretKey, (err, decoded) => {
        if (err)
            res.status(500).json({status : 'error', message : err.message})
    })

    let responseToken
    Token.findOne({token : token}, (err, document) => {
        if (err)
            res.status(404).json({error : 'Login session error'})
        else {
            responseToken = document
            checkAuth(responseToken, responseUser)
        }
    })

    let responseUser
    User.findOne({_id : userid}, (err, document) => {
        if (err)
            res.status(404).json({error : 'Login session error'})
        else {
            responseUser = document
            checkAuth(responseToken, responseUser)
        }
    })
    
    const checkAuth = (tokenArg, userArg) => {

        if (tokenArg && userArg)
        {
            if (tokenArg.email === userArg.email)
                next()
            else
                res.status(403).json({error : 'Login failed'})
        }
    }
}

module.exports = tokenAuth