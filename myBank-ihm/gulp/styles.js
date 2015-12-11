'use strict';

var gulp = require('gulp');
var inject = require('gulp-inject');
var less = require('gulp-less')
var filter = require('gulp-filter');
var paths = gulp.paths;

gulp.task('styles', ['clean'], function () {

  var injectFiles = gulp.src([
    paths.src + '/{app,components}/**/*.less'
  ]);

  var injectOptions = {
    ignorePath: paths.src,
    transform: function(filePath) {
      return '@import \'' + filePath + '\';';
    },
    starttag: '// injector',
    endtag: '// endinjector',
    addRootSlash: false
  };

  var indexFilter = filter('index.less', {restore: true});

  return gulp.src([
    paths.src + '/index.less',
    paths.src + '/vendor.less'
    ])
    .pipe(indexFilter)
    .pipe(inject(injectFiles, injectOptions))
    .pipe(indexFilter.restore)
    .pipe(less())
    .pipe(gulp.dest(paths.tmp + '/styles'));
});
