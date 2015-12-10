'use strict';

var gulp = require('gulp');
var browserSync = require('browser-sync');
var reload = browserSync.reload;
var paths = {
  scripts: ['src/**/*.html', 'src/**/*.js']
};

gulp.task('watch', function() {
    gulp.watch(paths.scripts, reload);
});