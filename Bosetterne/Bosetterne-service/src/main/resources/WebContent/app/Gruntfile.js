/**
 * Created by Espen on 15.03.2016.
 */
module.exports = function(grunt) {
    require("load-grunt-tasks")(grunt); // npm install --save-dev load-grunt-tasks

    grunt.initConfig({
        browserify: {
            dist: {
                options: {
                    transform: [
                        ["babelify", {
                            presets: ["es2015"]
                        }]
                    ]
                },
                files: {
                    // if the source file has an extension of es6 then
                    // we change the name of the source file accordingly.
                    // The result file's extension is always .js
                    "dist/hjem.js": ["hjem.js"],
                    "dist/modules/LoginToken.js": ["modules/LoginToken.js"]
                }
            }
        },
        "watch": {
            scripts: {
                files: ["./**/*.js"],
                tasks: ["browserify"]
            }
        }
    });

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