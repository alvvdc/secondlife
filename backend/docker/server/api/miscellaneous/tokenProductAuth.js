const Jwt = require('jsonwebtoken')
const Token = require('../model/token')
const User = require('../model/user')

const tokenAuth = (req, res, next) => {

    const userid = req.body.publisher
    if (!userid)
        res.status(500).json({error : 'Error getting publisher id'})

    const token = req.headers['x-access-token']
    const secretKey = req.app.get('secretKey')
    Jwt.verify(token, secretKey, (err, decoded) => {

        if (err)
            res.status(500).json({status : 'error', message : err.message})


        checkToken(token, (responseToken) => {
            checkUser(userid, (responseUser) => {
                
                if (responseToken && responseUser && (responseToken.email === responseUser.email))
                    next()
                else
                    res.status(403).json({error : 'Login failed'})
            })
        })
    })   
}

const checkToken = (token, callback) => {
    Token.findOne({token : token}, (err, document) => {
        if (err)
            res.status(404).json({error : 'Login session error'})
        else {
            callback(document)
        }
    })
}

const checkUser = (userid, callback) => {
    User.findOne({_id : userid}, (err, document) => {
        if (err)
            res.status(404).json({error : 'Login session error'})
        else {
            callback(document)
        }
    })
}    

module.exports = tokenAuth