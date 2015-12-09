'use strict';

var gulp = require('gulp');
var del = require('del');
var inject = require('gulp-inject');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var minifyHtml = require('gulp-minify-html')
var templateCache = require('gulp-angular-templatecache');
var paths = gulp.paths;

gulp.task('templates', function () {
  gulp.src(paths.src + '/{app,components}/**/*.html')
    .pipe(minifyHtml({
      empty: true,
      spare: true,
      quotes: true
    }))
    .pipe(templateCache('templateCacheHtml.js', {
      module: 'MyBankApp'
    }))
    .pipe(gulp.dest(paths.tmp));
});

gulp.task('build-styles', function() {
    // the return is important!
    return gulp.src(paths.src + '/{app,components}/**/*.less')
            .pipe(less())
            .pipe(gulp.dest('dist/font'));
});


gulp.task('build-js', ['templates'], function() {
    // the return is important if you want proper dependencies!
    return gulp.src([paths.src + '/index.js', paths.src + '/{app,components}/**/*.js'])
            .pipe(concat('myBank.min.js'))
            .pipe(uglify())
            .pipe(gulp.dest('dist/script'));
});

gulp.task('clean', function (done) {
  del([paths.dist + '/', paths.tmp + '/'], done);
});

gulp.task('html', ['inject', 'templates'], function () {
  var partialsInjectFile = gulp.src(paths.tmp + '/templateCacheHtml.js', { read: false });

  var htmlFilter = $.filter('*.html');
  var jsFilter = $.filter('**/*.js');
  var cssFilter = $.filter('**/*.css');
  var assets;

  return gulp.src(paths.tmp + '/serve/*.html')
    .pipe(inject(partialsInjectFile, partialsInjectOptions))
    .pipe(assets = $.useref.assets())
    .pipe($.rev())
    .pipe(jsFilter)
    .pipe($.ngAnnotate())
    .pipe($.uglify({preserveComments: $.uglifySaveLicense, mangle: false, compress: false}))
    .pipe(jsFilter.restore())
    .pipe(cssFilter)
    .pipe($.csso())
    .pipe(cssFilter.restore())
    .pipe($.replace('bower_components/bootstrap/fonts', 'fonts'))
    .pipe(assets.restore())
    .pipe($.useref())
    .pipe($.revReplace())
    .pipe(htmlFilter)
    .pipe($.minifyHtml({
      empty: true,
      spare: true,
      quotes: true
    }))
    .pipe(htmlFilter.restore())
    .pipe(gulp.dest(paths.dist + '/'))
    .pipe($.size({ title: paths.dist + '/', showFiles: true }));
});

gulp.task('build', ['clean', 'build-js', 'templates']);