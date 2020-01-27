const express = require('express')
const router = express.Router()
const userController = require('../controller/user')

router.post('/register', userController.create)
router.post('/login', userController.validate)
router.get('/user', userController.getAll)
router.get('/user/:id', userController.getById)
router.put('/user/:id', userController.update)
router.delete('/user/:id', userController.delete)

module.exports = router