var express = require('express');
var router = express.Router();
var passport = require('passport');

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()){
        return next();
    } else {
        res.redirect('/login');
    }
}

router.get('/profile', isLoggedIn, function(req, res, next) {
    res.render('index', { title: 'You are logged in.' });
});

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/login',function(req, res, next) {
  res.render('login', {});
});

router.post('/signup', passport.authenticate('signup', {
    successRedirect : '/',
    failureRedirect : '/login', //가입 실패시 redirect할 url주소
    failureFlash : true
}))
router.post('/login', passport.authenticate('login', {
    successRedirect : '/',
    failureRedirect : '/login', //로그인 실패시 redirect할 url주소
    failureFlash : true
}))


module.exports = router;
