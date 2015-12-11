'use strict';

var gulp = require('gulp');
var del = require('del');
var inject = require('gulp-inject');
var minifyHtml = require('gulp-minify-html')
var templateCache = require('gulp-angular-templatecache');
var useref = require('gulp-useref');
var rev = require('gulp-rev');
var uglify = require('gulp-uglify');
var filter = require('gulp-filter');
var revReplace = require('gulp-rev-replace');
var uglifySaveLicense = require('uglify-save-license');
var ngAnnotate = require('gulp-ng-annotate');
var csso = require('gulp-csso');
var paths = gulp.paths;

gulp.task('partials', ['clean'], function () {
  gulp.src(paths.src + '/{app,components}/**/*.html')
    .pipe(minifyHtml({
      empty: true,
      spare: true,
      quotes: true
    }))
    .pipe(templateCache('templateHtml.js', {
      module: 'MyBankApp'
    }))
    .pipe(gulp.dest(paths.tmp + '/partials/'));
});

gulp.task('clean', function (done) {
  del([paths.dist + '/', paths.tmp + '/'], done);
});

gulp.task('html', ['clean', 'inject', 'partials'], function () {
  var partialsInjectFile = gulp.src(paths.tmp + '/partials/templateHtml.js', { read: false });
  var partialsInjectOptions = {
    starttag: '<!-- inject:partials -->',
    ignorePath: paths.tmp + '/partials',
    addRootSlash: false
  };

  var htmlFilter = filter('*.html', {restore: true});
  var jsFilter = filter('**/*.js', {restore: true});
  var cssFilter = filter('**/*.css', {restore: true});

  return gulp.src(paths.tmp + '/serve/index.html')
    .pipe(inject(partialsInjectFile, partialsInjectOptions))
    .pipe(useref())
    .pipe(jsFilter)
    .pipe(ngAnnotate())
    .pipe(uglify({preserveComments: uglifySaveLicense, mangle: false, compress: false}))
    .pipe(rev())
    .pipe(jsFilter.restore)
    .pipe(cssFilter)
    .pipe(csso())
    .pipe(rev())
    .pipe(cssFilter.restore)
    .pipe(revReplace())
    .pipe(gulp.dest(paths.dist + '/'));
});

gulp.task('build', ['clean', 'html', 'inject', 'partials', 'styles']);