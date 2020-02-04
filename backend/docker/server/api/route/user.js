const express = require('express')
const router = express.Router()
const userController = require('../controller/user')
const tokenAdmin = require('../miscellaneous/tokenAdmin')
const tokenUserAuth = require('../miscellaneous/tokenUserAuth')

router.post('/register', userController.create)
router.post('/login', userController.validate)
router.get('/user', tokenAdmin, userController.getAll)
router.get('/user/:id', tokenUserAuth, userController.getById)
router.put('/user/:id', tokenUserAuth, userController.update)
router.delete('/user/:id', tokenUserAuth, userController.delete)

module.exports = router