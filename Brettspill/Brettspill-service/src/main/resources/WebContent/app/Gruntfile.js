/* globals module, require */
/**
 * Created by Espen on 15.03.2016.
 */
module.exports = function(grunt) {
    "use strict";
    require("load-grunt-tasks")(grunt); // npm install --save-dev load-grunt-tasks
    //target"target/classes/js/dist/hjem.js": ["js/hjem.js"]
    var target, hjem, hjemMin, pingpong, pingpongMin, config;

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
                            presets: ["es2015"]
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
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n'
            },
            dist: {
                files: {
                }
            }
        }
    };


    target = "./../../../../../target/classes/WebContent/app/js/dist/";
    hjem = target + "hjem.js";
    hjemMin = target + "hjem.min.js";
    pingpong = target + "pingpong.js"
    pingpongMin = target + "pingpong.min.js";

    config.browserify.dist.files[hjem] = ["js/hjem.js"];
    config.browserify.dist.files[pingpong] = ["js/pingpong.js"];

    //config.uglify.dist.files[hjemMin] = hjem;
    config.uglify.dist.files[pingpongMin] = pingpong;

    grunt.initConfig(config);

    grunt.loadNpmTasks("grunt-browserify");
    grunt.loadNpmTasks("grunt-contrib-watch");
    grunt.loadNpmTasks('grunt-contrib-uglify');

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