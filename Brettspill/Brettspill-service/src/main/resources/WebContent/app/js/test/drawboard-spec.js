/* jslint node: true */
/* global describe, it, expect */
"use strict";


var drawhex_lib = require('../modules/board/DrawHex');



describe("#drawboard", function () {
    it("returns the correct hex", function () {
        var hex = drawhex_lib.DrawHex(2, 3);
        expect(hex).toBe(6);
    });
});