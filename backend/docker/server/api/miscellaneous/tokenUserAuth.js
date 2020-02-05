const jwt = require('jsonwebtoken');
const token = require('../model/token')
const user = require('../model/user')

function tokenAuth(req, res, next) {

  jwt.verify(req.headers['x-access-token'], req.app.get('secretKey'), function (err, decoded) {

    if (err) res.json({ status: "error", message: err.message });
    else {
      token.findOne({ token: req.headers['x-access-token'] }, (err, tokenGiven) => {

        if (err) res.status(500).json({ error: 'Token error' + err })
        else if (tokenGiven) {

          user.findOne({ _id: req.params.id }, (err, userGiven) => {
            if (err) res.status(500).json({ error: 'Incorrect mail' })

            if (userGiven) {
              if (tokenGiven.email == userGiven.email) next();
              else res.status(404).json({ error: 'No tienes permisos' })
            }
            else res.status(404).json({ error: 'Incorrect mail' })
          })

        }
        else res.status(404).json({ error: 'Token error' + err })
      })
    }
  });
  
}

module.exports = tokenAuth
