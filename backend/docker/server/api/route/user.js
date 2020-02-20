const express = require('express')
const router = express.Router()
const userController = require('../controller/user')
const tokenAdmin = require('../miscellaneous/tokenAdmin')
const tokenUserAuth = require('../miscellaneous/tokenUserAuth')
const loggedAuth = require('../miscellaneous/loggedAuth')

router.post('/register', userController.create)
router.post('/login', userController.validate)
router.get('/user', tokenAdmin, userController.getAll)
router.get('/user/:id', tokenUserAuth, userController.getById)
router.put('/user/:id', tokenUserAuth, userController.update)
router.delete('/user/:id', tokenUserAuth, userController.delete)
router.get('/user/:id/product', userController.getUserProducts)
router.get('/user/:id/contact', loggedAuth, userController.getContactInfoById)

module.exports = router