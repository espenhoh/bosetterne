/* globals module, require */
/**
 * Created by Espen on 15.03.2016.
 */
module.exports = function(grunt) {
    "use strict";
    require("load-grunt-tasks")(grunt); // npm install --save-dev load-grunt-tasks
    //target"target/classes/js/dist/hjem.js": ["js/hjem.js"]
    var target, hjem, config;

    config = {
        pkg: grunt.file.readJSON('package.json'),
        browserify: {
            options: {
                browserifyOptions: {
                    debug: true
                }
            },
            dist: {
                options: {
                    transform: [
                        ["babelify", {
                            presets: ["es2015", "react"]
                        }]
                    ]
                },
                files: {
                }
            }
        },
        watch: {
            scripts: {
                files: ["./js/**/*.js", "!./js/dist/**"],
                tasks: ["browserify"]
            }
        }
    };


    target = "./../../../../../target/classes/WebContent/app/js/";
    hjem = target + "dist/hjem.js",
    config.browserify.dist.files[hjem] = ["js/hjem.js"];


    grunt.initConfig(config);

    grunt.loadNpmTasks("grunt-browserify");
    grunt.loadNpmTasks("grunt-contrib-watch");

    grunt.registerTask("default", ["browserify"]);
};
/*
 ,
 "babel": {
 options: {
 sourceMap: true,
 presets: ['es2015']
 },
 dist: {
 files: {
 "js/dist/hjem.js" : "js/hjem.js"
 }
 }
 }

 grunt.registerTask("default", ["babel"]);
 */