'use strict';

var gulp = require('gulp');
var browserSync = require('browser-sync').create();
var paths = gulp.paths;

// Static server
gulp.task('serve', ['build'], function() {
    browserSync.init({
        server: {
            baseDir: paths.src,
            routes: {
                "/bower_components": "bower_components"
            }
        }
    });
});