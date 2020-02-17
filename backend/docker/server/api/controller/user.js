const user = require('../model/user')
const product = require('../model/product')
const token = require('../model/token')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
const images = require('./images')

module.exports = {

    create: (req, res, next) => {

        const newUser = {
            nickname: req.body.nickname,
            name: req.body.name,
            lastName1: req.body.lastName1,
            lastName2: req.body.lastName2,
            email: req.body.email,
            password: req.body.password,
            phone: req.body.phone,
            type: req.body.type,
            image: "default.png"
        }
        user.create(newUser, (err, result) => {
            if (err) 
                res.status(500).json({error: 'Server failture ' + err})
            else
                res.status(201).json(result)
        })
    },

    validate: (req, res) => {              

        user.findOne({email: req.body.email}, (err, givenUser) => {
            if (err) res.status(500).json({error: 'Incorrect mail'})        
            
            if (givenUser) {
                if (bcrypt.compareSync(req.body.password, givenUser.password)){
                    const tokenString = jwt.sign({_id: givenUser._id}, req.app.get('secretKey'), { expiresIn: '365d' })
                    
                    const tokenCreated = {
                        token: tokenString,
                        email: req.body.email,
                        expiration: new Date().setFullYear(new Date().getFullYear()+1)
                    }

                    token.create(tokenCreated, (err) => {
                        if (err) 
                            console.log('Token save failture ' + err)
                    })
   
                    res.json({
                        token: tokenString,
                        userId: givenUser._id
                    })

                } else 
                    res.status(404).json({error: 'Incorrect password'})
            } 
            else 
                res.status(404).json({error: 'Incorrect mail'})
        })
    },

    getById: (req, res) => {                   
        user.findOne({_id: req.params.id}, (err, givenUser) => {            
            if (err) return res.status(500).json({error: 'Incorrect user' + err})
            if (!givenUser) return res.status(404).json({status: 'No user'}) 
            res.status(200).json(convertImageToBase64(givenUser))
        })
    },

    getAll: (req, res) => {

        user.find({}, (err, users) => {
            if (err) return res.status(500).json({error: 'Error getting users'})
            if (!users) return res.status(404).json({status: 'No users'}) 
            res.status(200).json({users})
        })
    },

    update: (req, res) => {
        
        const userEdit = req.body
        userEdit.image = images.writeUserImageSync(req.body.image)

        user.findOneAndUpdate({_id: req.params.id}, userEdit, (err, givenUser) => {
            if (err) res.status(500).json({error: 'Error updating user'})
            else res.status(200).json(convertImageToBase64(givenUser))
        })

    },

    delete: (req, res) => {

        user.findOne({_id: req.params.id}, (err, givenUser) => {
            if (err) return res.status(500).json({error: 'Incorrect user'})

            if (givenUser) {
                givenUser.remove(err => {
                    if (err) return res.status(500).json({error: 'Error deleting user'})
                    res.status(200).json('User deleted')
                })
            }
        } )
    },

    getUserProducts: (req, res) => {
        
        const userId = req.params.id
        product.find({publisher : userId}, (err, doc) => {

            if (err) {
                return res.status(500).json(err)
            } else {
                res.status(200).json(doc)
            }
        })
    }

}

const convertImageToBase64 = (user) => {
    const newUser = user
    newUser.image = images.readUserImageSync(user.image)
    return newUser
}
