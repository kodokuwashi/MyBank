'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var inject = require('gulp-inject');
var wiredep = require('wiredep').stream;

gulp.task('inject', function () {

  var injectScripts = gulp.src([
    paths.src + '/{app,components}/**/*.js'
  ]);

  var wiredepOptions = {
    directory: 'bower_components'
  };

  return gulp.src(paths.src + '/*.html')
    .pipe(inject(injectScripts))
    .pipe(wiredep(wiredepOptions))
    .pipe(gulp.dest(paths.tmp + '/serve'));

});
