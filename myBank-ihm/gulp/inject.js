'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var inject = require('gulp-inject');
var wiredep = require('wiredep').stream;

gulp.task('inject', function () {

  var injectScripts = gulp.src([
    paths.src + '/{app,components}/**/*.js',
    paths.src + '/index.js'
  ]);

  var wiredepOptions = {
    directory: 'bower_components'
  };

   var injectOptions = {
      ignorePath: paths.src,
      addRootSlash: false
    };

  return gulp.src(paths.src + '/index.html')
    .pipe(inject(injectScripts, injectOptions))
    .pipe(wiredep(wiredepOptions))
    .pipe(gulp.dest(paths.tmp + '/serve'));

});
