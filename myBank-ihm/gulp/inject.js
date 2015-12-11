'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var inject = require('gulp-inject');
var wiredep = require('wiredep').stream;

gulp.task('inject', [ 'clean', 'styles'], function () {

  var injectVendorStyles = gulp.src([
    paths.tmp + '/styles/vendor.css'
  ]);

  var injectIndexStyles = gulp.src([
      paths.tmp + '/styles/index.css'
    ]);

  var injectScripts = gulp.src([
    paths.src + '/index.js',
    paths.src + '/{app,components}/**/*.js'
  ]);

  var wiredepOptions = {
    directory: 'bower_components'
  };

  var injectScriptOptions = {
    ignorePath: paths.src,
    addRootSlash: false
  };

  return gulp.src(paths.src + '/index.html')
    .pipe(inject(injectScripts, injectScriptOptions))
    .pipe(inject(injectIndexStyles, {
      ignorePath: paths.tmp,
      addRootSlash: false,
      starttag: '<!-- inject:index:{{ext}} -->'
    }))
    .pipe(inject(injectVendorStyles, {
      ignorePath: paths.tmp,
      addRootSlash: false,
      starttag: '<!-- inject:vendor:{{ext}} -->'
    }))
    .pipe(wiredep(wiredepOptions))
    .pipe(gulp.dest(paths.tmp + '/serve'));

});
