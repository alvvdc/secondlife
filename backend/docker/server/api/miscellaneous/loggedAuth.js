const Jwt = require('jsonwebtoken')
const Token = require('../model/token')

const tokenAuth = (req, res, next) => {

    const token = req.headers['x-access-token']
    const secretKey = req.app.get('secretKey')
    Jwt.verify(token, secretKey, (err, decoded) => {
        if (err)
            res.status(500).json({status : 'error', message : err.message})
        else 
        {
            Token.findOne({token : token}, (err, document) => {
                if (err)
                    res.status(403).json({error : 'The sent token does not exist'})
                else 
                    next()
            })
        }
    })
}

module.exports = tokenAuth