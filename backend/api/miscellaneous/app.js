const express = require('express')
const bodyParser = require('body-parser')
const userRoutes = require('../route/user')
const app = express()

app.use(bodyParser.json)
app.use('/user', userRoutes)
app.set('secretKey123')

module.exports = app
