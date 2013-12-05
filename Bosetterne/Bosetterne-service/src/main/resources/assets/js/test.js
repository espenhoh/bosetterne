/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



jQuery(document).ready(function() {
    if (typeof jQuery !==  'undefined') {
        console.log('jQuery Loaded');
    } else {
        console.log('not loaded yet');
    }
    jQuery("#velkommen").text("Spill spill her");
});