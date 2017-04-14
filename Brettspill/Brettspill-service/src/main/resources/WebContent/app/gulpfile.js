/**
 * Created by Espen on 11.04.2016.
 */

var browserify = require('browserify'),
    watchify = require('watchify'),
    gulp = require('gulp'),
    babelify = require('babelify'),
    source = require('vinyl-source-stream'),
    destFolder = './../../../../../target/classes/WebContent/app/js/dist/';

var gutil = require('gulp-util');
var tap = require('gulp-tap');
var buffer = require('gulp-buffer');
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');

// gulpfile.js
var Server = require('karma').Server;

// gulpfile.js
gulp.task('testContinous', function (done) {
    return new Server({
        configFile: __dirname + '/karma.conf.js',
        singleRun: false
    }, done).start();
});

function handleErrors() {
    var args = Array.prototype.slice.call(arguments);
    notify.onError({
        title: 'Compile Error',
        message: '<%= error.message %>'
    }).apply(this, args);
    this.emit('end'); // Keep gulp from hanging on this task
}

function buildScript(file, watch) {

    var props = {
        entries: ['./js/' + file],
        debug : true,
        transform:  [babelify]
    };

    // watchify() if watch requested, otherwise run browserify() once
    var bundler = watch ? watchify(browserify(props)) : browserify(props);

    function rebundle() {
        var stream = bundler.bundle();
        return stream
            .on('error', handleErrors)
            .pipe(source(file))
            .pipe(buffer())
            .pipe(sourcemaps.init({loadMaps: true}))
            .pipe(uglify())
            .pipe(sourcemaps.write('./'))
            .pipe(gulp.dest(destFolder));
    }

    // listen for an update and run rebundle
    bundler.on('update', function() {
        rebundle();
        gutil.log('Rebundle...');
    });

    // run it once the first time buildScript is called
    return rebundle();
}



// // run once
// gulp.task('scripts', function() {
//     return buildScript('hjem.js', false);
// });

// run 'scripts' task first, then watch for future changes
gulp.task('default', function() {
    return buildScript('hjem.js', true);
});

gulp.task('js', function () {

    return gulp.src('js/*.js', {read: false}) // no need of reading file because browserify does.
        

        // transform file objects using gulp-tap plugin
        .pipe(tap(function (file) {

            gutil.log('bundling ' + file.path);

            // replace file contents with browserify's bundle stream
            file.contents = browserify(file.path, {debug: true, transform: [babelify]}).bundle();

        }))

        // transform streaming contents into buffer contents (because gulp-sourcemaps does not support streaming contents)
        .pipe(buffer())

        // load and init sourcemaps
        .pipe(sourcemaps.init({loadMaps: true}))

        .pipe(uglify())

        // write sourcemaps
        .pipe(sourcemaps.write('./'))

        .pipe(gulp.dest(destFolder));

});