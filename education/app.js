var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var index = require('./routes/index');
var users = require('./routes/users');

var app = express();

var mongoose = require('mongoose');
var passport = require('passport');
mongoose.connect('localhost/test');
mongoose.Promise = global.Promise;
var db = mongoose.connection;
//데이터베이스 접속 확인
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) {
    console.log("mongo DB connected...")
});

app.post('/post', (req, res) => {
   console.log('who get in here post /users');
   var inputData;

   req.on('data', (data) => {
     inputData = JSON.parse(data);
   });

   req.on('end', () => {
     console.log("user_id : "+inputData.user_id + " , name : "+inputData.name);
   });

   res.write("OK!");
   res.end();
});

app.listen(3000, () => {
  console.log('Example app listening on port 3000!');
});



require('./config/passport')(passport);
app.use(passport.initialize());
app.use(passport.session()); //로그인 세션 유지
//플래시메세지를 사용한다면
var flash = require('connect-flash');
app.use(flash());

//2
var session = require('express-session');
app.use(session({
    secret: 'keyboard cat',
    resave: false,
    saveUninitialized: true
}));

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', index);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
