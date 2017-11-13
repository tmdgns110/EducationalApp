var express = require('express');
var router = express.Router();
var passport = require('passport');



/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/login',function(req, res, next) {
  res.render('login', {});
});

router.post('/signup', passport.authenticate('signup', {
    successRedirect : '/profile',
    failureRedirect : '/', //가입 실패시 redirect할 url주소
    failureFlash : true
}))
router.post('/login', passport.authenticate('login', {
    successRedirect : '/profile',
    failureRedirect : '/', //로그인 실패시 redirect할 url주소
    failureFlash : true
}))


module.exports = router;
