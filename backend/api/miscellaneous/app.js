const express = require('express')
const bodyParser = require('body-parser')
const userRoutes = require('../route/user')
const app = express()

app.set('secretKey', 'vivaManolo')
app.use(bodyParser.json())
app.use('/user', userRoutes)

module.exports = app
