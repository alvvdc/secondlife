const user = require('../model/user')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')

module.exports = {

    create: (req, res, next) => {
        const newUser = {
            id: req.body.id,
            nickname: req.body.nickname,
            name: req.body.name,
            lastName1: req.body.lastName1,
            lastName2: req.body.lastName2,
            email: req.body.email,
            password: req.body.password,
            phone: req.body.phone,
            type: req.body.type
        }
        user.create(newUser, (err, result) => {
            if (err) 
                res.status(500).json({error: 'Server failture'})
            else
                res.status(201).json(result)
        })
    },

    validate: (req, res, next) => {       

        user.findOne({email: req.body.email}, (err, userGiven) => {
            if (err) {
                res.status(500).json({error: 'Incorrect mail'})
            }          
            
            if (userGiven) {
                if (bcrypt.compareSync(req.body.password, userGiven.password)){
                    const token = jwt.sign({id: userGiven._id}, req.app.get('secretKey'), { expiresIn: '1h' });  
                                     
                    res.json({
                        status: 'Sucess',
                        token: token
                    })
                } else 
                    res.status(404).json({error: 'Incorrect password'})
            } 
            else 
                res.status(404).json({error: 'Incorrect mail'})
        })
    }

}