'use strict';

var gulp = require('gulp');
var browserSync = require('browser-sync');
var paths = gulp.paths;

// Static server
gulp.task('serve', ['build', 'watch'], function() {
    browserSync.init({
        server: {
            baseDir: paths.dist
        }
    });
});